package io.seanforfun.seckill.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/24 12:44
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Slf4j
public class User {
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private String name;
}
