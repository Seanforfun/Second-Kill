package io.seanforfun.seckill.utils;

import java.util.UUID;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/29 16:39
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class UUIDUtils {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
