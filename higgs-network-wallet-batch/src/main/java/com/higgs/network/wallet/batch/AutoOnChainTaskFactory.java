package com.higgs.network.wallet.batch;

import com.higgs.network.wallet.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class AutoOnChainTaskFactory {
    private static final Logger logger = LoggerFactory.getLogger(AutoOnChainTaskFactory.class);

    @Autowired
    private AutoOnChainService autoOnChainService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private SymbolService symbolService;

    Map<String,AutoOnChainScanTask> scanTasks= Collections.synchronizedMap(new HashMap<String, AutoOnChainScanTask>());
    Map<String,AutoOnChainTransactionTask> transactionTasks= Collections.synchronizedMap(new HashMap<String, AutoOnChainTransactionTask>());

    String sTaskName = "AutoOnChainScanTask";
    String tTaskName = "AutoOnChainTransactionTask";
    @PostConstruct
    public void init(){
    }
    public synchronized void startAutoOnChainTask(){
        startScanTask();
        startTransactionTask();
    }

    /**
     * 启动扫描合作方转账记录接口线程,读取数据写入本地
     */
    private void startScanTask(){
        AutoOnChainScanTask task=scanTasks.get(sTaskName);
        if(task!=null) {
            task.stop();
            scanTasks.remove(sTaskName);
        }
        Integer sleep = 60*1000;
        AutoOnChainScanTask n_task=new AutoOnChainScanTaskImpl(sleep,autoOnChainService,symbolService);
        Thread thread=new Thread(n_task);
        thread.setName(sTaskName);
        scanTasks.put(sTaskName, n_task);
        thread.start();
    }

    /**
     * 启动数据上链线程,根据合作方流水记录决定上链数据
     */
    private void startTransactionTask(){
        AutoOnChainTransactionTask task=transactionTasks.get(tTaskName);
        if(task!=null) {
            task.stop();
            transactionTasks.remove(tTaskName);
        }
        Integer sleep = 10*1000;
        AutoOnChainTransactionTask n_task=new AutoOnChainTransactionTaskImpl(sleep,autoOnChainService,addressService,transactionService);
        Thread thread=new Thread(n_task);
        thread.setName(tTaskName);
        transactionTasks.put(tTaskName, n_task);
        thread.start();
    }

    public void stopAutoOnChainTask() {

        AutoOnChainScanTask sTask = scanTasks.get(sTaskName);
        if (sTask != null) {
            sTask.stop();
        }

        AutoOnChainTransactionTask tTask = transactionTasks.get(tTaskName);
        if (tTask != null) {
            tTask.stop();
        }
    }

}
