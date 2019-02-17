package io.seanforfun.seckill.entity.domain;

import io.seanforfun.seckill.utils.FormatUtils;
import io.seanforfun.seckill.utils.MD5Utils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Seanforfun
 * @date: Created in 2019/1/24 12:44
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class User {
    // User status related.
    public static final int ACTIVATED = 0;
    public static final int NOT_ACTIVATED = 1;
    public static final int REJECTED = 2;

    private static final String ACTIVATED_STR = "ACTIVATED";
    private static final String NOT_ACTIVATED_STR = "NOT_ACTIVATED";

    public static final Long NOT_USED = 0L;

    private static final Map<Integer,String> ACTIVATE_MAP = new HashMap<>();

    static{
        ACTIVATE_MAP.put(ACTIVATED, ACTIVATED_STR);
        ACTIVATE_MAP.put(NOT_ACTIVATED, NOT_ACTIVATED_STR);
    }

    public static final boolean IS_ADMIN = true;
    public static final boolean NOT_ADMIN = false;

    // User login related
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String USER_LOGIN = "USER_LOGIN";

    private static final String USER_LOGIN_SALT = "KOBE";

    public static final String USER_REMEMBER_ME = "REMEMBER_ME";
    public static final String USER_REMEMBER_ME_TOKEN = "REMEMBER_ME_TOKEN";

    @Setter
    private Long id;
    @Setter
    private String username;
    @Setter
    private String firstname;
    @Setter
    private String lastname;
    @Setter
    private String password;
    @Setter
    private String salt;
    @Setter
    private Boolean admin;
    @Setter
    private String country;
    @Setter
    private String state;
    @Setter
    private String zip;
    @Setter
    private String email;

    private int activated;

    private Long registerTime;
    private Long lastLoginTime;
    private Long lastModifiedTime;

    private String activatedVo;
    private String lastLoginTimeVo;
    private String registerTimeVo;
    private String lastModifiedTimeVo;

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
        this.registerTimeVo = FormatUtils.formatDateTime(registerTime);
    }

    public void setLastLoginTime(long lastLoginTime){
        this.lastLoginTime = lastLoginTime;
        this.lastLoginTimeVo = lastLoginTime == User.NOT_USED ? "No operation": FormatUtils.formatDateTime(lastLoginTime);
    }

    public void setLastModifiedTime(long lastModifiedTime){
        this.lastModifiedTime = lastModifiedTime;
        this.lastModifiedTimeVo = lastModifiedTime == User.NOT_USED ? "No operation": FormatUtils.formatDateTime(lastModifiedTime);
    }

    public void setActivated(int activated){
        this.activated = activated;
        this.activatedVo = ACTIVATE_MAP.get(activated);
    }

    public static String userLoginSession(){
        return MD5Utils.userLoginSession(USER_LOGIN_SALT);
    }

    public static boolean checkUserLogin(String token){
        String validToken = userLoginSession();
        return validToken.equals(token);
    }
}
