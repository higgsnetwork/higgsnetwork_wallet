package com.higgs.network.wallet.service;

import com.higgs.network.wallet.domain.AutoOnchainOrderinfo;
import com.higgs.network.wallet.domain.CreateWithdrawParameter;
import com.higgs.network.wallet.domain.TransactionDepositCrypto;
import com.higgs.network.wallet.domain.TransactionWithdrawCrypto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionService {

    Integer checkOrder(CreateWithdrawParameter cwp);

    Integer createOrder(CreateWithdrawParameter createWithdrawParameter);

    List<TransactionDepositCrypto> getDepositOrderListByPartnerIDListAndRequestCount(String partners, Integer RequestCountBegin, Integer RequestCountEnd);

    List<TransactionWithdrawCrypto> getWithdrawOrderListByPartnerIDListAndRequestCount(String partners, Integer RequestCountBegin, Integer RequestCountEnd);

    //创建交易流水上链转账订单
    Integer createOnChainOrder(AutoOnchainOrderinfo Orderinfo);

    TransactionWithdrawCrypto getTransactionWithdrawCryptoByPartnerOrderNo(String orderNo);

    TransactionDepositCrypto getTransactionDepositCryptoByTxid(Integer partnerId,String symbol,String txid);

    Integer updateOrderPartnerIdByOrderId(Integer id, Integer partnerId);
}
