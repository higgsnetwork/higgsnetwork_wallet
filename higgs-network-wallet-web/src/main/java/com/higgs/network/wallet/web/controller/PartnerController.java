package com.higgs.network.wallet.web.controller;

import com.higgs.network.wallet.common.stub.ResultMessage;
import com.higgs.network.wallet.common.stub.Result;
import com.higgs.network.wallet.common.stub.ResultCode;
import com.higgs.network.wallet.domain.*;
import com.higgs.network.wallet.service.AddressService;
import com.higgs.network.wallet.service.PartnerService;
import com.higgs.network.wallet.service.SymbolService;
import com.higgs.network.wallet.service.TransactionService;
import com.higgs.network.wallet.stub.NewAddress;
import com.higgs.network.wallet.stub.PartnerSymbolInfo;
import com.higgs.network.wallet.stub.SymbolInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/partner")
public class PartnerController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PartnerController.class);

    @Autowired
    private AddressService addressService;

    @Autowired
    private SymbolService symbolService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/hello")
    public String hello(){
        return "hello Partner";
    }

    private String funtionName="";

    /**
     * 申请新的钱包地址
     * @return
     */
    @PostMapping(path = "/newaddress", consumes = "application/json", produces = "application/json")
    public Result<JSONObject> getNewAddress(@RequestBody ApplyForNewAddressParameter param) {
        funtionName = "getNewAddress";
        logger.info("{} Begin", funtionName);
        Long nowTimestamp = System.currentTimeMillis();
        if (param == null) {
            //参数异常
            logger.warn("{} 申请地址接口参数异常", funtionName);
            return Result.fail(ResultCode.ParameterError, ResultMessage.RESULTMSGBOX.get(ResultCode.ParameterError));
        } else if (param.getPartnerId() <= 0 || StringUtils.isBlank(param.getPartnerUid()) || StringUtils.isBlank(param.getSymbol()) || nowTimestamp - param.getTimestamp() > 60 * 1000 || StringUtils.isBlank(param.getSign())) {
            //参数不全
            logger.warn("{} 申请地址接口参数不全", funtionName);
            return Result.fail(ResultCode.ParameterNull, ResultMessage.RESULTMSGBOX.get(ResultCode.ParameterNull));
        }

        //检测sign
        Integer code = checkSignByObjParameter(param, param.getPartnerId(), param.getSign());
        if (code != ResultCode.SUCCESS) {
            //状态异常，返回对应code
            logger.warn("{} 申请地址接口参数检测验签错误 PartnerId={} Symbol={}", funtionName, param.getPartnerId(), param.getSymbol());
            return Result.fail(code, ResultMessage.RESULTMSGBOX.get(code));
        }

        //检测合作方状态和币种状态
        code = checkPartnerAndSymbolDepositOpen(param.getPartnerId(), param.getSymbol());
        if (code != ResultCode.SUCCESS) {
            //状态异常，返回对应code
            logger.warn("{} 申请地址接口币种错误 PartnerId={} Symbol={}", funtionName, param.getPartnerId(), param.getSymbol());
            return Result.fail(code, ResultMessage.RESULTMSGBOX.get(code));
        }

        //得到主链名
        String token_base = symbolService.getTokenBaseBySymbol(param.getSymbol());
        if (StringUtils.isBlank(token_base)) {
            logger.warn("{} 申请地址接口参数检测验签错误 PartnerId={} Symbol={}", funtionName, param.getPartnerId(), param.getSymbol());
            return Result.fail(ResultCode.FAILED, ResultMessage.RESULTMSGBOX.get(ResultCode.FAILED));
        }

        NewAddress newAddress = new NewAddress();
        SystemAddress systemAddress = addressService.getSystemAddressByBasetoken(token_base.toUpperCase());
        String addressPrefix = "";
        if(systemAddress!=null){
            //EOS&IOST两个主链固定的Higgs钱包地址，拼好结构发给调用方
            addressPrefix = systemAddress.getAddress()+"|";
        }
        //查询用户地址
        AddressBindInfo abi = addressService.getAddressBindInfoByUidAndTokenBase(param.getPartnerId(), param.getPartnerUid(), token_base);
        if (abi == null) {
            //根据主链获得一个未使用的地址
            String address = addressService.getAddressIsNotUse(token_base.toLowerCase());
            if (!StringUtils.isBlank(address)) {
                //得到一个未使用的地址，进行合作方信息绑定
                if (addressService.bindAddressToPartner(param.getPartnerId(),param.getPartnerUid(), address, param.getSymbol(), token_base)) {
                    //得到一个未使用的地址，进行合作方信息绑定7
                    newAddress.setAddress(addressPrefix+address);//普通地址直接返回，EOS&IOST返回Address|number格式
                    newAddress.setSymbol(param.getSymbol());
                    newAddress.setBaseToken(token_base);
                    JSONObject jsonmap = new JSONObject();
                    jsonmap.put("AddressInfo", newAddress);
                    return Result.success(jsonmap, ResultMessage.RESULTMSGBOX.get(ResultCode.SUCCESS));
                }
                return Result.fail(ResultCode.FAILED, ResultMessage.RESULTMSGBOX.get(ResultCode.FAILED));
            } else {
                //没有空地址
                logger.warn("{} 申请地址接口没有空地址 PartnerId= Symbol={}", funtionName, param.getPartnerId(), param.getSymbol());
                return Result.fail(ResultCode.FAILED, ResultMessage.RESULTMSGBOX.get(ResultCode.FAILED));
            }
        }
        else{
            newAddress.setAddress(addressPrefix+abi.getAddress());//普通地址直接返回，EOS&IOST返回Address|number格式
            newAddress.setSymbol(param.getSymbol());
            newAddress.setBaseToken(token_base);
            JSONObject jsonmap = new JSONObject();
            jsonmap.put("AddressInfo", newAddress);
            return Result.success(jsonmap, ResultMessage.RESULTMSGBOX.get(ResultCode.SUCCESS));
        }
    }

    /**
     * 提币申请
     * @return
     */
    @PostMapping(path = "/createwithdraw", consumes = "application/json", produces = "application/json")
    public Result<JSONObject> createWithdraw(@RequestBody CreateWithdrawParameter param) {
        funtionName="createWithdraw";
        logger.info("{} Begin", funtionName);
        Long nowTimestamp = System.currentTimeMillis();
        if (param == null) {
            //参数异常
            logger.warn("{} 创建提币订单接口参数异常",funtionName);
            return Result.fail(ResultCode.ParameterError, ResultMessage.RESULTMSGBOX.get(ResultCode.ParameterError));
        }else if(param.getPartnerId() <= 0 || StringUtils.isBlank(param.getSymbol()) || nowTimestamp - param.getTimestamp() > 60 * 1000 || StringUtils.isBlank(param.getSign())){
            //参数不全
            logger.warn("{} 创建提币订单接口参数不全",funtionName);
            return Result.fail(ResultCode.ParameterNull, ResultMessage.RESULTMSGBOX.get(ResultCode.ParameterNull));
        }

        //检测sign
        Integer code = checkSignByObjParameter(param, param.getPartnerId(), param.getSign());
        if (code != ResultCode.SUCCESS) {
            //状态异常，返回对应code
            logger.warn("{} 创建提币订单接口参数检测验签错误 PartnerId={} Symbol={} OrderNo={}",funtionName,param.getPartnerId(),param.getSymbol(),param.getPartnerOrderNo());
            return Result.fail(code, ResultMessage.RESULTMSGBOX.get(code));
        }

        //检测合作方状态和币种状态
        code = checkPartnerAndSymbolWithdrawOpen(param.getPartnerId(), param.getSymbol());
        if (code != ResultCode.SUCCESS) {
            //状态异常，返回对应code
            logger.warn("{} 创建提币订单接口币种状态错误 PartnerId={} Symbol={}",funtionName,param.getPartnerId(),param.getSymbol());
            return Result.fail(code, ResultMessage.RESULTMSGBOX.get(code));
        }

        if(!symbolService.getSymbolWithdrawStatusByPatnerId(param.getPartnerId(),param.getSymbol().toUpperCase())){
            //检测币种是否对合作方开放提币
            logger.warn("{} 创建提币订单接口币种提币状态对合作方未配置或关闭 PartnerId={} Symbol={}",funtionName,param.getPartnerId(),param.getSymbol());
            return Result.fail(ResultCode.SymbolError, ResultMessage.RESULTMSGBOX.get(ResultCode.SymbolError));
        }


        PartnerInfo partnerInfo = partnerService.getPartnerInfoByID(param.getPartnerId());
        //如果不是测试项目 并且 项目状态不是沙盒，检测订单金额是否可以提现，总充值-(总提币+当前提币订单金额)>=0
        if(partnerInfo.getSendbox()!=1 && param.getPartnerId()!=1) {
            //验证订单金额是否超限
            code = transactionService.checkOrder(param);
            if (code != ResultCode.SUCCESS) {
                logger.warn("{} 创建提币订单接口金额超限 PartnerId={} PartnerOrderNo={} Symbol={} amount={}", funtionName, param.getPartnerId(), param.getPartnerOrderNo(), param.getSymbol(), param.getAmount());
                return Result.fail(code, ResultMessage.RESULTMSGBOX.get(code));
            }
        }

        //创建订单
        code = transactionService.createOrder(param);
        if (code != ResultCode.SUCCESS) {
            //状态异常，返回对应code
            logger.warn("{} 申请地址接口参数检测验签错误 PartnerId={} Symbol={} OrderNo={}",funtionName,param.getPartnerId(),param.getSymbol(),param.getPartnerOrderNo());
            return Result.fail(code, ResultMessage.RESULTMSGBOX.get(code));
        }

        JSONObject jsonmap=new JSONObject();
        return Result.successMsg(ResultMessage.RESULTMSGBOX.get(ResultCode.SUCCESS));
    }

    /**
     * 对合作方开放的币种列表
     * @return
     */
    @PostMapping(path = "/symbolopenlist", consumes = "application/json", produces = "application/json")
    public Result<JSONObject> getSymbolOpenList(@RequestBody SymbolOpenListParameter param){
        funtionName="getSymbolOpenList";
        logger.info("{} Begin", funtionName);
        Long nowTimestamp = System.currentTimeMillis();
        if (param == null) {
            //参数异常
            logger.warn("{} 获取币种开放列表接口参数异常",funtionName);
            return Result.fail(ResultCode.ParameterError, ResultMessage.RESULTMSGBOX.get(ResultCode.ParameterError));
        }else if(param.getPartnerId() <= 0 || nowTimestamp - param.getTimestamp() > 60 * 1000 || StringUtils.isBlank(param.getSign())){
            //参数不全
            logger.warn("{} 获取币种开放列表接口参数不全",funtionName);
            return Result.fail(ResultCode.ParameterNull, ResultMessage.RESULTMSGBOX.get(ResultCode.ParameterNull));
        }

        //检测sign
        Integer code = checkSignByObjParameter(param, param.getPartnerId(), param.getSign());
        if (code != ResultCode.SUCCESS) {
            //状态异常，返回对应code
            logger.warn("{} 获取币种开放列表接口参数检测验签错误 PartnerId={}",funtionName,param.getPartnerId());
            return Result.fail(code, ResultMessage.RESULTMSGBOX.get(code));
        }

        //获取给合作方配置好的已开放币列表
        List<PartnerSymbol> partnerSymbolList = symbolService.getPartnerSymbolListByPatnerIdAndIsOpen(param.getPartnerId());
        JSONObject jsonmap=new JSONObject();
        List<PartnerSymbolInfo> partnerSymbolInfos=new ArrayList<>();
        if(partnerSymbolList!=null)
        {
            ConfigCoinSymbol configCoinSymbol;
            for (PartnerSymbol item : partnerSymbolList){
                //根据币名查询币种配置信息
                configCoinSymbol = symbolService.getConfigCoinSymbolBySymbol(item.getSymbol());
                if(configCoinSymbol!=null)
                {
                    //填充返回数据
                    PartnerSymbolInfo psinfo = new PartnerSymbolInfo();
                    psinfo.setSymbol(item.getSymbol());
                    psinfo.setTokenBase(configCoinSymbol.getTokenBase());
                    psinfo.setContratPrecision(configCoinSymbol.getContractPrecision());
                    psinfo.setShowPrecision(configCoinSymbol.getShowPrecision());
                    psinfo.setWithdrawMin(configCoinSymbol.getWithdrawMin().toString());
                    psinfo.setWithdrawMax(configCoinSymbol.getWithdrawMax().toString());

                    //两个表提币状态都是开放=true
                    if(item.getWithdrawOpen()==1 && configCoinSymbol.getWithdrawOpen()==1 && configCoinSymbol.getReleaseStatus()==2){
                        psinfo.setWithdrawOpen(true);
                    }else{
                        psinfo.setWithdrawOpen(false);
                    }

                    //两个表充币状态都是开放=ture
                    if(item.getDepositOpen()==1 && configCoinSymbol.getDepositOpen()==1 && configCoinSymbol.getReleaseStatus()==2){
                        psinfo.setDepositOpen(true);
                    }else{
                        psinfo.setDepositOpen(false);
                    }
                    partnerSymbolInfos.add(psinfo);
                }

                jsonmap.put("symbols",partnerSymbolInfos);
            }
        }
        return Result.success(jsonmap,ResultMessage.RESULTMSGBOX.get(ResultCode.SUCCESS));
    }


    /**
     * 全部支持的币种
     * @return
     */
    @RequestMapping("/allsymbol")
    public Result<JSONObject> getConfigSymbolList() {
        funtionName="getConfigSymbolList";
        logger.info("{} Begin", funtionName);
        List<ConfigCoinSymbol> configCoinSymbolList = symbolService.getAllSymbolListIsOpen();
        JSONObject jsonmap = new JSONObject();
        List<PartnerSymbolInfo> partnerSymbolInfos = new ArrayList<>();

        for (ConfigCoinSymbol item : configCoinSymbolList) {
            //根据币名查询币种配置信息
            //填充返回数据
            PartnerSymbolInfo psinfo = new PartnerSymbolInfo();
            psinfo.setSymbol(item.getCoinSymbol());
            psinfo.setTokenBase(item.getTokenBase());
            psinfo.setContratPrecision(item.getContractPrecision());
            psinfo.setShowPrecision(item.getShowPrecision());
            psinfo.setWithdrawMin(item.getWithdrawMin().toString());
            psinfo.setWithdrawMax(item.getWithdrawMax().toString());

            //提币状态都是开放=true
            if (item.getWithdrawOpen() == 1 && item.getReleaseStatus()==2) {
                psinfo.setWithdrawOpen(true);
            } else {
                psinfo.setWithdrawOpen(false);
            }

            //充币状态都是开放=ture
            if (item.getDepositOpen() == 1 && item.getReleaseStatus()==2) {
                psinfo.setDepositOpen(true);
            } else {
                psinfo.setDepositOpen(false);
            }
            partnerSymbolInfos.add(psinfo);
        }

        jsonmap.put("symbols", partnerSymbolInfos);
        return Result.success(jsonmap, ResultMessage.RESULTMSGBOX.get(ResultCode.SUCCESS));
    }

    /**
     * 检测地址是否可用
     * @return true可以使用,false不可使用
     */
    @PostMapping(path = "/checkaddress", consumes = "application/json", produces = "application/json")
    public Result<JSONObject> checkaddress(@RequestBody CheckAddressParameter param){
        funtionName="checkaddress";
        logger.info("{} Begin", funtionName);
        Long nowTimestamp = System.currentTimeMillis();
        if (param == null) {
            //参数异常
            logger.warn("{} 检查地址接口参数异常",funtionName);
            return Result.fail(ResultCode.ParameterError, ResultMessage.RESULTMSGBOX.get(ResultCode.ParameterError));
        }else if(param.getPartnerId() <= 0 || StringUtils.isBlank(param.getAddrss()) || nowTimestamp - param.getTimestamp() > 60 * 1000 || StringUtils.isBlank(param.getSign())){
            //参数不全
            logger.warn("{} 检查地址接口参数不全",funtionName);
            return Result.fail(ResultCode.ParameterNull, ResultMessage.RESULTMSGBOX.get(ResultCode.ParameterNull));
        }

        //检测sign
        Integer code = checkSignByObjParameter(param, param.getPartnerId(), param.getSign());
        if (code != ResultCode.SUCCESS) {
            //状态异常，返回对应code
            logger.warn("{} 检查地址接口参数检测验签错误 PartnerId={}",funtionName,param.getPartnerId());
            return Result.fail(code, ResultMessage.RESULTMSGBOX.get(code));
        }

        JSONObject jsonmap = new JSONObject();
        jsonmap.put("status", addressService.checkAddressByPartnerID(param.getPartnerId(),param.getAddrss()));
        return Result.success(jsonmap, ResultMessage.RESULTMSGBOX.get(ResultCode.SUCCESS));
    }
}