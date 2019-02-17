package io.seanforfun.seckill.entity.vo;

import io.seanforfun.seckill.validator.isEmail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 18:57
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Component
@Slf4j
@ToString
public class LoginVo {
    @Getter
    @Setter
    @NotNull
    private String username;

    @Getter
    @Setter
    @NotNull
    @Length(min=6)
    private String password;

    @Getter
    @Setter
    private boolean rememberMe;
}
