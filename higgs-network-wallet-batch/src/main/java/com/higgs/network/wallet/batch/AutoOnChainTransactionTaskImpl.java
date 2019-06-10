package com.higgs.network.wallet.batch;

import com.higgs.network.wallet.domain.*;
import com.higgs.network.wallet.service.AddressService;
import com.higgs.network.wallet.service.AutoOnChainService;
import com.higgs.network.wallet.service.TransactionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;
import java.util.List;

@EnableScheduling
public class AutoOnChainTransactionTaskImpl implements AutoOnChainTransactionTask {
    private static final Logger logger = LoggerFactory.getLogger(AutoOnChainTransactionTaskImpl.class);

    private Integer sleep;
    private Integer runStatus;
    private Integer AutoOnChain_Wait=1;//等待上链
    private Integer AutoOnChain_Underway=2;//正在上链中
    private Integer AutoOnChain_Success=3;//成功上链

    private AutoOnChainService autoOnChainService;
    private AddressService addressService;
    private TransactionService transactionService;
    private AutoOnchainOrderinfo autoOnchainOrderinfo;


    public AutoOnChainTransactionTaskImpl(Integer sleep, AutoOnChainService autoOnChainService, AddressService addressService, TransactionService transactionService){
        this.sleep=sleep;
        this.runStatus = STATUS_NOTRUN;
        this.autoOnChainService = autoOnChainService;
        this.addressService = addressService;
        this.transactionService = transactionService;
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
        logger.info("AutoOnChainTransactionTaskImpl - start");
        boolean executed = false;

        this.runStatus = STATUS_RUNNING;

        while (!executed && runStatus == STATUS_RUNNING) {
            try {
                //扫描成功订单更新订单状态
                UpdateOrderStatus();

                List<AutoOnchainPartnerOrderinfo> autoOnchainPartnerOrderinfoList = autoOnChainService.getAutoOnchainPartnerOrderinfoByStatus(AutoOnChain_Wait);
                if(autoOnchainPartnerOrderinfoList==null){
                    //没有需要上链的流水数据结束当前循环进入下一次循环
                    continue;
                }
                for (AutoOnchainPartnerOrderinfo partnerOrderItem:autoOnchainPartnerOrderinfoList) {
                    //遍历合作方订单
                    List<AutoOnchainPartnerinfo> autoOnchainPartnerinfoList = autoOnChainService.getAutoOnchainPartnerinfoListByPartnerId(partnerOrderItem.getPartnerId());
                    //获取合作方订单需要刷交易的主链列表
                    if (autoOnchainPartnerinfoList != null && autoOnchainPartnerinfoList.size() > 0) {
                        //遍历主链列表
                        AutoOnchainSymbol autoOnchainSymbol = autoOnChainService.getAutoOnchainSymbolByTokenBaseAndSymbol(partnerOrderItem.getTokenBase(), partnerOrderItem.getSymbol());
                        if (autoOnchainSymbol != null) {
                            String onChainTokenBase = autoOnchainSymbol.getOnchainTokenBase();
                            String onChainSymbol = autoOnchainSymbol.getOnchainSymbol();
                            autoOnchainOrderinfo = autoOnChainService.getAutoOnchainOrderinfoByPartnerOrdernoAndTokenbaseAndSymbol(partnerOrderItem.getPartnerId(), partnerOrderItem.getPartnerOrderno(), onChainTokenBase, onChainSymbol);
                            if (autoOnchainOrderinfo == null) {
                                String fromAddress = "";
                                String toAddress = "";
                                String orderNo = "T" + partnerOrderItem.getPartnerId() + onChainTokenBase + onChainSymbol + String.valueOf(System.currentTimeMillis());

                                AddressBindInfo fromAddressBindInfo = addressService.getAddressBindInfoByPartnerUidAndTokenbase(partnerOrderItem.getPartnerId(), partnerOrderItem.getFromPartneruid(), onChainTokenBase);
                                AddressBindInfo toAddressBindInfo = addressService.getAddressBindInfoByPartnerUidAndTokenbase(partnerOrderItem.getPartnerId(), partnerOrderItem.getToPartneruid(), onChainTokenBase);
                                if (fromAddressBindInfo != null) {
                                    //取用户ID对应刷交易主链的钱包地址
                                    fromAddress = fromAddressBindInfo.getAddress();
                                } else {
                                    //刷交易主链用户没有地址
                                    String address = addressService.BindNewAddressByPartnerUid(partnerOrderItem.getPartnerId(), partnerOrderItem.getFromPartneruid(), onChainTokenBase, onChainSymbol);
                                    if (!StringUtils.isBlank(address)) {
                                        fromAddress = address;
                                    }
                                }
                                if (toAddressBindInfo != null) {
                                    //取用户ID对应刷交易主链的钱包地址
                                    toAddress = toAddressBindInfo.getAddress();
                                } else {
                                    //刷交易主链用户没有地址
                                    String address = addressService.BindNewAddressByPartnerUid(partnerOrderItem.getPartnerId(), partnerOrderItem.getToPartneruid(), onChainTokenBase, onChainSymbol);
                                    if (!StringUtils.isBlank(address)) {
                                        toAddress = address;
                                    }
                                }
                                //from to 地址都有生成订单信息
                                if (!StringUtils.isBlank(fromAddress) && !StringUtils.isBlank(toAddress)) {
                                    //生成本地订单并保存
                                    autoOnchainOrderinfo = new AutoOnchainOrderinfo();
                                    autoOnchainOrderinfo.setPartnerId(partnerOrderItem.getPartnerId());
                                    autoOnchainOrderinfo.setPartnerOrderno(partnerOrderItem.getPartnerOrderno());
                                    autoOnchainOrderinfo.setOrderno(orderNo);
                                    autoOnchainOrderinfo.setOnchainTokenBase(onChainTokenBase);
                                    autoOnchainOrderinfo.setOnchainSymbol(onChainSymbol);
                                    autoOnchainOrderinfo.setAmount(partnerOrderItem.getAmount());
                                    autoOnchainOrderinfo.setFromAddress(fromAddress);
                                    autoOnchainOrderinfo.setFromPartnerUid(partnerOrderItem.getFromPartneruid());
                                    autoOnchainOrderinfo.setWithdrawStatus(1);
                                    autoOnchainOrderinfo.setWithdrawTime(new Date(System.currentTimeMillis()));
                                    autoOnchainOrderinfo.setToAddress(toAddress);
                                    autoOnchainOrderinfo.setToPartnerUid(partnerOrderItem.getToPartneruid());
                                    autoOnchainOrderinfo.setDepositStatus(1);
                                    autoOnchainOrderinfo.setDepositTime(new Date(System.currentTimeMillis()));
                                    autoOnchainOrderinfo.setTxid("");
                                    autoOnchainOrderinfo.setStatus(1);
                                    autoOnchainOrderinfo.setCreateTime(new Date(System.currentTimeMillis()));
                                    //保存本地订单
                                    autoOnChainService.addAutoOnchainOrderinfo(autoOnchainOrderinfo);
                                    //保存上链订单
                                    transactionService.createOnChainOrder(autoOnchainOrderinfo);
                                } else {
                                    logger.warn("AutoOnChainTransactionTaskImpl tonken={} symbol={} 为获得用户地址无法写入上链信息", onChainTokenBase, onChainSymbol);
                                    //如果经过分配用户还是没有地址，那么推出此次循环等待下次循环急需上述过程；
                                }
                            } else {
                                //订单已经存在
                                List<AutoOnchainOrderinfo> autoOnchainOrderinfos = autoOnChainService.getAutoOnchainOrderinfoByPartnerOrderno(partnerOrderItem.getPartnerId(), partnerOrderItem.getPartnerOrderno());
                                boolean flag = true;
                                //遍历此订单刷交易的所有订单（多条链刷交易的情况否则只有一条数据），如果全部都是上链成功那么flag=true
                                for (AutoOnchainOrderinfo orderitem : autoOnchainOrderinfos) {
                                    if (orderitem.getStatus() != AutoOnChain_Success) {
                                        //有一条不成功flag就是false；
                                        flag = false;
                                    }
                                }
                                if (flag) {
                                    //如果全部订单都是上链成功，则更新合作方订单状态为全部完成，下次不扫描此订单
                                    autoOnChainService.updatePartnerOrderinfoStatusByPartnerIdAndPartnerOrderNo(
                                            partnerOrderItem.getPartnerId(), partnerOrderItem.getPartnerOrderno(),
                                            AutoOnChain_Success);
                                }
                            }
                        }
                    }
                }

                //等待毫秒
                Thread.sleep(sleep);

            } catch (Exception ex) {
                logger.error("AutoOnChainTransactionTaskImpl catch {}", ex);
//                executed = true;
            }
        }
        logger.info("AutoOnChainTransactionTaskImpl - stop ");
    }

    public void UpdateOrderStatus() {
        //得到所有等待上链的数据
        List<AutoOnchainOrderinfo> autoOnchainOrderinfoList = autoOnChainService.getAutoOnchainOrderinfoListByStatus(AutoOnChain_Wait);
        for (AutoOnchainOrderinfo orderinfoItme : autoOnchainOrderinfoList) {
            //遍历本地订单列表
            TransactionWithdrawCrypto withdrawCrypto = transactionService.getTransactionWithdrawCryptoByPartnerOrderNo(orderinfoItme.getOrderno());
            //查询提币上链订单表是否已写入
            if (withdrawCrypto != null) {
                //已经写入的订单判断状态是否已完成
                if (withdrawCrypto.getStatus() != 8 && orderinfoItme.getWithdrawStatus() != AutoOnChain_Underway) {
                    //数据上链未完成更新本地订单状态为进行中
                    orderinfoItme.setTxid(withdrawCrypto.getTxid());
                    orderinfoItme.setWithdrawStatus(AutoOnChain_Underway);
                    orderinfoItme.setStatus(AutoOnChain_Underway);
                    autoOnChainService.updateAutoOnchainOrderinfoByWithdrawAndOrderNo(orderinfoItme);
                    autoOnChainService.updatePartnerOrderinfoStatusByPartnerIdAndPartnerOrderNo(orderinfoItme.getPartnerId(), orderinfoItme.getPartnerOrderno(), AutoOnChain_Underway);
                }
                //查询上链充值订单，根据提币订单的txid查询是否已经充入完成
                TransactionDepositCrypto depositCrypto = transactionService.getTransactionDepositCryptoByTxid(orderinfoItme.getPartnerId(), orderinfoItme.getOnchainSymbol(), orderinfoItme.getTxid());
                if (depositCrypto != null) {
                    if (depositCrypto.getStatus() != 8 && orderinfoItme.getWithdrawStatus() != AutoOnChain_Underway) {
                        //数据上链未完成更新本地订单状态为进行中
                        orderinfoItme.setTxid(withdrawCrypto.getTxid());
                        orderinfoItme.setWithdrawStatus(AutoOnChain_Underway);
                        orderinfoItme.setStatus(AutoOnChain_Underway);
                        autoOnChainService.updateAutoOnchainOrderinfoByWithdrawAndOrderNo(orderinfoItme);
                    }
                }
            } else {
                //写入finance读取的表中
                transactionService.createOnChainOrder(orderinfoItme);
            }
        }

        //得到已上链数据
        autoOnchainOrderinfoList = autoOnChainService.getAutoOnchainOrderinfoListByStatus(AutoOnChain_Underway);
        for (AutoOnchainOrderinfo orderinfoItme : autoOnchainOrderinfoList) {
            //遍历本地订单列表
            TransactionWithdrawCrypto withdrawCrypto = transactionService.getTransactionWithdrawCryptoByPartnerOrderNo(orderinfoItme.getOrderno());
            //查询提币上链订单表是否已写入
            if (withdrawCrypto != null) {
                //已经写入的订单判断状态是否已完成
                if (withdrawCrypto.getStatus() == 8 && orderinfoItme.getWithdrawStatus() != AutoOnChain_Success) {
                    //提现状态: 0 未审核，1 审核通过，2 审核拒绝，3 支付中已经打币，4 支付失败，5 已完成，6 已撤销, 7 通知失败,8 通知完成
                    //上链提币已完成订单更新本地订单提币状态已完成
                    orderinfoItme.setTxid(withdrawCrypto.getTxid());
                    orderinfoItme.setWithdrawStatus(AutoOnChain_Success);
                    orderinfoItme.setWithdrawTime(new Date(System.currentTimeMillis()));
                    autoOnChainService.updateAutoOnchainOrderinfoByWithdrawAndOrderNo(orderinfoItme);
                }
                //查询上链充值订单，根据提币订单的txid查询是否已经充入完成
                TransactionDepositCrypto depositCrypto = transactionService.getTransactionDepositCryptoByTxid(orderinfoItme.getPartnerId(), orderinfoItme.getOnchainSymbol(), orderinfoItme.getTxid());
                if (depositCrypto != null) {
                    if (depositCrypto.getStatus() == 8 && orderinfoItme.getDepositStatus() != AutoOnChain_Success) {
                        //上链充值已完成订单更新本地订单状态已完成
                        orderinfoItme.setDepositStatus(AutoOnChain_Success);
                        orderinfoItme.setDepositTime(new Date(System.currentTimeMillis()));
                        orderinfoItme.setStatus(AutoOnChain_Success);
                        autoOnChainService.updateAutoOnchainOrderinfoByDepositAndTxid(orderinfoItme);
                    }
                }
            }

            if (orderinfoItme.getDepositStatus() == AutoOnChain_Success && orderinfoItme.getWithdrawStatus() == AutoOnChain_Success) {
                //更新合作方订单状态为成功
                autoOnChainService.updatePartnerOrderinfoStatusByPartnerIdAndPartnerOrderNo(orderinfoItme.getPartnerId(), orderinfoItme.getPartnerOrderno(), AutoOnChain_Success);
            }
        }
    }
}