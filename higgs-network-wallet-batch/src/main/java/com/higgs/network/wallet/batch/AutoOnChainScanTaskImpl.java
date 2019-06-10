package com.higgs.network.wallet.batch;

import com.higgs.network.wallet.common.util.HttpUtil;
import com.higgs.network.wallet.common.util.MD5;
import com.higgs.network.wallet.common.util.StringUtil;
import com.higgs.network.wallet.common.util.VerifySign;
import com.higgs.network.wallet.domain.*;
import com.higgs.network.wallet.service.AutoOnChainService;
import com.higgs.network.wallet.service.SymbolService;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class AutoOnChainScanTaskImpl implements AutoOnChainScanTask {
    private static final Logger logger = LoggerFactory.getLogger(AutoOnChainScanTaskImpl.class);

    private Integer sleep;
    private Integer runStatus;
    private AutoOnChainService autoOnChainService;
    private SymbolService symbolService;

    public AutoOnChainScanTaskImpl(Integer sleep,AutoOnChainService autoOnChainService,SymbolService symbolService){
        this.sleep = sleep;
        this.runStatus = STATUS_NOTRUN;
        this.autoOnChainService = autoOnChainService;
        this.symbolService = symbolService;
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
    public void run() {
        logger.info("AutoOnChainScanTaskImpl - start");
        boolean executed = false;

        this.runStatus = STATUS_RUNNING;
        while (!executed && runStatus == STATUS_RUNNING) {
//            logger.info("AutoOnChainScanTaskImpl - run {} , executed={} , runStatus={}", key,executed,runStatus);
            try {
                //得到所有需要上链刷交易的合作方列表
                List<PartnerInfo> partnerInfoList = autoOnChainService.getScanTransactionPartnerList();
                if(partnerInfoList!=null&&partnerInfoList.size()>0) {

                    if (partnerInfoList.size() > 0) {
                        for (PartnerInfo partnerInfoItem : partnerInfoList) {
                            //遍历所有扫描交易的合作方列表
                            boolean flag = true;
                            while (flag) {
                                //循环扫描订单
                                Integer count = 20;
                                String lastorderno=autoOnChainService.getLastPartnerOrderno(partnerInfoItem.getId());
                                String key=partnerInfoItem.getKey();
                                if(!StringUtils.isBlank(partnerInfoItem.getTransactionUrl())) {
                                    //正式逻辑通过接口读取交易流水信息
                                    String responseData = autoOnChainService.ScanTransactionUrl(lastorderno, partnerInfoItem.getTransactionUrl(), key, count);
                                    //正式逻辑
                                    //测试逻辑 本地随机生成流水信息
//                                    String responseData = testResponseData();
                                    //测试逻辑
                                    logger.info("AutoOnChainScanTaskImpl partner={} url={} responseData={}", partnerInfoItem.getId(), partnerInfoItem.getTransactionUrl(), responseData);
                                    if(!StringUtils.isBlank(responseData)) {
                                        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(responseData);
                                        ScanTransaction scanTransaction = (ScanTransaction) jsonObject.toJavaObject(ScanTransaction.class);
                                        if (scanTransaction == null || !scanTransaction.getStatus().toLowerCase().equals("success") || scanTransaction.getOrderCount() <= 0) {
                                            flag = false;
                                        } else {
                                            //收到返回值并且状态正常
                                            List<ScanTransactionOrderItem> scanTransactionDate = scanTransaction.getDate();
                                            if (scanTransactionDate == null || scanTransactionDate.size() <= 0) {
                                                //没有新数据
                                                flag = false;
                                            } else {
                                                //返回订单数量大于0
                                                //格式化合作方的返回数据
                                                AutoOnchainPartnerOrderinfo autoOnchainPartnerOrderinfo;
                                                for (ScanTransactionOrderItem item : scanTransactionDate) {
                                                    //循环所有订单列表生成本地订单数据保存
                                                    autoOnchainPartnerOrderinfo = new AutoOnchainPartnerOrderinfo();
                                                    autoOnchainPartnerOrderinfo.setPartnerId(partnerInfoItem.getId());
                                                    autoOnchainPartnerOrderinfo.setPartnerOrderno(String.valueOf(System.currentTimeMillis()));
                                                    autoOnchainPartnerOrderinfo.setTokenBase(symbolService.getConfigCoinSymbolBySymbol(item.getSymbol()).getTokenBase());
                                                    autoOnchainPartnerOrderinfo.setSymbol(item.getSymbol());
                                                    autoOnchainPartnerOrderinfo.setAmount(new BigDecimal(item.getAmount()));
                                                    autoOnchainPartnerOrderinfo.setFromPartneruid(item.getFromUid());
                                                    autoOnchainPartnerOrderinfo.setToPartneruid(item.getToUid());
                                                    autoOnchainPartnerOrderinfo.setStatus(1);
                                                    autoOnchainPartnerOrderinfo.setCreateTime(new Date(System.currentTimeMillis()));

                                                    //写入上链数据表
                                                    autoOnChainService.addAutoOnchainPartnerOrderinfo(autoOnchainPartnerOrderinfo);
                                                }
                                                if (scanTransactionDate.size() < count) {
                                                    flag = false;
                                                }
                                            }
                                        }
                                    }else{
                                        flag = false;
                                    }
                                }else{
                                    flag = false;
                                    logger.info("AutoOnChainScanTaskImpl partnerInfoListsize={} partner={} url=null",partnerInfoList.size(), partnerInfoItem.getId(), partnerInfoItem.getTransactionUrl());
                                }
                            }
                        }
                    }
                }
                //等待毫秒
                Thread.sleep(sleep);
            } catch (Exception ex) {
                logger.error("AutoOnChainScanTaskImpl - catch {}" , ex);
//                executed = true;
            }
        }
        logger.info("AutoOnChainScanTaskImpl - stop");
    }

    //解析返回值格式化orderlist
    private List<AutoOnchainPartnerOrderinfo> formatOrderList(List<ScanTransactionOrderItem> scanTransactionDate,Integer partnerId){
        List<AutoOnchainPartnerOrderinfo> autoOnchainPartnerOrderinfos = new ArrayList<AutoOnchainPartnerOrderinfo>();
        AutoOnchainPartnerOrderinfo autoOnchainPartnerOrderinfo;
        for(ScanTransactionOrderItem item : scanTransactionDate){
            autoOnchainPartnerOrderinfo = new AutoOnchainPartnerOrderinfo();
            autoOnchainPartnerOrderinfo.setPartnerId(partnerId);
            autoOnchainPartnerOrderinfo.setPartnerOrderno(item.getOrderno());
            autoOnchainPartnerOrderinfo.setTokenBase(symbolService.getConfigCoinSymbolBySymbol(item.getSymbol()).getTokenBase());
            autoOnchainPartnerOrderinfo.setSymbol(item.getSymbol());
            autoOnchainPartnerOrderinfo.setAmount(new BigDecimal(item.getAmount()));
            autoOnchainPartnerOrderinfo.setFromPartneruid(item.getFromUid());
            autoOnchainPartnerOrderinfo.setToPartneruid(item.getToUid());
            autoOnchainPartnerOrderinfo.setStatus(1);
            autoOnchainPartnerOrderinfo.setCreateTime(new Date(System.currentTimeMillis()));
            autoOnchainPartnerOrderinfos.add(autoOnchainPartnerOrderinfo);
        }
        return autoOnchainPartnerOrderinfos;
    }

    /**
     * 扫描交易
     * @param lastorderno
     * @param url
     * @param salt
     */
    public String ScanTransactionUrl(String lastorderno, String url, String salt,Integer count) {
        List<AutoOnchainPartnerOrderinfo> autoOnchainPartnerOrderinfos=null;
        try {
            if (!StringUtils.isBlank(url)) {
                Long time = System.currentTimeMillis();
                //组装参数进行签名
                Map<String, Object> map = new HashMap<>();
                if(StringUtils.isBlank(lastorderno)){
                    map.put("lastorderno", "");
                }else {
                    map.put("lastorderno", lastorderno);
                }
                map.put("count",count);
                map.put("timestamp",time);
                String sign = MD5.getMD5(VerifySign.getVerifySignString(map, salt));

                JSONObject params = new JSONObject(map);
                params.put("sign", sign);
                logger.info("AutoOnChainScanTaskImpl - run 扫描交易 url={} params={}", url, params.toJSONString());

                String responseData = HttpUtil.sendPostJson(url, params, "utf-8");
                logger.info("AutoOnChainScanTaskImpl - run 扫描交易 responseData={}", responseData);
                return responseData;
            }
            else{
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("AutoOnChainScanTaskImpl Scan catch {}" , e.getMessage());
            return null;
        }
    }

    private String testResponseData() {
        Random ra =new Random();
        String[] symbollist=new String[]{"NEO","FISH"};
        String[] userlist=new String[]{"user0001","user0002","user0003","user0004"};
        Integer count = ra.nextInt(20);
        String responseData = "{\"status\":\"success\",\"orderCount\":"+count+",\"date\": [";


        for (int i = 0; i < count; i++) {

            // 定义double变量
            double d = ra.nextDouble();
            // 根据小数位，有几位，乘以10的几次方，强制转换成int类型。
            int iamount = (int) (d * 10000);
            // 转回double类型,然后将乘上的数重新除去。
            double amount = (double)iamount / 10000 + ra.nextInt(1000);

            responseData += "{\"orderno\":\""+System.currentTimeMillis()+"\",";
            responseData += "\"symbol\":\""+symbollist[ra.nextInt(symbollist.length)]+"\",";
            responseData += "\"amount\":\""+amount+"\",";
            String fromUid = userlist[ra.nextInt(userlist.length)];
            String toUid = "";
            responseData += "\"fromUid\":\""+fromUid+"\",";
            boolean flag = true;
            while (flag) {
                toUid = userlist[ra.nextInt(userlist.length)];
                if(fromUid!=toUid){
                    flag = false;
                }
            }
            responseData += "\"toUid\":\""+toUid+"\"";

            responseData += "}";
            if(i!=count-1){
                responseData += ",";
            }
        }
        responseData += "]}";
        return responseData;
    }
}