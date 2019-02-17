package io.seanforfun.seckill.entity.vo;

import io.seanforfun.seckill.entity.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/1 14:08
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Component
@Scope("prototype")
@Slf4j
@Getter
@Setter
@ToString
public class UserVo extends PaginationBean {
    private List<User> user;
}
