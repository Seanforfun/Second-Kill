package io.seanforfun.seckill.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/12 21:53
 * @description: ${description}
 * @modified:
 * @version: 0.01
 */
@Component
@Scope("singleton")
@Configuration
@ConfigurationProperties(prefix = "environment")
@PropertySource(value = "classpath:/properties/environment.properties")
@Slf4j
@Getter
@Setter
public class EnvironmentConfigBean {
    private Boolean dev;
    private Boolean cachePage;
}
