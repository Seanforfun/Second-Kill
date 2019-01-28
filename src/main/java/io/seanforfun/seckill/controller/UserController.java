package io.seanforfun.seckill.controller;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.vo.LoginVo;
import io.seanforfun.seckill.entity.vo.RegisterVo;
import io.seanforfun.seckill.result.Result;
import io.seanforfun.seckill.service.LoginService;
import io.seanforfun.seckill.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 18:05
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RegisterService registerService;

    /**
     * Path re-directory
     */
    @RequestMapping("/tologin")
    public ModelAndView toLogin(ModelAndView mv){
        mv.setViewName("/pages/login.html");
        return mv;
    }

    @RequestMapping("/toRegister")
    public ModelAndView toRegister(ModelAndView mv){
        mv.setViewName("/pages/register.html");
        return mv;
    }

    /**
     * AJAX methods
     */
    @RequestMapping("/login")
    @ResponseBody
    public Result<User> login(@Valid LoginVo loginVo){
        User loginUser = loginService.login(loginVo);
        return Result.success(loginUser);
    }

    @RequestMapping("/register")
    @ResponseBody
    public Result<Boolean> register(@Valid RegisterVo registerVo){
        registerService.registerUser(registerVo);
        return Result.success(true);
    }

}
