package com.higgs.network.wallet.common.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * 用于产生地址
 * @author :
 * @date :
 */
public class DMAddressUtil {
    private static final String USER_SALT = "fF3Db,2DK3i,K2Bc3,Co2Db,2Fo22";
    private static int SALT_LENGTH = 20;
    public static String PREFIX_USER_ADDR = "0x1";
    public static String PREFIX_DM_LOGIN_ADDR = "0x2";
    public static String PREFIX_DM_DEAL_ADDR = "0x3";
    public static String PREFIX_DM_LOTTERY_ADDR = "0x4";

    /**
     * @param eventId
     * @return  prefix+md_32
     */
    public static String genAddrForMLoginEvent(String eventId){
        //随机数生成器
        SecureRandom random = new SecureRandom();
        //声明盐数组变量
        byte[] salt = new byte[SALT_LENGTH];
        //将随机数放入盐变量中
        random.nextBytes(salt);
        return PREFIX_DM_LOGIN_ADDR+generateSaltedHash(eventId,salt);
    }
    /**
     * @param eventId
     * @return  prefix+md_32
     */
    public static String genAddrForMDealEvent(String eventId){
        //随机数生成器
        SecureRandom random = new SecureRandom();
        //声明盐数组变量
        byte[] salt = new byte[SALT_LENGTH];
        //将随机数放入盐变量中
        random.nextBytes(salt);
        return PREFIX_DM_DEAL_ADDR+generateSaltedHash(eventId,salt);
    }
    /**
     * @param eventId
     * @return  prefix+md_32
     */
    public static String genAddrForMLottEvent(String eventId){
        //随机数生成器
        SecureRandom random = new SecureRandom();
        //声明盐数组变量
        byte[] salt = new byte[SALT_LENGTH];
        //将随机数放入盐变量中
        random.nextBytes(salt);
        return PREFIX_DM_LOTTERY_ADDR+generateSaltedHash(eventId,salt);
    }

    /**
     * @param uid
     * @return  prefix+md_32
     */
    public static String genAddrForUser(String uid){
        byte[] salt=USER_SALT.getBytes();
        return PREFIX_USER_ADDR+generateSaltedHash(uid,salt);
    }

    /**
     * @param value
     * @param saltPhrase
     * @return  md_32
     */
    public static String generateSaltedHash(String value, byte[] saltPhrase) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(saltPhrase);
            byte[] salt = md.digest();
            md.reset();
            md.update(value.getBytes());
            md.update(salt);
            byte[] raw = md.digest();
            return convertByteToHex(raw);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertByteToHex(byte[] byteData) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String user = genAddrForUser("10001");
        System.out.println(user);
        user = genAddrForUser("10001");
        System.out.println(user);
        user = genAddrForUser("10001");
        System.out.println(user);
        user = genAddrForUser("10002");
        System.out.println(user);
        user = genAddrForUser("10002");
        System.out.println(user);
        String event = genAddrForMLoginEvent("1");
        System.out.println(event);
        event = genAddrForMDealEvent("1");
        System.out.println(event);
        event = genAddrForMLottEvent("1");
        System.out.println(event);
    }
}
