package io.seanforfun.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/25 11:51
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
public class RedisService {
    @Autowired
    private JedisPool jedisPool;

    public <T> T get(String key, Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            String string  =jedis.get(key);
            return stringToBean(string, clazz);
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean set(String key, T value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if(str == null){
                return false;
            }
            jedis.set(key, str);
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value){
        if(value == null || value.getClass() != value.getClass()){
            return null;
        }else if(value.getClass() == int.class || value.getClass() == Integer.class){
            return "" + value;
        }else if(value.getClass() == String.class){
            return (String) value;
        }else if(value.getClass() == long.class || value.getClass() == Long.class){
            return "" + value;
        }else{
            return JSON.toJSONString(value);
        }
    }

    private <T> T stringToBean(String string, Class<T> clazz) {
        if(string == null || string.length() <= 0 || clazz == null){
            return null;
        }else if(clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(string);
        }else if(clazz == String.class){
            return (T)string;
        }else if(clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(string);
        }else{
            return JSON.toJavaObject(JSON.parseObject(string), clazz);
        }
    }

    private void returnToPool(Jedis jedis){
        if(jedis != null)
            jedis.close();
    }
}
