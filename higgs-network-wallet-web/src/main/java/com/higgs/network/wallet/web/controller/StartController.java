package com.higgs.network.wallet.web.controller;

import com.higgs.network.wallet.batch.AutoOnChainTaskFactory;
import com.higgs.network.wallet.batch.CallbackTaskFactory;
import com.higgs.network.wallet.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StartController {
    private static final Logger logger = LoggerFactory.getLogger(StartController.class);
    @Autowired
    CallbackTaskFactory callbackTaskFactory;
    @Autowired
    AutoOnChainTaskFactory autoOnChainTaskFactory;

//    @Autowired
//    private RedisService redisService;

    @RequestMapping("/startCallback")
    public void startCallback(){
        callbackTaskFactory.startCallbackTask();
    }

    @RequestMapping("/stopCallback")
    public void stopCallback(){
        callbackTaskFactory.stopCallbackTask();
    }

    @RequestMapping("/startAutoOnChain")
    public void startAutoOnChain(){
        autoOnChainTaskFactory.startAutoOnChainTask();
    }

    @RequestMapping("/stopAutoOnChain")
    public void stopAutoOnChain(){
        autoOnChainTaskFactory.stopAutoOnChainTask();
    }

}