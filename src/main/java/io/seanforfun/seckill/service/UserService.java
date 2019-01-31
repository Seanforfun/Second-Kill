package io.seanforfun.seckill.service;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import io.seanforfun.seckill.dao.UserDao;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.redis.UserKey;
import io.seanforfun.seckill.service.ebi.UserEbi;
import io.seanforfun.seckill.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    @Autowired
    private UserDao userDao;

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

    @Override
    public boolean exists(User user) {
        return userDao.getUserNumberByUsernameAndEmail(user) > 0;
    }

    @Override
    public boolean checkLogout(User user, HttpServletRequest request, HttpSession session) {
        String loginToken = (String )session.getAttribute(User.USER_LOGIN);
        if(!StringUtils.isEmpty(loginToken)){
            return false;
        }
        String cookieValue = CookieUtils.getValueFromCookie(request.getCookies(), User.USER_TOKEN);
        return StringUtils.isEmpty(cookieValue);
    }

    @Override
    @Transactional
    public void updateUserPassword(User user) {
        userDao.updateUserPasswordAndSalt(user);
    }

    protected void updateUserSession(String token, User user, HttpServletResponse response){
        redisService.set(UserKey.userToken, token,  user);
        Cookie cookie = new Cookie(User.USER_TOKEN, token);
        cookie.setMaxAge(UserKey.userToken.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
