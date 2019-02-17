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

    private static String md5(String token){
        return DigestUtils.md5Hex(token);
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

    public static String userLoginSession(String fixedLoginSalt){
        return md5(fixedLoginSalt);
    }

    /**
     * Multi-thread safe
     * @param name name of image.
     * @param token Unique id of image.
     * @param path  Path load from properties.
     * @param len Length of the internal path
     * @return full path name of current image with given token.
     */
    public static String localImagePath(String name, String token, String path, int len){
        String md5Path = md5(token).substring(0, len);
        String separator = File.separator;
        String modifiedPath = path.replace("\\", separator).replace("/", separator) ;
        StringBuffer sb = new StringBuffer(modifiedPath);
        if(!sb.toString().substring(sb.length() - 1, sb.length()).equals(separator)){
            sb.append(separator);
        }
        for(int i = 0; i < len; i++){
            sb.append(md5Path.charAt(i));
            sb.append(separator);
        }
        sb.append(name);
        return sb.toString();
    }
}
