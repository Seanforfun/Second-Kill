package io.seanforfun.seckill.utils;

import io.seanforfun.seckill.entity.domain.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/31 10:54
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class CookieUtils {

    /**
     * Get value from cookies by key.
     * @param cookies
     * @param key
     * @return String value
     */
    public static String getValueFromCookie(Cookie[] cookies, String key) {
        if(StringUtils.isEmpty(key) || cookies == null){
            return null;
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(key)){
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * @return Return a cookie for UserToken expiration.
     */
    public static Cookie createExpireUserCookie(){
        Cookie cookie = new Cookie(User.USER_TOKEN, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }

}
