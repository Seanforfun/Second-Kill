package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.entity.domain.Message;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.result.Result;
import io.seanforfun.seckill.service.MessageService;
import io.seanforfun.seckill.service.ebi.MessageEbi;
import io.seanforfun.seckill.utils.SnowFlakeUtils;
import lombok.ToString;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/5 10:18
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageEbi messageService;

    @RequestMapping("/toMessage/{id}")
    public ModelAndView toMessage(ModelAndView mv, @PathVariable("id") Long id){
        mv.setViewName("redirect: /admin/message/" + id);
        return mv;
    }

    @RequestMapping("/send")
    @ResponseBody
    public Result<Boolean> sendMessage(@Valid Message message, User user){
        if(message.getToUser().equals(user.getId())){
            return Result.error(CodeMsg.SEND_TO_YOURSELF_ERROR_MSG);
        }
        message.setId(SnowFlakeUtils.getSnowFlakeId());
        message.setHasRead(false);
        messageService.sendMessageToUser(message);
        return Result.success(true);
    }

}
