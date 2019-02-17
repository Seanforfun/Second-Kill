package io.seanforfun.seckill.redis;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/11 16:51
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class PageKey extends BasePrefix {
    public PageKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }

    public static PageKey getPageByName = new PageKey(1200, "htmlPageCache");
}
