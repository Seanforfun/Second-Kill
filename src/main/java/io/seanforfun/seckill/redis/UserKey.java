package io.seanforfun.seckill.redis;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/26 10:23
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix) {
        super(prefix);
    }

    public static final UserKey getKeyForId = new UserKey("id");
    public static final UserKey getKeyForName = new UserKey("name");
}
