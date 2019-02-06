package io.seanforfun.seckill.controller;

import io.seanforfun.seckill.entity.domain.Message;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.exceptions.GlobalException;
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
import java.util.List;

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
        message.setStatus(Message.MESSAGE_UNREAD);
        messageService.sendMessageToUser(message);
        return Result.success(true);
    }

    @RequestMapping("/list")
    @ResponseBody
    public ModelAndView listMessage(ModelAndView mv, User user, List<Message> messages){
        mv.setViewName("/pages/messages/messageList.html");
        return mv;
    }

    @RequestMapping("/reply/{toId}/{messageId}")
    @ResponseBody
    public ModelAndView reply(ModelAndView mv, User user,
                              List<Message> unreadMessages, @PathVariable("toId") Long toId,
                              @PathVariable("messageId") Long messageId){
        if(unreadMessages == null || unreadMessages.size() == 0){
            throw new GlobalException(CodeMsg.UNREAD_MSG_ENPTY_ERROR_MSG);
        }
        // Update unread message list in redis.
        Message currentMsg = messageService.redisUpdateUnreadMsgList(unreadMessages, messageId, user.getId());
        mv.addObject("unreadMsg", unreadMessages);
        // Update current message unread status in db.
        messageService.setMessageRead(messageId);
        mv.addObject("currentMsg", currentMsg);
        mv.addObject("toUserId", toId);
        // Record information for reply.
        mv.setViewName("/pages/messages/sendMessage.html");
        return mv;
    }

    @RequestMapping("/trash/{messageId}")
    @ResponseBody
    public Result<Boolean> trash(ModelAndView mv, User user, List<Message> unreadMsgs, @PathVariable("messageId") Long messageId){
        if(unreadMsgs == null || unreadMsgs.size() == 0){
            throw new GlobalException(CodeMsg.UNREAD_MSG_ENPTY_ERROR_MSG);
        }
        messageService.redisUpdateUnreadMsgList(unreadMsgs, messageId, user.getId());
        messageService.trashMsg(messageId);
        return Result.success(true);
    }

    @RequestMapping("/read/{messageId}")
    @ResponseBody
    public ModelAndView read(ModelAndView mv, User user, List<Message> unreadMsgs, @PathVariable("messageId") Long messageId){
        if(unreadMsgs == null || unreadMsgs.size() == 0){
            throw new GlobalException(CodeMsg.UNREAD_MSG_ENPTY_ERROR_MSG);
        }
        Message currentMsg = messageService.redisUpdateUnreadMsgList(unreadMsgs, messageId, user.getId());
        mv.addObject("unreadMsg", unreadMsgs);
        messageService.readMsg(messageId);
        mv.addObject("readMsg", currentMsg);
        mv.addObject("toUserId", currentMsg.getFromUser());
        mv.setViewName("/pages/messages/readMessage.html");
        return mv;
    }
}
