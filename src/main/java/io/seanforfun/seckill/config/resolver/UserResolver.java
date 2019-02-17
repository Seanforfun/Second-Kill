package io.seanforfun.seckill.config.resolver;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.service.UserService;
import io.seanforfun.seckill.service.ebi.UserEbi;
import io.seanforfun.seckill.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/31 10:12
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
public class UserResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserEbi userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String requestToken = request.getParameter(User.USER_TOKEN);
        Cookie[] cookies = request.getCookies();
        String cookieToken = CookieUtils.getValueFromCookie(cookies, User.USER_TOKEN);
        if(StringUtils.isEmpty(requestToken) && StringUtils.isEmpty(cookieToken)){
            return null;
        }
        String token = StringUtils.isEmpty(requestToken) ? cookieToken : requestToken;
        User userByToken = userService.getUserByToken(response, token);
        modelAndViewContainer.addAttribute("user", userByToken);
        return userByToken;
    }
}
