package io.seanforfun.seckill.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/29 10:56
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {UsernameValidator.class}
)
public @interface isUsername {
    boolean required() default true;

    String message() default "DUPLICATE USERNAME";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
