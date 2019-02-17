package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.result.Result;
import io.seanforfun.seckill.service.ebi.LoginEbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/30 13:09
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @Autowired
    private LoginEbi loginService;

    @RequestMapping("/error/toError")
    @ResponseBody
    public ModelAndView toError(@CookieValue(value = User.USER_TOKEN, required = false) String userToken,
                                @RequestParam(value = User.USER_TOKEN, required = false) String paramToken,
                                HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                ModelAndView mv) throws Exception {
        if(!StringUtils.isEmpty(userToken) || !StringUtils.isEmpty(paramToken)){
            String token = StringUtils.isEmpty(userToken) ? paramToken : userToken;
            loginService.logout(token, session, request, response);
        }
        mv.setViewName("pages/404");
        return mv;
    }

    @RequestMapping(value = "/error")
    public String error() {
        return "pages/404";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
