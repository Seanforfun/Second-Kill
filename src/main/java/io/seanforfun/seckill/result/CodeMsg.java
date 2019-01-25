package io.seanforfun.seckill.result;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/24 10:43
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Slf4j
public class CodeMsg {
    @Getter
    private int code;
    @Getter
    private String msg;

    private static final int SUCCESS = 0;
    private static final String SUCCESS_STR = "SUCCESS";

    // General errors 5001XX
    private static final int SERVER_ERROR = 500100;
    private static final String SERVER_ERROR_STR = "SERVER ERROR";

    public static CodeMsg SUCCESS_MSG = new CodeMsg(SUCCESS, SUCCESS_STR);
    public static CodeMsg SERVER_ERROR_MSG = new CodeMsg(SERVER_ERROR, SERVER_ERROR_STR);

    //Redis error 5002XX
    private static final int REDIS_ERROR = 500200;
    private static final String REDIS_ERROR_STR = "REDIS ERROR";
    private static final int REDIS_NOT_FOUND_ERROR = 500201;
    private static final String REDIS_NOT_FOUND_ERROR_STR = "REDIS VALUE NOT FOUND ERROR";
    public static CodeMsg REDIS_ERROR_MSG = new CodeMsg(REDIS_ERROR, REDIS_ERROR_STR);
    public static CodeMsg REDIS_VALUE_NOT_FOUND_MSG = new CodeMsg(REDIS_NOT_FOUND_ERROR, REDIS_NOT_FOUND_ERROR_STR);

    private CodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
