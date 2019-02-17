package io.seanforfun.seckill.exceptions;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import io.seanforfun.seckill.result.CodeMsg;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/27 20:27
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Slf4j
public class GlobalException extends  RuntimeException{
    @Getter
    private CodeMsg msg;

    public GlobalException(CodeMsg msg){
        super();
        this.msg = msg;
    }
}
