package io.seanforfun.seckill.config.annotations;

import java.lang.annotation.*;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/30 22:05
 * @description: ${description}
 * @modified:
 * @version: 0.01
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Access {

    String value() default "";

    String authority() default "USER";
}
