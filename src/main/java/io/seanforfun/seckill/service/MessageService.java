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

import java.util.ArrayList;
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
        // Update data saved in db.
        messageDao.saveUnreadMessage(message);
        // Update the unreadList saved in redis.
        User toUser = new User();
        toUser.setId(message.getToUser());
        List<Message> redisUnreadMsgs = null;
        redisUnreadMsgs = redisService.getList(MessageKey.getUnreadMessageByUserId, "" + toUser.getId(), Message.class);
        if(redisUnreadMsgs == null){
            List<Message> dbUserUnreadList = getUserUnreadMsg(toUser);
            redisService.set(MessageKey.getUnreadMessageByUserId, "" + toUser.getId(), dbUserUnreadList);
        }else{
            redisAddUnreadMessage(redisUnreadMsgs, message.getToUser(), message);
        }
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
    public List<Message> getReadedMsgs(Long id) {
        List<Message> readedMesssages = null;
        readedMesssages = redisService.getList(MessageKey.getReadedMessageByUserId, "" + id, Message.class);
        if(readedMesssages != null){
            return readedMesssages;
        }
        readedMesssages = messageDao.getMessageByUserIdAndStatus(id, Message.MESSAGE_READ);
        redisService.set(MessageKey.getReadedMessageByUserId, "" + id, readedMesssages);
        return readedMesssages;
    }

    @Override
    public Message getMessageById(Long messageId) {
        Message message = null;
        message = redisService.get(MessageKey.getMessageById, "" + messageId, Message.class);
        if(message != null){
            return message;
        }
        message = messageDao.getMessageById(messageId);
        redisService.set(MessageKey.getMessageById, "" + messageId, message);
        return message;
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

    @Override
    public void redisUpdateReadedMsgList(List<Message> readedMsgs, Long messageId, Long userId) {
        for(int i = 0; i < readedMsgs.size(); i++){
            Message msg = readedMsgs.get(i);
            if(msg.getId().equals(messageId)){
                readedMsgs.remove(i);
                break;
            }
        }
        redisService.set(MessageKey.getReadedMessageByUserId, "" + userId, readedMsgs);
    }

    @Override
    public void redisAddReadMsgList(Message currentMsg, Long userId) {
        List<Message> readedMessage = null;
        readedMessage = redisService.getList(MessageKey.getReadedMessageByUserId, "" + userId, Message.class);
        if(readedMessage == null){
            readedMessage = new ArrayList<>();
        }
        readedMessage.add(currentMsg);
        redisService.set(MessageKey.getReadedMessageByUserId, "" + userId, readedMessage);
    }

    private void redisAddUnreadMessage(List<Message> unreadMsg, Long userId, Message newMsg){
        unreadMsg.add(newMsg);
        redisService.set(MessageKey.getUnreadMessageByUserId, "" + userId, unreadMsg);
    }
}
