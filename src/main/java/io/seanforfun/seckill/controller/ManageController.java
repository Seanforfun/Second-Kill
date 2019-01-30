package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.redis.UserKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/toManage")
    @ResponseBody
    public ModelAndView toManage(ModelAndView mv,
                                 @CookieValue(value = User.USER_TOKEN, required = false) String userToken,
                                 @RequestParam(value = User.USER_TOKEN, required = false) String paramToken){
        if(StringUtils.isEmpty(userToken) && StringUtils.isEmpty(paramToken)){
            mv.setViewName("/pages/login.html");
            return mv;
        }
        String token = StringUtils.isEmpty(userToken) ? paramToken : userToken;
        // Get basic user information, required for both user and admin.
        User user = redisService.get(UserKey.userToken, token, User.class);
        mv.addObject("user", user);
        // Get user available actions by user information.
        //TODO
        mv.setViewName("/pages/manage.html");
        return mv;
    }

}
