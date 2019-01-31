package io.seanforfun.seckill.config.Interceptor;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.service.UserService;
import io.seanforfun.seckill.service.ebi.UserEbi;
import io.seanforfun.seckill.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/31 12:07
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
public class UserInterceptor {

    @Autowired
    private UserEbi userService;

    protected void notLoginHandler(HttpServletResponse response) throws Exception{
        response.sendRedirect("/user/tologin");
    }

    protected boolean checkUserLogin(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        String loginToken = (String)session.getAttribute(User.USER_LOGIN);
        if (StringUtils.isEmpty(loginToken)){
            notLoginHandler(response);
            return false;
        }
        Cookie[] cookies = request.getCookies();
        String cookieToken = CookieUtils.getValueFromCookie(cookies, User.USER_TOKEN);
        String requestToken = request.getParameter(User.USER_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(requestToken)){
            notLoginHandler(response);
            return false;
        }
        String token = StringUtils.isEmpty(cookieToken) ? requestToken : cookieToken;
        if(!userService.userInRedisSession(token)){
            notLoginHandler(response);
            return false;
        }
        return true;
    }

}
