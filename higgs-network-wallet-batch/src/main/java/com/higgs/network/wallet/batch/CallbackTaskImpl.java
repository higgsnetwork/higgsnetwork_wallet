package com.higgs.network.wallet.batch;

import com.higgs.network.wallet.common.stub.OrderStatusCode;
import com.higgs.network.wallet.common.util.HttpUtil;
import com.higgs.network.wallet.common.util.MD5;
import com.higgs.network.wallet.common.util.VerifySign;
import com.higgs.network.wallet.dao.TransactionDepositCryptoDAO;
import com.higgs.network.wallet.dao.TransactionWithdrawCryptoDAO;
import com.higgs.network.wallet.domain.*;
import com.higgs.network.wallet.service.*;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallbackTaskImpl implements CallbackTask {
    private static final Logger logger = LoggerFactory.getLogger(CallbackTaskImpl.class);

    private String key;
    private Integer sleep;
    private Integer runStatus;
    private Integer begin;
    private Integer end;

    private PartnerService partnerService;
    private List<PartnerInfo> partnerInfoList;
    private TransactionService transactionService;
    private AutoOnChainService autoOnChainService;
    private TransactionDepositCryptoDAO depositDAO;
    private TransactionWithdrawCryptoDAO withdrawDAO;
    private SymbolService symbolService;
    private AddressService addressService;


    public CallbackTaskImpl(String key,Integer sleep,PartnerService partnerService,TransactionService transactionService,TransactionDepositCryptoDAO depositDAO,TransactionWithdrawCryptoDAO withdrawDAO,AutoOnChainService autoOnChainService,SymbolService symbolService,AddressService addressService){
        this.key = key;
        this.sleep=sleep;
        this.runStatus = STATUS_NOTRUN;
        this.partnerService = partnerService;
        this.transactionService = transactionService;
        this.depositDAO = depositDAO;
        this.withdrawDAO = withdrawDAO;
        this.autoOnChainService = autoOnChainService;
        this.symbolService = symbolService;
        this.addressService = addressService;
    }

    @Override
    public void suspend(){

    }

    @Override
    public void unsuspend(){

    }

    @Override
    public void stop(){
        runStatus=STATUS_STOPPED;
    }

    @Override
    public String getSymbol() {
        return key;
    }

    @Override
    public void run() {
        logger.info("CallbackTaskImpl - start {}", key);
        boolean executed = false;
        partnerInfoList = this.partnerService.getAllPartnerInfoListByIsOpen();

        this.runStatus = STATUS_RUNNING;
        //设置request_count 通知次数的取值区间
        switch (key) {
            case "Now":
                //新订单从0开始，实时进行回调
                begin = 0;
                end = 0;
                break;
            case "OneMinute":
                //1分钟线程读取通知次数1到9的订单，一共通知9次，耗时10分钟
                begin = 1;
                end = 9;
                break;
            case "TenMinute":
                //10分钟线程读取通知次数10到14的订单，一共通知5次，耗时50分钟
                begin = 10;
                end = 14;
                break;
            case "OneHour":
                //1小时线程读取通知15次以上的订单，一共通知48次，耗时2天
                begin = 15;
                end = 63;
                break;
        }
        while (!executed && runStatus == STATUS_RUNNING) {
            try {
                //获取开放的平台列表
                partnerInfoList = this.partnerService.getAllPartnerInfoListByIsOpen();
//                if (sleep >= (1000 * 60)) {
//                    //大于10分钟执行的进程，每次循环都更新列表
//                    partnerInfoList = this.partnerService.getAllPartnerInfoListByIsOpen();
//                }
                if (partnerInfoList != null && partnerInfoList.size() > 0) {
                    //拼接已开放平台的ID列表
                    String Partners = "0";
                    //默认增加Partners=0 是因为IOST 等 代前缀的 地址 finance项目无法获取真正的PartnerId信息 默认为0，要更新这些订单的真正PartnerID
                    for (int i = 0; i < partnerInfoList.size(); i++) {
                        Partners += ","+partnerInfoList.get(i).getId();
                    }


                    //根据已开放的ID平台查询通知次数区间的<充值订单>进行通知
                    List<TransactionDepositCrypto> DepositOrder = transactionService.getDepositOrderListByPartnerIDListAndRequestCount(Partners, begin, end);
                    if (DepositOrder != null&& DepositOrder.size()>0) {
                        //IOST&EOS币对 节点程序通知时地址格式为 《地址前缀|用户number》格式无法对应到address_bind_info表中数据，所以无法得到正确的PartnerId
                        //无法通知到正确的合作方回调地址，需要更新订单的PartnerId数据
                        for (int i = 0; i < DepositOrder.size(); i++) {
                            if (Integer.valueOf(DepositOrder.get(i).getStatus()) == OrderStatusCode.DEPOSIT_ORDER_STATUS_SUCCESS) {
                                //第一次通知过来的数据检测更新PartnerId数据
                                String basetoken = symbolService.getConfigCoinSymbolBySymbol(DepositOrder.get(i).getSymbol()).getTokenBase();
                                //获得币种 basetoken
                                SystemAddress systemAddress = addressService.getSystemAddressByBasetoken(basetoken);
                                if (systemAddress != null) {
                                    //是需要更新的币对
                                    //按照|拆分 取用户number，number是address_bind_info表中信息
                                    String addr = DepositOrder.get(i).getAddressTo().split("\\|")[1];
                                    //得到真正的用户address反查PartnerId
                                    Integer partnerId = addressService.getAddressInfo(addr).getPartnerId();
                                    //更新订单PartnerId
                                    transactionService.updateOrderPartnerIdByOrderId(DepositOrder.get(i).getId(), partnerId);
                                }

                            }
                        }
                        DepositOrder = transactionService.getDepositOrderListByPartnerIDListAndRequestCount(Partners, begin, end);
                        //根据订单列表
                        for (int i = 0; i < DepositOrder.size(); i++) {
                            //遍历订单列表分别处理回调
                            List<AutoOnchainSymbol> autoOnchainSymbol = autoOnChainService.getAutoOnchainSymbolListByOnchainSymbol(DepositOrder.get(i).getSymbol());
                            if (autoOnchainSymbol != null && autoOnchainSymbol.size() > 0) {
                                //是刷交易的币种不执行通知逻辑
                                depositDAO.updateOrderRequestCountByOrderId(DepositOrder.get(i).getId(), OrderStatusCode.DEPOSIT_ORDER_STATUS_CALLBACK_SUCCESS);
                            } else {

                                for (int x = 0; x < partnerInfoList.size(); x++) {
                                    if (partnerInfoList.get(x).getId() == DepositOrder.get(i).getPartnerId()) {
                                        //根据订单的平台ID取得回调地址和签名key
                                        depositCallbackNotifyUrl(DepositOrder.get(i), partnerInfoList.get(x).getDepositCallbackUrl(), partnerInfoList.get(x).getKey());
                                    }
                                }
                            }
                        }
                    }

                    //根据已开放的ID平台查询通知次数区间的<提币订单>进行通知
                    List<TransactionWithdrawCrypto> WithdrawOrder = transactionService.getWithdrawOrderListByPartnerIDListAndRequestCount(Partners, begin, end);
                    if (WithdrawOrder != null && WithdrawOrder.size()>0) {
                        //根据订单列表
                        for (int i = 0; i < WithdrawOrder.size(); i++) {
                            //遍历订单列表分别处理回调
                            List<AutoOnchainSymbol> autoOnchainSymbol = autoOnChainService.getAutoOnchainSymbolListByOnchainSymbol(WithdrawOrder.get(i).getSymbol());
                            if (autoOnchainSymbol != null && autoOnchainSymbol.size() > 0) {
                                //是刷交易的币种不执行通知逻辑
                                withdrawDAO.updateOrderRequestCountByOrderId(WithdrawOrder.get(i).getId(), OrderStatusCode.WITHDRAW_ORDER_STATUS_CALLBACK_SUCCESS);
                            } else {
                                //遍历订单列表分别处理回调
                                for (int x = 0; x < partnerInfoList.size(); x++) {
                                    if (partnerInfoList.get(x).getId() == WithdrawOrder.get(i).getPartnerId()) {
                                        //根据订单的平台ID取得回调地址和签名key
                                        withdrawCallbackNotifyUrl(WithdrawOrder.get(i), partnerInfoList.get(x).getWithdrawCallbackUrl(), partnerInfoList.get(x).getKey());
                                    }
                                }
                            }
                        }
                    }
                }

                //等待毫秒
                Thread.sleep(sleep);
            } catch (Exception ex) {
                 logger.error("CallbackTaskImpl - {} catch {}" , key, ex);
                executed = true;
            }
        }
        logger.info("CallbackTaskImpl - stop {}", key);
    }

    /**
     * 充值订单通知合作方
     * @param Order
     * @param url
     * @param salt
     */
    public void depositCallbackNotifyUrl(TransactionDepositCrypto Order,String url,String salt) {
        try {
            if (Order != null && !StringUtils.isBlank(url)) {
                Long time = System.currentTimeMillis();
                //组装参数进行签名
                Map<String, Object> map = new HashMap<>();
                map.put("amount", Order.getAmount().toString());
                map.put("partnerId", Order.getPartnerId());
                map.put("symbol", Order.getSymbol());
                map.put("toaddress", Order.getAddressTo());
                map.put("txid", Order.getTxid());
                map.put("type", "deposit");
                map.put("timestamp", time);
                String sign = MD5.getMD5(VerifySign.getVerifySignString(map, salt));

                JSONObject params = new JSONObject(map);
                params.put("sign", sign);
                if (!StringUtils.isBlank(url)) {
                    logger.info("CallbackTaskImpl - run {} 充值通知 url={} params={}", key, url, params.toJSONString());
                    String responseData = HttpUtil.sendPostJson(url, params, "utf-8");

                    if (!StringUtils.isBlank(responseData) && responseData.toLowerCase().equals("success")) {
                        //通知平台成功
                        depositDAO.updateOrderRequestCountByOrderId(Order.getId(), OrderStatusCode.DEPOSIT_ORDER_STATUS_CALLBACK_SUCCESS);
                            logger.info("CallbackTaskImpl - run {} 充值通知成功 responseData={}", key, responseData);
                    } else {
                        //通知平台失败
                        depositDAO.updateOrderRequestCountByOrderId(Order.getId(), OrderStatusCode.DEPOSIT_ORDER_STATUS_CALLBACK_FAILED);
                        logger.warn("CallbackTaskImpl - run {} 充值通知失败 responseData={} params={}", key, responseData, params.toJSONString());
                    }
                }else{
                    //通知平台失败
                    depositDAO.updateOrderRequestCountByOrderId(Order.getId(), OrderStatusCode.DEPOSIT_ORDER_STATUS_CALLBACK_FAILED);
                    logger.warn("CallbackTaskImpl - run {} 充值通知失败 url=null", key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("CallbackTaskImpl - run {} 充值通知失败 catch {}" , key, e.getMessage());
        }
    }

    /**
     * 提币订单通知合作方
     * @param Order
     * @param url
     * @param salt
     */
    public void withdrawCallbackNotifyUrl(TransactionWithdrawCrypto Order,String url,String salt) {
        try {
            if (Order != null ) {
                Long time = System.currentTimeMillis();
                //组装参数进行签名
                Map<String, Object> map = new HashMap<>();
                map.put("amount",Order.getAmount().toString());
                map.put("partnerId",Order.getPartnerId());
                map.put("partnerorderno",Order.getPartnerOrderNo());
                map.put("symbol",Order.getSymbol());
                map.put("txid",Order.getTxid());
                map.put("type","withdraw");
                map.put("timestamp",time);
                String sign = MD5.getMD5(VerifySign.getVerifySignString(map,salt));

                //将所有参数结果的MAP转换json并加入签名
                JSONObject params=new JSONObject(map);
                params.put("sign",sign);
                logger.info("CallbackTaskImpl - run {} 提币通知 url={} params={}", key,url, params.toJSONString());
                if (!StringUtils.isBlank(url)) {
                    //提交json参数并得到返回值
                    String responseData = HttpUtil.sendPostJson(url, params, "utf-8");

                    if (!StringUtils.isBlank(responseData) && responseData.toLowerCase().equals("success")) {
                        //通知平台成功
                        withdrawDAO.updateOrderRequestCountByOrderId(Order.getId(), OrderStatusCode.WITHDRAW_ORDER_STATUS_CALLBACK_SUCCESS);
                        logger.info("CallbackTaskImpl - run {} 提币通知成功 responseData={}", key, responseData);
                    } else {
                        //通知平台失败
                        withdrawDAO.updateOrderRequestCountByOrderId(Order.getId(), OrderStatusCode.WITHDRAW_ORDER_STATUS_CALLBACK_FAILED);
                        logger.warn("CallbackTaskImpl - run {} 提币通知失败 responseData={}  params={}", key, responseData, params.toJSONString());
                    }
                }else{
                    //通知平台失败
                    withdrawDAO.updateOrderRequestCountByOrderId(Order.getId(), OrderStatusCode.WITHDRAW_ORDER_STATUS_CALLBACK_FAILED);
                    logger.warn("CallbackTaskImpl - run {} 提币通知失败 url=null", key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("CallbackTaskImpl - run {} 提币通知失败 catch {}" , key, e.getMessage());
        }
    }


//    /**
//     * 每分钟更新
//     * @throws ParseException
//     */
//    @Scheduled(cron = "0 */1 * * * ?")
//    public void TasksOneMinute() throws ParseException {
//        if(sleep<(1000*60)) {
////            logger.error("CallbackTaskImpl - {} TasksOneMinute {}" , key, "更新平台列表");
//            //小于10分钟执行的进程，每分钟更新一次平台列表
//            partnerInfoList = this.partnerService.getAllPartnerInfoListByIsOpen();
//        }
//    }
}