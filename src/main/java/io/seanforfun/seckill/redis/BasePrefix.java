package io.seanforfun.seckill.redis;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/26 10:16
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public abstract class BasePrefix implements KeyPrefix {

    private int expireSecond;
    private String prefix;

    public static final int NEVER_EXPIRE = 0;

    public BasePrefix(int expireSecond, String prefix) {
        this.expireSecond = expireSecond;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix){
        this(NEVER_EXPIRE, prefix);
    }

    @Override
    public int expireSeconds() {
        return this.expireSecond;
    }

    @Override
    public String getPrefix() {
        return this.getClass().getSimpleName() + ":" + prefix;
    }
}
