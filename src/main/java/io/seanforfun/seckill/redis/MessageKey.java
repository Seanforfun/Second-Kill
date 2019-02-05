package io.seanforfun.seckill.redis;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/5 13:55
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class MessageKey extends BasePrefix {
    private static final int THREE_MINUTES = 60 * 3;
    private MessageKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }

    public static final MessageKey getUnreadMessageByUserId = new MessageKey(THREE_MINUTES, "UnReadMsgUserId");
}
