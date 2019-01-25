package io.seanforfun.seckill.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/25 11:37
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Component
@ConfigurationProperties(prefix = "redis")
@Slf4j
public class RedisConfig {
    @Getter
    @Setter
    private String host;
    @Getter
    @Setter
    private int port;
    @Getter
    @Setter
    private int timeout;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private int maxActive;
    @Getter
    @Setter
    private int maxIdle;
    @Getter
    @Setter
    private int maxWait;
}
