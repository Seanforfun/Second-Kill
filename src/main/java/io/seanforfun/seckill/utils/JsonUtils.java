package io.seanforfun.seckill.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.result.CodeMsg;

public class JsonUtils {
    public static <T> T get(String json, String key, Class<T> clazz){
        String[] tokens = key.trim().split("\\.");
        if(tokens == null || tokens.length == 0){
            throw new GlobalException(CodeMsg.JSON_PARSE_INCORRECT_TOKEN_MSG);
        }
        Object res = get(JSON.parseObject(json), key);
        T result = clazz.cast(res);
        return result;
    }

    private static Object get(JSONObject jsonObject, String key){
        String[] tokens = key.trim().split("\\.");
        if(tokens.length == 1){
            return jsonObject.get(key);
        }else{
            JSONObject next = (JSONObject) jsonObject.get(tokens[0]);
            return get(next, key.substring(key.indexOf('.') + 1));
        }
    }
}
