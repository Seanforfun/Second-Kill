package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.entity.vo.LoginVo;
import io.seanforfun.seckill.result.Result;
import io.seanforfun.seckill.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 18:05
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/tologin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping()
    @ResponseBody
    public Result<Boolean> login(@Valid LoginVo loginVo){
        loginService.login(loginVo);
        return Result.success(true);
    }

}
