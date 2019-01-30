package io.seanforfun.seckill.redis;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/26 10:23
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class UserKey extends BasePrefix {

    public static final int USER_EXPIRE_SECONDS = 2 * 24 * 3600;

    private UserKey(String prefix) {
        super(prefix);
    }

    private UserKey(int expireSecond, String prefix){
        super(expireSecond, prefix);
    }

    public static final UserKey getKeyForId = new UserKey("id");
    public static final UserKey getKeyForName = new UserKey("name");
    public static final UserKey userToken = new UserKey(USER_EXPIRE_SECONDS,"tk");
}
