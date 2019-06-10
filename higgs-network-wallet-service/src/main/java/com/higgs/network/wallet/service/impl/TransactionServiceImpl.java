package com.higgs.network.wallet.service.impl;

import com.higgs.network.wallet.common.stub.OrderStatusCode;
import com.higgs.network.wallet.common.stub.ResultCode;
import com.higgs.network.wallet.dao.ExFeeDAO;
import com.higgs.network.wallet.dao.TransactionDepositCryptoDAO;
import com.higgs.network.wallet.dao.TransactionWithdrawCryptoDAO;
import com.higgs.network.wallet.domain.*;
import com.higgs.network.wallet.service.AddressService;
import com.higgs.network.wallet.service.PartnerService;
import com.higgs.network.wallet.service.SymbolService;
import com.higgs.network.wallet.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private SymbolService symbolService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private ExFeeDAO exFeeDAO;

    @Autowired
    private TransactionWithdrawCryptoDAO withdrawDAO;

    @Autowired
    private TransactionDepositCryptoDAO depositDAO;

    @Autowired
    private AddressService addressService;

    @Override
    public Integer checkOrder(CreateWithdrawParameter cwp){
        BigDecimal sumWithdrawAmount = withdrawDAO.getSumAmountByPartnerID(cwp.getPartnerId(),cwp.getSymbol());
        BigDecimal sumDepositAmount = depositDAO.getSumAmountByPartnerID(cwp.getPartnerId(),cwp.getSymbol());
        BigDecimal amountSubtract;
        if(sumWithdrawAmount == null){
            //如果没有提币订单赋值0，方便后续逻辑计算
            sumWithdrawAmount = new BigDecimal(0);
        }
        if(sumDepositAmount == null){
            //如果没有充值订单赋值0，方便后续逻辑计算
            sumDepositAmount = new BigDecimal(0);
        }
        //amountSubtract=充值-提币
        amountSubtract = sumDepositAmount.subtract(sumWithdrawAmount);
        logger.warn("checkOrder PartnerId={} OrderNo={} symbol={} sumDepositAmount={} sumWithdrawAmount={} amountSubtract={} amount={}",cwp.getPartnerId(),cwp.getPartnerOrderNo(),cwp.getSymbol(),sumDepositAmount,sumWithdrawAmount,amountSubtract,cwp.getAmount());
        PartnerInfo partnerInfo = partnerService.getPartnerInfoByID(cwp.getPartnerId());
        if(partnerInfo.getSendbox()!=1) {
            //非沙盒模式验证订单金额
            if (amountSubtract.compareTo(cwp.getAmount()) < 0) {
                //入账出账差值超额，可提金额减去本订单金额超额
                logger.warn("入账出账差值超额 PartnerId={} OrderNo={} symbol={} sumDepositAmount={} sumWithdrawAmount={} amountSubtract={} amount={}",cwp.getPartnerId(),cwp.getPartnerOrderNo(),cwp.getSymbol(),sumDepositAmount,sumWithdrawAmount,amountSubtract,cwp.getAmount());
                return ResultCode.AmountError;
            }
        }
        return ResultCode.SUCCESS;
    }

    @Override
    @Transactional
    public Integer createOrder(CreateWithdrawParameter cwp){

        ConfigCoinSymbol configCoinSymbol = symbolService.getConfigCoinSymbolBySymbol(cwp.getSymbol());
        if(configCoinSymbol==null){
            //币种配置不存在
            logger.warn("币种不存在 PartnerId={} OrderNo={} symbol={}",cwp.getPartnerId(),cwp.getPartnerOrderNo(),cwp.getSymbol());
            return ResultCode.SymbolError;
        }

        ExFee exFee = exFeeDAO.getExFeeBySymbol(cwp.getSymbol());

        if(cwp.getFee().compareTo(new BigDecimal(0))<=0){
            cwp.setFee(exFee.getRate());
        }

        //实际金额=订单金额-手续费
        BigDecimal pay_amount = cwp.getAmount().subtract(exFee.getRate());
        if(pay_amount.compareTo(new BigDecimal(0))<=0){
            //实际金额<=0
            logger.warn("实际金额<=0 PartnerId={} OrderNo={} symbol={} amount={} fee={} pay_amount={}",cwp.getPartnerId(),cwp.getPartnerOrderNo(),cwp.getSymbol(),cwp.getAmount(),exFee.getRate(),pay_amount);
            return ResultCode.AmountError;
        }

        if(pay_amount.compareTo(configCoinSymbol.getWithdrawMax())>0){
            //金额大于最大值
            logger.warn("金额>最大值 PartnerId={} OrderNo={} symbol={} pay_amount={} MaxAmount={}",cwp.getPartnerId(),cwp.getPartnerOrderNo(),cwp.getSymbol(),pay_amount,configCoinSymbol.getWithdrawMax());
            return ResultCode.AmountError;
        }
        if(pay_amount.compareTo(configCoinSymbol.getWithdrawMin())<0){
            //金额小于最小值
            logger.warn("金额<最小值 PartnerId={} OrderNo={} symbol={} pay_amount={} MinAmount={}",cwp.getPartnerId(),cwp.getPartnerOrderNo(),cwp.getSymbol(),pay_amount,configCoinSymbol.getWithdrawMin());
            return ResultCode.AmountError;
        }

        boolean toAddressIsHiggs = false;
        if(addressService.checkAddressInHiggs(cwp.getAddressTo())){
            toAddressIsHiggs = true;
        }

        PartnerInfo partnerInfo = partnerService.getPartnerInfoByID(cwp.getPartnerId());
        TransactionWithdrawCrypto transactionWithdrawCrypto = withdrawDAO.getTransactionWithdrawCryptoByPartnerOrderNo(cwp.getPartnerOrderNo());
        if(transactionWithdrawCrypto==null) {
            transactionWithdrawCrypto = new TransactionWithdrawCrypto();
            transactionWithdrawCrypto.setAddressTo(cwp.getAddressTo());
            transactionWithdrawCrypto.setAmount(cwp.getAmount());
            transactionWithdrawCrypto.setFee(cwp.getFee());
            transactionWithdrawCrypto.setConfirmations(0);
            transactionWithdrawCrypto.setPartnerId(cwp.getPartnerId());
            transactionWithdrawCrypto.setPartnerOrderNo(cwp.getPartnerOrderNo());
            transactionWithdrawCrypto.setPayAmount(pay_amount);
            transactionWithdrawCrypto.setSymbol(cwp.getSymbol());
            transactionWithdrawCrypto.setCreatedAt(new Date(cwp.getTimestamp()));
            if(cwp.getPartnerId()==1 || partnerInfo.getSendbox()==1){
                //编号1为测试编号，提币不上链订单状态为完结
                transactionWithdrawCrypto.setStatus((byte) OrderStatusCode.WITHDRAW_ORDER_STATUS_SUCCESS.intValue());
                transactionWithdrawCrypto.setSendbox(1);
            }else if (toAddressIsHiggs){
                //toAddress是higgs内部地址，则数据不上链进行中心化转账，同时生成充值成功订单
                transactionWithdrawCrypto.setStatus((byte) OrderStatusCode.WITHDRAW_ORDER_STATUS_SUCCESS.intValue());
            }else {
                transactionWithdrawCrypto.setStatus((byte) 1);
            }
            transactionWithdrawCrypto.setAuditType((byte) 1);
            transactionWithdrawCrypto.setRequestCount(0);

            withdrawDAO.insert(transactionWithdrawCrypto);
            logger.info("提币订单保存成功 PartnerId={} OrderNo={} symbol={} pay_amount={}",cwp.getPartnerId(),cwp.getPartnerOrderNo(),cwp.getSymbol(),pay_amount);

            if (toAddressIsHiggs) {
                //toAddress是higgs内部地址，则数据不上链进行中心化转账，同时生成充值成功订单
                TransactionDepositCrypto transactionDepositCrypto = new TransactionDepositCrypto();
                transactionDepositCrypto.setPartnerId(cwp.getPartnerId());
                transactionDepositCrypto.setSymbol(cwp.getSymbol());
                transactionDepositCrypto.setAmount(cwp.getAmount());
                transactionDepositCrypto.setFee(new BigDecimal(0));
                transactionDepositCrypto.setRealFee(new BigDecimal(0));
                transactionDepositCrypto.setCreatedAt(new Date(System.currentTimeMillis()));
                transactionDepositCrypto.setUpdatedAt(new Date(System.currentTimeMillis()));
                transactionDepositCrypto.setAddressTo(cwp.getAddressTo());
                transactionDepositCrypto.setTxid(cwp.getPartnerOrderNo());
                transactionDepositCrypto.setConfirmations(0);
                transactionDepositCrypto.setIsMining((byte)0);
                transactionDepositCrypto.setStatus((byte)1);
                transactionDepositCrypto.setEncrypt("");
                transactionDepositCrypto.setRequestCount(0);
                transactionDepositCrypto.setSendbox(0);

                if(depositDAO.insert(transactionDepositCrypto)<=0){

                    logger.info("中心化转账充值订单保存失败 PartnerId={} OrderNo={} symbol={} pay_amount={}",cwp.getPartnerId(),cwp.getPartnerOrderNo(),cwp.getSymbol(),pay_amount);

                    return ResultCode.FAILED;
                }

                logger.info("中心化转账充值订单保存成功 PartnerId={} OrderNo={} symbol={} pay_amount={}",cwp.getPartnerId(),cwp.getPartnerOrderNo(),cwp.getSymbol(),pay_amount);

            }

            return ResultCode.SUCCESS;
        }
        else{

            return ResultCode.SUCCESS;
        }
    }

    @Override
    public List<TransactionDepositCrypto> getDepositOrderListByPartnerIDListAndRequestCount(String partners, Integer RequestCountBegin, Integer RequestCountEnd){

        List<TransactionDepositCrypto> OrderList = new ArrayList<>();
        //如果查询的是通知次数=0的订单，状态为5=已付款
        Integer status = OrderStatusCode.DEPOSIT_ORDER_STATUS_SUCCESS;
        if(RequestCountBegin>0){
            //查询通知次数>0，状态为已通知(未完成)
            status = OrderStatusCode.DEPOSIT_ORDER_STATUS_CALLBACK_FAILED;
        }
        OrderList = depositDAO.getDepositOrderListByPartnerIDListAndRequestCount(partners,status,RequestCountBegin,RequestCountEnd);
        return OrderList;
    }

    @Override
    public List<TransactionWithdrawCrypto> getWithdrawOrderListByPartnerIDListAndRequestCount(String partners, Integer RequestCountBegin, Integer RequestCountEnd){
        List<TransactionWithdrawCrypto> OrderList = new ArrayList<>();
        //如果查询的是通知次数=0的订单，状态为5=已付款
        Integer status = OrderStatusCode.WITHDRAW_ORDER_STATUS_SUCCESS;
        if(RequestCountBegin>0){
            //查询通知次数>0，状态为已通知(未完成)
            status = OrderStatusCode.WITHDRAW_ORDER_STATUS_CALLBACK_FAILED;
        }
//        logger.info("getOrderList partners={} status={} RequestCountBegin={} RequestCountEnd={}",partners,status,RequestCountBegin,RequestCountEnd);
        OrderList = withdrawDAO.getWithdrawOrderListByPartnerIDListAndRequestCount(partners,status,RequestCountBegin,RequestCountEnd);
//        logger.info("getOrderList OrderList.size={}",OrderList.size());
        return OrderList;
    }

    //创建交易流水上链转账订单
    @Override
    public Integer createOnChainOrder(AutoOnchainOrderinfo Orderinfo){
        TransactionWithdrawCrypto transactionWithdrawCrypto = withdrawDAO.getTransactionWithdrawCryptoByPartnerOrderNo(Orderinfo.getOrderno());
        if(transactionWithdrawCrypto==null) {
            transactionWithdrawCrypto = new TransactionWithdrawCrypto();
            transactionWithdrawCrypto.setAddressTo(Orderinfo.getToAddress());
            transactionWithdrawCrypto.setAmount(Orderinfo.getAmount());
            transactionWithdrawCrypto.setFee(new BigDecimal(0));
            transactionWithdrawCrypto.setConfirmations(0);
            transactionWithdrawCrypto.setPartnerId(Orderinfo.getPartnerId());
            transactionWithdrawCrypto.setPartnerOrderNo(Orderinfo.getOrderno());
            transactionWithdrawCrypto.setPayAmount(Orderinfo.getAmount());
            transactionWithdrawCrypto.setSymbol(Orderinfo.getOnchainSymbol());
            transactionWithdrawCrypto.setCreatedAt(new Date(System.currentTimeMillis()));
            transactionWithdrawCrypto.setStatus((byte) 1);
            transactionWithdrawCrypto.setAuditType((byte) 1);
            transactionWithdrawCrypto.setRequestCount(0);

            logger.info("交易流水上链订单保存成功 PartnerId={} OrderNo={} symbol={} amount={}", Orderinfo.getPartnerId(), Orderinfo.getOrderno(), Orderinfo.getOnchainSymbol(),Orderinfo.getAmount());

            return withdrawDAO.insert(transactionWithdrawCrypto);
        }
        return 0;
    }

    @Override
    public TransactionWithdrawCrypto getTransactionWithdrawCryptoByPartnerOrderNo(String orderNo){
        return withdrawDAO.getTransactionWithdrawCryptoByPartnerOrderNo(orderNo);
    }

    @Override
    public TransactionDepositCrypto getTransactionDepositCryptoByTxid(Integer partnerId,String symbol,String txid){
        return depositDAO.getTransactionDepositCryptoByTxid(partnerId,symbol,txid);
    }

    @Override
    public Integer updateOrderPartnerIdByOrderId(Integer id, Integer partnerId){
        return depositDAO.updateOrderPartnerIdByOrderId(id,partnerId);
    }
}
