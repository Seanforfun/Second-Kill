package io.seanforfun.seckill.utils;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/26 10:37
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class MD5Utils {
    private static final String FIX_SALT = "ILOVEIRENE";

    private String md5(String password){
        return DigestUtils.md2Hex(password);
    }

    public static String passToHttpPass(String password){
        // TODO Need to fill this ustils.
        return null;
    }
}
