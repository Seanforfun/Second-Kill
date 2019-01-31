package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/29 16:55
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Controller
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @RequestMapping("/toManage")
    @ResponseBody
    public ModelAndView toManage(ModelAndView mv, User user) throws Exception {
        if (user == null){
            mv.setViewName("/pages/login.html");
            return mv;
        }
        mv.addObject("user", user);
        // Get user available actions by user information.
        mv.setViewName("/pages/manage.html");
        return mv;
    }

}
