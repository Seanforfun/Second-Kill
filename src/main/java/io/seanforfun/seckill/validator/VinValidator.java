package io.seanforfun.seckill.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/12 13:42
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class VinValidator implements ConstraintValidator<isVin, String> {
    private static final int VIN_LENGTH = 17;
    boolean required = false;
    @Override
    public void initialize(isVin constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required){
            if(StringUtils.isEmpty(value)){
                return false;
            }else{
                if(value.length() != VIN_LENGTH){
                    return false;
                }
            }
        }
        return true;
    }
}
