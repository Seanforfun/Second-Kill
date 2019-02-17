package io.seanforfun.seckill.validator;

import io.seanforfun.seckill.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 19:35
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class EmailValidator implements ConstraintValidator<isEmail, String> {
    boolean required = false;

    @Override
    public void initialize(isEmail constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(this.required){
            if(StringUtils.isEmpty(value)){
                return false;
            }else{
                return ValidatorUtils.isEmail(value);
            }
        }
        return true;
    }
}
