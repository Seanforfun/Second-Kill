package io.seanforfun.seckill.config.Interceptor;

import io.seanforfun.seckill.entity.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/31 11:59
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Component
public class RememberMeInterceptor extends  UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Step 1: Check if current user has login.
        checkUserLogin(request, response);
        // Step 2: Check if need to remember me logic.
        String rememberMe = (String) request.getSession().getAttribute(User.USER_REMEMBER_ME);
        if(checkRememberMe(rememberMe)){
            response.sendRedirect("/manage/toManage");
            return false;
        }
        return true;
    }

    private boolean checkRememberMe(String current){
        if(StringUtils.isEmpty(current)){
            return false;
        }
        return current.equals(User.USER_REMEMBER_ME_TOKEN);
    }

    @Override
    public void notLoginHandler(HttpServletResponse response) throws Exception {

    }
}
