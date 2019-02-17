package io.seanforfun.seckill.config.Interceptor;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.service.ebi.UserEbi;
import io.seanforfun.seckill.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

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
@Service
public class LoginInterceptor extends  UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return checkUserLogin(request, response);
    }
}
