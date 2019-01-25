package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.domain.User;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.result.Result;
import io.seanforfun.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/24 10:15
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/")
    @ResponseBody
    public String demo(){
        return "hello world";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        return Result.error(CodeMsg.SERVER_ERROR_MSG);
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name", "Sean");
        return "hello";
    }

    @RequestMapping("/user")
    @ResponseBody
    public Result<Boolean> getUser(){
        return Result.success(userService.tx());
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<String> redisGet(){
        String user = redisService.get("hahaha", String.class);
        return user == null ? Result.error(CodeMsg.REDIS_VALUE_NOT_FOUND_MSG) : Result.success(user);
    }

    @RequestMapping("/redis/put/{key}/{value}")
    @ResponseBody
    public <T> Result<String> redisPut(@PathVariable(value = "key") String key,
                                        @PathVariable(value = "value") T value ){
        this.redisService.set(key, value);
        String ret = redisService.get(key, String.class);
        return ret == null ? Result.error(CodeMsg.REDIS_VALUE_NOT_FOUND_MSG) : Result.success(ret);
    }
}
