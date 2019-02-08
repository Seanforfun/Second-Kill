package io.seanforfun.seckill.service.ebi;

import io.seanforfun.seckill.entity.domain.Message;
import io.seanforfun.seckill.entity.domain.User;

import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/2 0:48
 * @description: ${description}
 * @modified:
 * @version: 0.01
 */
public interface MessageEbi {

    /**
     * Send message to toId user from fromId user.
     * @param message
     */
    void sendMessageToUser(Message message);

    /**
     * Get user unread messages using user information.
     * @param user
     * @return
     */
    List<Message> getUserUnreadMsg(User user);

    /**
     * Set message status to readed.
     */
    void setMessageRead(Long messageId);

    /**
     * Trash a message.
     * @param messageId
     */
    void trashMsg(Long messageId);

    /**
     * Read a message.
     * @param messageId
     */
    void readMsg(Long messageId);

    /**
     * Get user's readed messages
     * @param id
     * @return
     */
    List<Message> getReadedMsgs(Long id);

    /**
     * Get message by Id.
     * @param messageId
     * @return
     */
    Message getMessageById(Long messageId);

    /**
     * Remove current message from unread message list.
     * @param unreadMsgs
     * @param messageId
     * @param userId
     */
    Message redisUpdateUnreadMsgList(List<Message>unreadMsgs, Long messageId, Long userId);

    /**
     * Update readed messages saved in redis.
     * @param readedMsgs
     * @param messageId
     * @param id
     */
    void redisUpdateReadedMsgList(List<Message> readedMsgs, Long messageId, Long id);

    /**
     * Add message to readed list in redis.
     * @param currentMsg
     * @param id
     */
    void redisAddReadMsgList(Message currentMsg, Long id);
}
