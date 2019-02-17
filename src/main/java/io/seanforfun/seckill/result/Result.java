package io.seanforfun.seckill.result;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/24 10:24
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Slf4j
public class Result<T> {
    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String msg;
    @Getter
    @Setter
    private T data;

    private static final int SUCCESS = 0;
    private static final String SUCCESS_STR = "SUCCESS";

    private Result(T data){
        this.code = SUCCESS;
        this.msg = SUCCESS_STR;
        this.data = data;
    }

    private Result(CodeMsg msg){
        if(msg == null) return;
        this.code = msg.getCode();
        this.msg = msg.getMsg();
    }

    public static <T> Result<T> success(T data){
        return new Result<>(data);
    }

    public static <T> Result<T> error(CodeMsg msg){
        return new Result<>(msg);
    }
}
