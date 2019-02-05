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
}
