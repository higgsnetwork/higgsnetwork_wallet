package com.higgs.network.wallet.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import org.apache.commons.codec.digest.DigestUtils;


public class MD5 {

//    public static String getMD5(String str){
//        MessageDigest md5 = null;
//        try{
//            md5 = MessageDigest.getInstance("MD5");
//        }catch (Exception e){
//            System.out.println(e.toString());
//            e.printStackTrace();
//            return "";
//        }
//        char[] charArray = str.toCharArray();
//        byte[] byteArray = new byte[charArray.length];
//        for (int i = 0; i < charArray.length; i++)
//            byteArray[i] = (byte) charArray[i];
//        byte[] md5Bytes = md5.digest(byteArray);
//        StringBuffer hexValue = new StringBuffer();
//        for (int i = 0; i < md5Bytes.length; i++){
//            int val = ((int) md5Bytes[i]) & 0xff;
//            if (val < 16)
//                hexValue.append("0");
//            hexValue.append(Integer.toHexString(val));
//        }
//        System.out.println(hexValue.toString().toLowerCase());
//        return hexValue.toString().toLowerCase();
//    }

    public static String getMD5(String str){
        return DigestUtils.md5Hex(str);
    }
}
