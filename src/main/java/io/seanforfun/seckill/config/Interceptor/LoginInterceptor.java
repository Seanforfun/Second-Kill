package io.seanforfun.seckill.config.Interceptor;

import io.seanforfun.seckill.config.annotations.Access;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.redis.UserKey;
import io.seanforfun.seckill.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/30 21:35
 * @description: ${description}
 * @modified:
 * @version: 0.01
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String loginToken = (String)session.getAttribute(User.USER_LOGIN);
        if (StringUtils.isEmpty(loginToken)){
            notLoginHandler(request, response);
            return false;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            notLoginHandler(request, response);
            return false;
        }
        for (Cookie cookie : cookies){
            if (cookie.getName().equals(User.USER_TOKEN)){
                String key = cookie.getValue();
                if(!redisService.exists(UserKey.userToken, key)) {
                    notLoginHandler(request, response);
                    return false;
                }
                User user = redisService.get(UserKey.userToken, key, User.class);
                if(user.checkUserLogin(loginToken)){
                   return true;
                }
            }
        }
        notLoginHandler(request, response);
        return false;
    }

    private void notLoginHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/user/tologin").forward(request, response);
    }
}
