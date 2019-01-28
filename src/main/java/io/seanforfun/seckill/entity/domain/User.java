package io.seanforfun.seckill.entity.domain;

import io.seanforfun.seckill.utils.FormatUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

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
    public static final int ACTIVATED = 0;
    public static final int NOT_ACTIVATED = 1;

    private static final String ACTIVATED_STR = "ACTIVATED";
    private static final String NOT_ACTIVATED_STR = "NOT_ACTIVATED";

    private static final Map<Integer,String> ACTIVATE_MAP = new HashMap<>();

    static{
        ACTIVATE_MAP.put(ACTIVATED, ACTIVATED_STR);
        ACTIVATE_MAP.put(NOT_ACTIVATED, NOT_ACTIVATED_STR);
    }

    public static final boolean IS_ADMIN = true;
    public static final boolean NOT_ADMIN = false;

    @Setter
    private Long id;
    @Setter
    private String username;
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
    private String registerTimeVo;
    private String lastLoginTimeVo;
    private String lastModifiedTimeVo;

    public void setRegisterTime(long registerTime){
        this.registerTime = registerTime;
        this.registerTimeVo = FormatUtils.formatDateTime(registerTime);
    }

    public void setLastLoginTime(long lastLoginTime){
        this.lastLoginTime = lastLoginTime;
        this.lastLoginTimeVo = FormatUtils.formatDateTime(lastLoginTime);
    }

    public void setLastModifiedTime(long lastModifiedTime){
        this.lastModifiedTime = lastModifiedTime;
        this.lastModifiedTimeVo = FormatUtils.formatDateTime(lastModifiedTime);
    }

    public void setActivated(int activated){
        this.activated = activated;
        this.activatedVo = ACTIVATE_MAP.get(activated);
    }
}