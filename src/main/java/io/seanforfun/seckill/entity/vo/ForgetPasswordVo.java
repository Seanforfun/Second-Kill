package io.seanforfun.seckill.entity.vo;

import io.seanforfun.seckill.validator.isEmail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/31 16:16
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Slf4j
@Setter
@Getter
@ToString
public class ForgetPasswordVo {
    @NotNull
    @Length(min = 6)
    private String username;
    @NotNull
    private String password;
    @NotNull
    @isEmail
    private String email;
}
