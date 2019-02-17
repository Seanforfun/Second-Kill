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
 * @date: Created in 2019/2/1 14:19
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Component
@Scope("singleton")
@Configuration
@ConfigurationProperties
@PropertySource(value = "classpath:/properties/pagination.properties")
@Slf4j
@Getter
@Setter
public class PaginationConfigBean {
    private int maxUserPerPage;

    private boolean dev;
}
