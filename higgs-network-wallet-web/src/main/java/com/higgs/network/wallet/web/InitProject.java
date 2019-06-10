package com.higgs.network.wallet.web;

import com.higgs.network.wallet.batch.AutoOnChainTaskFactory;
import com.higgs.network.wallet.batch.CallbackTaskFactory;
import com.higgs.network.wallet.web.controller.PartnerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitProject implements ApplicationRunner {

    @Autowired
    CallbackTaskFactory callbackTaskFactory;
    @Autowired
    AutoOnChainTaskFactory autoOnChainTaskFactory;

    private static final Logger logger = LoggerFactory.getLogger(PartnerController.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("==========init project===========");
        callbackTaskFactory.startCallbackTask();
        autoOnChainTaskFactory.startAutoOnChainTask();
        logger.info("==========startCallbackTask===========");
    }
}