package com.higgs.network.wallet.common.util;

import net.sf.json.JSONObject;

public class JsonUtil {

    public static Object getObjectByJson(String sJson,Object obj){
        JSONObject jsonObject = JSONObject.fromObject(sJson);
        return JSONObject.toBean(jsonObject,obj.getClass());
    }
}
