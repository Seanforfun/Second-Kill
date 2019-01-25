package io.seanforfun.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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

    @Autowired
    private RedisConfig redisConfig;

    @Bean
    public JedisPool jedisPoolFactory(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getMaxActive());
        poolConfig.setMaxWaitMillis(redisConfig.getMaxWait() * 1000);
        JedisPool pool = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout() * 1000, redisConfig.getPassword());
        return pool;
    }

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

    public <T> boolean set(String key, T value, Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value, clazz);
            if(str == null){
                return false;
            }
            jedis.set(key, str);
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value, Class<T> clazz){
        if(value == null || clazz != value.getClass()){
            return null;
        }else if(clazz == int.class || clazz == Integer.class){
            return "" + value;
        }else if(clazz == String.class){
            return (String) value;
        }else if(clazz == long.class || clazz == Long.class){
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
