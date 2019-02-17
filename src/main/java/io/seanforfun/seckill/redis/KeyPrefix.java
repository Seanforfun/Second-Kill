package io.seanforfun.seckill.redis;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/26 10:14
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public interface KeyPrefix {

    /**
     * @return Return the alive time for current K-v pair
     */
    int expireSeconds();


    /**
     * This method will return the real prefix that current K-V pair saved in Redis.
     * @return
     */
    String getPrefix();
}
