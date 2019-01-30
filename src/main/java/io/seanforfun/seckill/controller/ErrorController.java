package io.seanforfun.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/30 13:09
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/toError")
    public ModelAndView toError(@NotNull String msg, ModelAndView mv){
        mv.addObject("msg", msg);
        mv.setViewName("/pages/404.html");
        return mv;
    }

}
