package io.seanforfun.seckill.entity.domain;

import io.seanforfun.seckill.utils.FormatUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
public class User {
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

    private Long registerTime;
    private Long lastLoginTime;
    private Long lastModifiedTime;

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
}
