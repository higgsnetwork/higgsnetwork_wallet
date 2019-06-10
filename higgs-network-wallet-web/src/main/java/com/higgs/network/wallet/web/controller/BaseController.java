package com.higgs.network.wallet.web.controller;

import com.higgs.network.wallet.common.stub.ResultCode;
import com.higgs.network.wallet.common.util.MapUtil;
import com.higgs.network.wallet.common.util.VerifySign;
import com.higgs.network.wallet.domain.ConfigCoinSymbol;
import com.higgs.network.wallet.domain.PartnerInfo;
import com.higgs.network.wallet.domain.PartnerSymbol;
import com.higgs.network.wallet.service.PartnerService;
import com.higgs.network.wallet.service.SymbolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private SymbolService symbolService;

    /**
     * 参数检查及sign检测函数封装
     * @param Parameter
     * @return
     */
    public Integer checkSignByObjParameter(Object Parameter,Integer platformid,String sign) {
        try {
            Map<String, Object> parameterMap = MapUtil.objectToMap(Parameter);
            return checkSignByMap(parameterMap,platformid,sign);
        }catch (Exception e){
            logger.error("接口参数转换异常 {}",e.getMessage());
            return ResultCode.Error;
        }
    }

    public Integer checkSignByMap(Map<String, Object> parameterMap,Integer platformid,String sign){
        try {
            if(parameterMap==null) {
                //参数异常
                return ResultCode.ParameterNull;
            }
            PartnerInfo partnerInfo = partnerService.getPartnerInfoByID(platformid);
            String key = partnerInfo.getKey();
            //按照参数map排序并验证sign
            if(!VerifySign.ParameterSign(parameterMap,key,sign)){
                //签名验证失败
                logger.error("checkPlatformSign 验签失败");
                return ResultCode.SignError;
            }
        }catch (Exception e){
            logger.error("接口参数转换异常 {}",e.getMessage());
            return ResultCode.Error;
        }
        return ResultCode.SUCCESS;
    }

    //检测充币状态
    public Integer checkPartnerAndSymbolDepositOpen(Integer platformid,String symbol){

        //检查合作方状态
        PartnerInfo partnerInfo = partnerService.getPartnerInfoByID(platformid);
        if(partnerInfo == null || partnerInfo.getStatus()!=1){
            logger.warn("检测合作方信息 不存在或未开放 platformid={}",platformid);
            return ResultCode.PartnerNotOpen;
        }

        //检查币种状态，提币状态
        ConfigCoinSymbol configSymbol = symbolService.getConfigCoinSymbolBySymbol(symbol);
        if(configSymbol == null || configSymbol.getIsOpen() != 1 || configSymbol.getDepositOpen() != 1){
            logger.warn("检测合作方充币信息 ConfigCoinSymbol 不存在或未开放 platformid={}",platformid);
            return ResultCode.SymbolError;
        }

        PartnerSymbol partnerSymbol = symbolService.getPartnerSymbolByPatnerId(platformid,symbol);
        if(partnerSymbol == null || partnerSymbol.getIsOpen() != 1 || partnerSymbol.getDepositOpen() != 1){
            logger.warn("检测合作方充币信息 ConfigCoinSymbol 不存在或未开放 platformid={}",platformid);
            return ResultCode.SymbolError;
        }
        //合作方的币种开放验证
        return ResultCode.SUCCESS;
    }

    //检测提币状态
    public Integer checkPartnerAndSymbolWithdrawOpen(Integer platformid,String symbol){

        //检查合作方状态
        PartnerInfo partnerInfo = partnerService.getPartnerInfoByID(platformid);
        if(partnerInfo == null || partnerInfo.getStatus()!=1){
            logger.warn("检测合作方信息 不存在或未开放 platformid={}",platformid);
            return ResultCode.PartnerNotOpen;
        }

        //检查币种状态，提币状态
        ConfigCoinSymbol configSymbol = symbolService.getConfigCoinSymbolBySymbol(symbol);
        if(configSymbol == null || configSymbol.getIsOpen() != 1 || configSymbol.getDepositOpen() != 1){
            logger.warn("检测合作方提币信息 ConfigCoinSymbol 不存在或未开放 platformid={}",platformid);
            return ResultCode.SymbolError;
        }

        PartnerSymbol partnerSymbol = symbolService.getPartnerSymbolByPatnerId(platformid,symbol);
        if(partnerSymbol == null || partnerSymbol.getIsOpen() != 1 || partnerSymbol.getWithdrawOpen() != 1){
            logger.warn("检测合作方提币信息 PartnerSymbol 不存在或未开放 platformid={}",platformid);
            return ResultCode.SymbolError;
        }
        //合作方的币种开放验证
        return ResultCode.SUCCESS;
    }

}
