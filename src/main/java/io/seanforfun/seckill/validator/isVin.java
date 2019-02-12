package io.seanforfun.seckill.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/12 13:41
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {VinValidator.class}
)
public @interface isVin {
    boolean required() default true;

    String message() default "INVALID VIN FORMAT";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
