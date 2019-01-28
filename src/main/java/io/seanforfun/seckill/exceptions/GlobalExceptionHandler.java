package io.seanforfun.seckill.exceptions;

import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 20:46
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        if(e instanceof GlobalException){
            GlobalException exception = (GlobalException)e;
            return Result.error(exception.getMsg());
        }else if(e instanceof BindException){
            BindException exception = (BindException)e;
            List<ObjectError> allErrors = exception.getAllErrors();
            ObjectError error = allErrors.get(0);
            String errorMsg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR_MSG.fillArgs(errorMsg));
        }else{
            return Result.error(CodeMsg.SERVER_ERROR_MSG);
        }
    }
}
