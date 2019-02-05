package io.seanforfun.seckill.service.ebi;

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
     * @param fromId
     * @param toId
     * @param message
     */
    void sendMessageToUserById(Long fromId, Long toId, String message);
}
