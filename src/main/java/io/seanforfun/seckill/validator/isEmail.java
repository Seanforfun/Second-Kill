package io.seanforfun.seckill.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 19:30
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {EmailValidator.class}
)
public @interface isEmail {

    boolean required() default true;

    String message() default "INVALID E-MAIL FORMAT";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
