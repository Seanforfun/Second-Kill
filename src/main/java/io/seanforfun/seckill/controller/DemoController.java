package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.rabbitmq.MqReceiver;
import io.seanforfun.seckill.rabbitmq.MqSender;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.result.Result;
import io.seanforfun.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    private MqSender mqSender;

    @Autowired
    private MqReceiver mqReceiver;

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

    @RequestMapping("/mq")
    @ResponseBody
    public Result<Boolean> mq(){
        for(int i = 0; i < 1; i++)
            mqSender.topicSender("Hello world!");
        return Result.success(true);
    }
}
