package com.higgs.network.wallet.common.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class VerifySign {

    /**
     * 使用 Map按key进行排序拼接参数加密串
     * @param map
     * @return
     */
    public static Boolean ParameterSign(Map<String, Object> map,String key, String sign) {

        if(sign.equals(getSign(map,key))){
            return true;
        }
        else {
//            System.out.println(sign);
//            System.out.println(getSign(map,key));
            return false;
        }
    }

    /**
     * 使用 Map按key进行排序拼接参数加密串
     * @param map
     * @return
     */
    public static String getSign(Map<String, Object> map,String salt) {
        return MD5.getMD5(getVerifySignString(map,salt).toLowerCase());
    }



    /**
     * 使用 Map按key进行排序拼接参数加密串
     * @param map
     * @return
     */
    public static String getVerifySignString(Map<String, Object> map,String salt) {
        Map<String, Object> sortMap = sortMapByKey(map);
        String str = "";
        int i=0;
        for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
            if(entry.getKey()!="sign"){
                str += entry.getKey() + "=" + String.valueOf(entry.getValue());
                if(i!=sortMap.size()-1){
                    str+="&";
                }
                i++;
            }
        }
        str=str+salt;
        System.out.println("getSign str=salt:"+str.toLowerCase());
        return str.toLowerCase();
    }

    /** 专门给调用交易所接口使用的加密规则
     * 使用 Map按key进行排序拼接参数加密串
     * @param map
     * @return
     */
    public static String getBitGameExchangeSign(Map<String, Object> map,String salt) {
        map.put("key",salt);
        Map<String, Object> sortMap = sortMapByKey(map);
        String str = "";
        int i=0;
        for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
            if(entry.getKey()!="sign"){
                str += entry.getKey() + "=" + String.valueOf(entry.getValue());
                if(i!=sortMap.size()-1){
                    str+="&";
                }
            }
            i++;
        }
        System.out.println("str "+str);
        return MD5.getMD5(str);
    }

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Object> sortMap = new TreeMap<String, Object>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }


    private static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }

    public String getBitGameHeaderAppID(){
        return "";
    }

    public String getBitGameHeaderSign(){

        String sign= String.valueOf(System.currentTimeMillis());
        return MD5.getMD5(sign);
    }

}
