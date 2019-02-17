package io.seanforfun.seckill.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 19:50
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class ValidatorUtils {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    public static boolean isEmail(String src){
        if(StringUtils.isEmpty(src)){
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(src);
        return matcher.matches();
    }
}
