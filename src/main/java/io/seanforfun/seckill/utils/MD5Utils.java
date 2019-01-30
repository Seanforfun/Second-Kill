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

    private static String md5(String password){
        return DigestUtils.md5Hex(password);
    }

    public static String passToHttpPass(String password){
        return md5(password + FIX_SALT);
    }

    public static String httpPassToDbPass(String password, String salt){
        return md5(password + salt);
    }

    public static String passToDbPass(String password, String salt){
        return httpPassToDbPass(passToHttpPass(password), salt);
    }

    public static String userLoginSession(String username, String salt, String fixedLoginSalt){
        return md5(username + salt + fixedLoginSalt);
    }
}
