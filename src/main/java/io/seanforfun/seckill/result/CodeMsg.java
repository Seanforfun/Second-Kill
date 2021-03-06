package io.seanforfun.seckill.result;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/24 10:43
 * @description: Code message for showing the status of different operations.
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
    private static final int BIND_ERROR = 500101;
    private static final String BIND_ERROR_FORMAT_STR = "BIND_ERROR: %s";
    private static final int IMAGE_NAME_EMPTY = 500102;
    private static final String IMAGE_NAME_EMPTY_STR = "IMAGE NAME EMPTY ERROR";
    private static final int IMAGE_CONTENT_EMPTY = 500103;
    private static final String IMAGE_CONTENT_EMPTY_STR = "IMAGE CONTENT EMPTY ERROR";
    private static final int PATH_EMPTY_ERROR = 500104;
    private static final String PATH_EMPTY_ERROR_STR = "PATH EMPTY ERROR";
    private static final int IMGUR_UPLOAD_IMAGE_ERROR = 500105;
    private static final String IMGUR_UPLOAD_IMAGE_ERROR_STR = "IMGUR UPLOAD ERROR";
    private static final int IMGUR_DELETE_IMAGE_ERROR = 500107;
    private static final String IMGUR_DELETE_IMAGE_ERROR_STR = "IMGUR DELETE IMAGE ERROR";
    private static final int JSON_PARSE_INCORRECT_TOKEN = 500106;
    private static final String JSON_PARSE_INCORRECT_TOKEN_STR = "JSON PARSE KEY ERROR";
    private static final int AWS_BUCKET_DUPLICATE_NAME = 500108;
    private static final String AWS_BUCKET_DUPLICATE_NAME_STR = "DUPLICATE BUCKET NAME ERROR, PLEASE CHANGE YOUR BUCKET NAME FOR %s";
    private static final int AWS_FILE_UPLOAD_FAILED = 500109;
    private static final String AWS_FILE_UPLOAD_FAILED_STR = "AWS S3 UPLOAD FAILED";
    private static final int GET_IMAGE_SOURCE_ERROR = 500110;
    private static final String GET_IMAGE_SOURCE_ERROR_STR = "GET IMAGE FROM WRONG SOURCE";
    private static final int INVALID_IMAGE = 500111;
    private static final String INVALID_IMAGE_STR = "INVALID IMAGE";
    private static final int LOAD_IMAGE_BYTE_ERROR = 500112;
    private static final String LOAD_IMAGE_BYTE_ERROR_STR = "LOAD IMAGE BYTE ERROR";
    private static final int AWS_DELETE_IMAGE_ERROR = 500113;
    private static final String AWS_DELETE_IMAGE_ERROR_STR = "AWS DELETE IMAGE ERROR";
    private static final int IMAGE_CHEKC_INFO_ERROR = 500114;
    private static final String IMAGE_CHEKC_INFO_ERROR_STR = "GET IMAGE INFO ERROR";
    private static final int LOCAL_FILE_IMAGE_UPLOAD_ERROR = 500115;
    private static final String LOCAL_FILE_IMAGE_UPLOAD_ERROR_STR = "LOCAL FILE SYSTEM IMAGE UPLOAD ERROR";

    public static CodeMsg SUCCESS_MSG = new CodeMsg(SUCCESS, SUCCESS_STR);
    public static CodeMsg SERVER_ERROR_MSG = new CodeMsg(SERVER_ERROR, SERVER_ERROR_STR);
    public static CodeMsg BIND_ERROR_MSG = new CodeMsg(BIND_ERROR, BIND_ERROR_FORMAT_STR);
    public static CodeMsg IMAGE_NAME_EMPTY_MSG = new CodeMsg(IMAGE_NAME_EMPTY, IMAGE_NAME_EMPTY_STR);
    public static CodeMsg IMAGE_CONTENT_EMPTY_MSG = new CodeMsg(IMAGE_CONTENT_EMPTY, IMAGE_CONTENT_EMPTY_STR);
    public static CodeMsg PATH_EMPTY_ERROR_MSG = new CodeMsg(PATH_EMPTY_ERROR, PATH_EMPTY_ERROR_STR);
    public static CodeMsg IMGUR_UPLOAD_IMAGE_ERROR_MSG = new CodeMsg(IMGUR_UPLOAD_IMAGE_ERROR, IMGUR_UPLOAD_IMAGE_ERROR_STR);
    public static CodeMsg JSON_PARSE_INCORRECT_TOKEN_MSG = new CodeMsg(JSON_PARSE_INCORRECT_TOKEN, JSON_PARSE_INCORRECT_TOKEN_STR);
    public static CodeMsg IMGUR_DELETE_IMAGE_ERROR_MSG = new CodeMsg(IMGUR_DELETE_IMAGE_ERROR, IMGUR_DELETE_IMAGE_ERROR_STR);
    public static CodeMsg AWS_BUCKET_DUPLICATE_NAME_MSG = new CodeMsg(AWS_BUCKET_DUPLICATE_NAME, AWS_BUCKET_DUPLICATE_NAME_STR);
    public static CodeMsg AWS_FILE_UPLOAD_FAILED_MSG = new CodeMsg(AWS_FILE_UPLOAD_FAILED, AWS_FILE_UPLOAD_FAILED_STR);
    public static CodeMsg GET_IMAGE_SOURCE_ERROR_MSG = new CodeMsg(GET_IMAGE_SOURCE_ERROR, GET_IMAGE_SOURCE_ERROR_STR);
    public static CodeMsg INVALID_IMAGE_MSG = new CodeMsg(INVALID_IMAGE, INVALID_IMAGE_STR);
    public static CodeMsg LOAD_IMAGE_BYTE_ERROR_MSG = new CodeMsg(LOAD_IMAGE_BYTE_ERROR, LOAD_IMAGE_BYTE_ERROR_STR);
    public static CodeMsg AWS_DELETE_IMAGE_ERROR_MSG = new CodeMsg(AWS_DELETE_IMAGE_ERROR, AWS_DELETE_IMAGE_ERROR_STR);
    public static CodeMsg IMAGE_CHEKC_INFO_ERROR_MSG = new CodeMsg(IMAGE_CHEKC_INFO_ERROR, IMAGE_CHEKC_INFO_ERROR_STR);
    public static CodeMsg LOCAL_FILE_IMAGE_UPLOAD_ERROR_MSG = new CodeMsg(LOCAL_FILE_IMAGE_UPLOAD_ERROR, LOCAL_FILE_IMAGE_UPLOAD_ERROR_STR);

    //Redis error 5002XX
    private static final int REDIS_ERROR = 500200;
    private static final String REDIS_ERROR_STR = "REDIS ERROR";
    private static final int REDIS_NOT_FOUND_ERROR = 500201;
    private static final String REDIS_NOT_FOUND_ERROR_STR = "REDIS VALUE NOT FOUND ERROR";

    public static CodeMsg REDIS_ERROR_MSG = new CodeMsg(REDIS_ERROR, REDIS_ERROR_STR);
    public static CodeMsg REDIS_VALUE_NOT_FOUND_MSG = new CodeMsg(REDIS_NOT_FOUND_ERROR, REDIS_NOT_FOUND_ERROR_STR);

    // Login/Register error 5003XX
    private static final int USERNAME_NOT_EXIST_ERROR = 500300;
    private static final String USERNAME_NOT_EXIST_ERROR_STR = "USERNAME IS NOT FOUND";
    private static final int INCORRECT_PASSWORD_ERROR = 500301;
    private static final String INCORRECT_PASSWORD_ERROR_STR = "PASSWORD IS INCORRECT";
    private static final int USER_NOT_ACTIVATED_ERROR = 500302;
    private static final String USER_NOT_ACTIVATED_ERROR_STR = "USER IS NOT ACTIVATED";
    private static final int INCORRECT_ZIP_ERROR = 500303;
    private static final String INCORRECT_ZIP_ERROR_STR = "INCORRECT ZIP FORMAT";
    private static final int USER_NOT_LOGIN_ERROR = 500304;
    private static final String USER_NOT_LOGIN_ERROR_STR = "USER NOT LOGIN";
    private static final int USER_NOT_FOUND_ERROR = 500305;
    private static final String USER_NOT_FOUND_ERROR_STR = "USER NOT FOUND";
    private static final int USER_NOT_LOGOUT_ERROR = 500306;
    private static final String USER_NOT_LOGOUT_ERROR_STR = "USER NOT LOG OUT";
    private static final int USER_REGISTER_FAILED = 500307;
    private static final String USER_REGISTER_FAILED_STR = "USER REGISTER FAILED";


    public static CodeMsg USERNAME_NOT_EXIST_ERROR_MSG = new CodeMsg(USERNAME_NOT_EXIST_ERROR, USERNAME_NOT_EXIST_ERROR_STR);
    public static CodeMsg INCORRECT_PASSWORD_ERROR_MSG = new CodeMsg(INCORRECT_PASSWORD_ERROR, INCORRECT_PASSWORD_ERROR_STR);
    public static CodeMsg USER_NOT_ACTIVATED_ERROR_MSG = new CodeMsg(USER_NOT_ACTIVATED_ERROR, USER_NOT_ACTIVATED_ERROR_STR);
    public static CodeMsg INCORRECT_ZIP_ERROR_MSG = new CodeMsg(INCORRECT_ZIP_ERROR, INCORRECT_ZIP_ERROR_STR);
    public static CodeMsg USER_NOT_LOGIN_ERROR_MSG = new CodeMsg(USER_NOT_LOGIN_ERROR, USER_NOT_LOGIN_ERROR_STR);
    public static CodeMsg USER_NOT_FOUND_ERROR_MSG = new CodeMsg(USER_NOT_FOUND_ERROR, USER_NOT_FOUND_ERROR_STR);
    public static CodeMsg USER_NOT_LOGOUT_ERROR_MSG = new CodeMsg(USER_NOT_LOGOUT_ERROR, USER_NOT_LOGOUT_ERROR_STR);
    public static CodeMsg USER_REGISTER_FAILED_MSG = new CodeMsg(USER_REGISTER_FAILED, USER_REGISTER_FAILED_STR);

    // Message error 5004XX
    private static final int SEND_TO_YOURSELF_ERROR = 500400;
    private static final String SEND_TO_YOURSELF_ERROR_STR = "CANNOT SEND MESSAGE TO YOURSELF";
    private static final int UNREAD_MSG_ENPTY_ERROR = 500401;
    private static final String UNREAD_MSG_ENPTY_ERROR_STR = "UNREAD MESSAGE SHOULD NOT BE EMPTY";
    private static final int READ_MSG_ENPTY_ERROR = 500402;
    private static final String READ_MSG_ENPTY_ERROR_STR = "READ MESSAGE SHOULD NOT BE EMPTY";

    public static CodeMsg SEND_TO_YOURSELF_ERROR_MSG = new CodeMsg(SEND_TO_YOURSELF_ERROR, SEND_TO_YOURSELF_ERROR_STR);
    public static CodeMsg UNREAD_MSG_ENPTY_ERROR_MSG = new CodeMsg(UNREAD_MSG_ENPTY_ERROR, UNREAD_MSG_ENPTY_ERROR_STR);
    public static CodeMsg READ_MSG_ENPTY_ERROR_MSG = new CodeMsg(READ_MSG_ENPTY_ERROR, READ_MSG_ENPTY_ERROR_STR);

    // Vehicle error 5005XX
    private static final int FILE_NOT_IMAGE = 500501;
    private static final String FILE_NOT_IMAGE_STR = "UPLOAD FILE IS NOT A IMAGE";
    private static final Integer NO_VEHICLE_IMAGE_SAVED_ERROR = 500502;
    private static final String NO_VEHICLE_IMAGE_SAVED_ERROR_STR = "NO VEHICLE IMAGE SAVED ERROR";
    private static final int VEHICLE_ADD_ERROR = 500503;
    private static final String VEHICLE_ADD_ERROR_STR = "VEHICLE ADD ERROR";
    private static final int DUPLICATE_VEHICLE_VIN = 500504;
    private static final String DUPLICATE_VEHICLE_VIN_STR = "DUPLICATE VEHICLE VIN";

    public static CodeMsg FILE_NOT_IMAGE_MSG = new CodeMsg(FILE_NOT_IMAGE, FILE_NOT_IMAGE_STR);
    public static CodeMsg NO_VEHICLE_IMAGE_SAVED_ERROR_MSG = new CodeMsg(NO_VEHICLE_IMAGE_SAVED_ERROR, NO_VEHICLE_IMAGE_SAVED_ERROR_STR);
    public static CodeMsg VEHICLE_ADD_ERROR_MSG = new CodeMsg(VEHICLE_ADD_ERROR, VEHICLE_ADD_ERROR_STR);
    public static CodeMsg DUPLICATE_VEHICLE_VIN_MSG = new CodeMsg(DUPLICATE_VEHICLE_VIN, DUPLICATE_VEHICLE_VIN_STR);

    private CodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args){
        if(ArrayUtils.isEmpty(args)){
            return this;
        }
        String msg = String.format(this.msg, args[0]);
        return new CodeMsg(this.code, msg);
    }
}
