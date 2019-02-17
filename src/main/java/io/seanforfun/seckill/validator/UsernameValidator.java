package io.seanforfun.seckill.validator;

import io.seanforfun.seckill.service.UserService;
import io.seanforfun.seckill.service.ebi.RegisterEbi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/29 10:57
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Component
public class UsernameValidator implements ConstraintValidator<isUsername, String> {
    @Autowired
    private RegisterEbi userSerivce;

    private Boolean required;

    @Override
    public void initialize(isUsername constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(this.required){
            if(StringUtils.isEmpty(value) || value.length() < 6){
                return false;
            }else if(!userSerivce.validUsername(value)){
                    return false;
            }
        }
        return true;
    }
}
