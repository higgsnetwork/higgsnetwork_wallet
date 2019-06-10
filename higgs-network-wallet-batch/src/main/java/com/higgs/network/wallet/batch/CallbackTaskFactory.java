package com.higgs.network.wallet.batch;

import com.higgs.network.wallet.dao.TransactionDepositCryptoDAO;
import com.higgs.network.wallet.dao.TransactionWithdrawCryptoDAO;
import com.higgs.network.wallet.domain.PartnerInfo;
import com.higgs.network.wallet.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CallbackTaskFactory {
    private static final Logger logger = LoggerFactory.getLogger(CallbackTaskFactory.class);

    @Autowired
    private PartnerService partnerService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AutoOnChainService autoOnChainService;
    @Autowired
    private TransactionDepositCryptoDAO depositDAO;
    @Autowired
    private TransactionWithdrawCryptoDAO withdrawDAO;
    @Autowired
    private SymbolService symbolService;
    @Autowired
    private AddressService addressService;

    private String[] keys = new String[]{"Now","OneMinute","TenMinute","OneHour"};
    Map<String,CallbackTask> qdTasks= Collections.synchronizedMap(new HashMap<String, CallbackTask>());


    @PostConstruct
    public void init(){
    }
    public synchronized void startCallbackTask(){
        for(int i=0;i<keys.length;i++){
            CallbackTask task=qdTasks.get(keys[i]);
            if(task!=null) {
                task.stop();
                qdTasks.remove(keys[i]);
            }
            Integer sleep = 0;
            switch (keys[i]){
                case "Now":
                    sleep = 1*1000;
                    break;
                case "OneMinute":
                    sleep = 60*1000;
                    break;
                case "TenMinute":
                    sleep = 10*60*1000;
                    break;
                case "OneHour":
                    sleep = 60*60*1000;
                    break;
            }
            CallbackTask n_task=new CallbackTaskImpl(keys[i],sleep,this.partnerService,this.transactionService,this.depositDAO,this.withdrawDAO,this.autoOnChainService,this.symbolService,this.addressService);
            Thread thread=new Thread(n_task);
            thread.setName("CallbackTask-"+keys[i]);
            qdTasks.put(keys[i], n_task);
            thread.start();
        }
    }
    public void stopCallbackTask(){

        for(int i=0;i<keys.length;i++){
            CallbackTask task=qdTasks.get(keys[i]);
            if(task!=null) {
                task.stop();
                qdTasks.remove(keys[i]);
            }
        }
    }

}
