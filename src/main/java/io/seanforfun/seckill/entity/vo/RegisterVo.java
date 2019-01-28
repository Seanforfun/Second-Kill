package io.seanforfun.seckill.entity.vo;

import io.seanforfun.seckill.validator.isEmail;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/28 15:07
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NotNull
@ToString
public class RegisterVo {
    @Length(min = 1)
    private String username;
    @Length(min = 6)
    private String password;
    private String country;
    private String state;
    @isEmail
    private String email;
    private String zip;
}
