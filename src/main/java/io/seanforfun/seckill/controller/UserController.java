package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.domain.UserFactory;
import io.seanforfun.seckill.entity.vo.ForgetPasswordVo;
import io.seanforfun.seckill.entity.vo.LoginVo;
import io.seanforfun.seckill.entity.vo.RegisterVo;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.result.Result;
import io.seanforfun.seckill.service.RegisterService;
import io.seanforfun.seckill.service.UserService;
import io.seanforfun.seckill.service.ebi.LoginEbi;
import io.seanforfun.seckill.service.ebi.UserEbi;
import io.seanforfun.seckill.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 18:05
 * @description: Controllers to deal with user related request.
 * @modified:
 * @version: 0.0.1
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LoginEbi loginService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserEbi userService;

    /**
     * Path re-directory
     */
    @RequestMapping("/tologin")
    @ResponseBody
    public ModelAndView toLogin(ModelAndView mv){
        mv.setViewName("/pages/login.html");
        return mv;
    }

    @RequestMapping("/toRegister")
    public ModelAndView toRegister(ModelAndView mv){
        mv.setViewName("/pages/register.html");
        return mv;
    }

    @RequestMapping("/toForgetPassword")
    public ModelAndView toForgetPassword(@CookieValue(value = User.USER_TOKEN, required = false) String cookieToken,
                                         @RequestAttribute(value = User.USER_TOKEN, required = false) String requestToken,
                                         HttpSession session,
                                         HttpServletRequest request,
                                         HttpServletResponse response,
                                         ModelAndView mv) throws Exception {
        if(!StringUtils.isEmpty(cookieToken) || !StringUtils.isEmpty(requestToken)){
            String token = StringUtils.isEmpty(cookieToken) ? requestToken : cookieToken;
            loginService.logout(token, session, request, response);
        }
        mv.setViewName("/pages/forgetpassword.html");
        return mv;
    }

    @RequestMapping("/renderForgetPassword")
    @ResponseBody
    public Result<Boolean> renderForgetPassword(@CookieValue(value = User.USER_TOKEN, required = false) String cookieToken,
                                                @RequestAttribute(value = User.USER_TOKEN, required = false) String requestToken,
                                                HttpSession session,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws Exception{
        if(!StringUtils.isEmpty(cookieToken) || !StringUtils.isEmpty(requestToken)){
            String token = StringUtils.isEmpty(cookieToken) ? requestToken : cookieToken;
            loginService.logout(token, session, request, response);
        }
        return Result.success(true);
    }

    /**
     * AJAX methods
     */
    @RequestMapping("/login")
    @ResponseBody
    public Result<User> login(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User loginUser = loginService.login(request, response, loginVo);
        return Result.success(loginUser);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Result<Boolean> logout(@CookieValue(value = User.USER_TOKEN, required = false) String userToken,
                                  @RequestParam(value = User.USER_TOKEN, required = false) String paramToken,
                                  HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if(StringUtils.isEmpty(userToken) && StringUtils.isEmpty(paramToken)){
            return Result.error(CodeMsg.USER_NOT_LOGIN_ERROR_MSG);
        }
        String token = StringUtils.isEmpty(userToken) ? paramToken : userToken;
        loginService.logout(token, session, request, response);
        return Result.success(true);
    }

    @RequestMapping("/register")
    @ResponseBody
    public Result<Boolean> register(@Valid RegisterVo registerVo){
        User user = UserFactory.USER_FACTORY.produceUser();
        user.setUsername(registerVo.getUsername());
        user.setFirstname(registerVo.getFirstname());
        user.setLastname(registerVo.getLastname());
        user.setCountry(registerVo.getCountry());
        user.setState(registerVo.getState());
        user.setEmail(registerVo.getEmail());
        user.setZip(registerVo.getZip());
        String dbPassword = MD5Utils.httpPassToDbPass(registerVo.getPassword(), user.getSalt());
        user.setPassword(dbPassword);
        registerService.registerUser(user);
        return Result.success(true);
    }

    @RequestMapping("/resetPassword")
    @ResponseBody
    public Result<Boolean> changePassword(ForgetPasswordVo forgetPasswordVo, HttpServletRequest request, HttpSession session){
        String username = forgetPasswordVo.getUsername();
        String email = forgetPasswordVo.getEmail();
        User user = UserFactory.USER_FACTORY.produceEmptyUser();
        user.setUsername(username);
        user.setEmail(email);
        String httpPass = forgetPasswordVo.getPassword();
        String salt = user.getSalt();
        String dbPass = MD5Utils.httpPassToDbPass(httpPass, salt);
        user.setPassword(dbPass);
        if(userService.exists(user)){
            if(!userService.checkLogout(user, request, session)){
                return Result.error(CodeMsg.USER_NOT_LOGOUT_ERROR_MSG);
            }
            userService.updateUserPassword(user);
        }else{
            return Result.error(CodeMsg.USER_NOT_FOUND_ERROR_MSG);
        }
        return  Result.success(true);
    }

}
