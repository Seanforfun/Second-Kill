package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.MessageDao;
import io.seanforfun.seckill.entity.domain.Message;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.redis.MessageKey;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.service.ebi.MessageEbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/5 12:33
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
public class MessageService implements MessageEbi {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional
    public void sendMessageToUser(Message message) {
        Long currentTime = System.currentTimeMillis();
        message.setSendTime(currentTime);
        messageDao.saveUnreadMessage(message);
        // Update the unreadList saved in redis.
        User toUser = new User();
        toUser.setId(message.getToUser());
        List<Message> toUserUnreadList = getUserUnreadMsg(toUser);
        redisAddUnreadMessage(toUserUnreadList, message.getToUser(), message);
    }

    @Override
    public List<Message> getUserUnreadMsg(User user) {
        List<Message> messages = null;
        // Try to get unread messages from redis
        messages = redisService.getList(MessageKey.getUnreadMessageByUserId, "" + user.getId(), Message.class);
        if (messages != null){
            return messages;
        }
        // Get unread messages from db
        messages = messageDao.getMessageByUserIdAndStatus(user.getId(), Message.MESSAGE_UNREAD);
        if (messages == null || messages.size() == 0){
            return messages;
        }
        // Save unread messages to redis
        redisService.set(MessageKey.getUnreadMessageByUserId, user.getId() +"", messages);
        return messages;
    }

    @Override
    public void setMessageRead(Long messageId) {
        messageDao.setMessageStatusById(messageId, Message.MESSAGE_READ);
    }

    @Override
    public void trashMsg(Long messageId) {
        messageDao.setMessageStatusById(messageId, Message.MESSAGE_TRASH);
    }

    @Override
    public void readMsg(Long messageId) {
        messageDao.setMessageStatusById(messageId, Message.MESSAGE_READ);
    }

    @Override
    public Message redisUpdateUnreadMsgList(List<Message> unreadMsg, Long messageId, Long userId) {
        Message current = null;
        for(int i = 0; i < unreadMsg.size(); i++){
            Message msg = unreadMsg.get(i);
            if(msg.getId().equals(messageId)){
                current = msg;
                unreadMsg.remove(i);
                break;
            }
        }
        redisService.set(MessageKey.getUnreadMessageByUserId, "" + userId, unreadMsg);
        return current;
    }

    private void redisAddUnreadMessage(List<Message> unreadMsg, Long userId, Message newMsg){
        unreadMsg.add(newMsg);
        redisService.set(MessageKey.getUnreadMessageByUserId, "" + userId, unreadMsg);
    }
}
