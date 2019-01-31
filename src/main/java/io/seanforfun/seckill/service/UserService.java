package io.seanforfun.seckill.service;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.redis.UserKey;
import io.seanforfun.seckill.service.ebi.UserEbi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/31 10:19
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
public class UserService implements UserEbi {

    @Autowired
    private RedisService redisService;

    @Override
    public User getUserByToken(HttpServletResponse response, String token) throws Exception {
        User user = null;
        if(!StringUtils.isEmpty(token)){
            user = redisService.get(UserKey.userToken, token, User.class);
        }
        updateUserSession(token, user, response);
        return user;
    }

    @Override
    public boolean userInRedisSession(String token) {
        return redisService.exists(UserKey.userToken, token);
    }

    protected void updateUserSession(String token, User user, HttpServletResponse response){
        redisService.set(UserKey.userToken, token,  user);
        Cookie cookie = new Cookie(User.USER_TOKEN, token);
        cookie.setMaxAge(UserKey.userToken.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
