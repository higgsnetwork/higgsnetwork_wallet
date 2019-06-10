package com.higgs.network.wallet.common.util;

import java.io.*;
import java.util.Date;

/**
 *
 * @author :
 * @date :
 */
public class FileUtil {

    public static void inputStreamToOutputStream(InputStream ins, OutputStream ous) throws IOException{
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = ins.read(buffer)) != -1) {
            ous.write(buffer, 0, len);
        }
        ous.flush();
    }
    public static void inputstreamToFile(InputStream ins, File file) throws IOException{
        FileOutputStream baos=new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = ins.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        baos.close();
    }

    public static void closeInputStream(InputStream ins)throws IOException{
        if(ins!=null){
            ins.close();
        }
    }
    public static void closeOutputStream(OutputStream ous)throws IOException{
        if(ous!=null){
            ous.close();
        }
    }

    public static String genUniqueFileName(long time,String key){
        String nkey=key.replace("/","-");
        return time+"-"+nkey;
    }

    public static void main(String[] args){
        String str=genUniqueFileName(new Date().getTime(),"upload/1231231232132.jpg");
        System.out.println(str);
    }
}
