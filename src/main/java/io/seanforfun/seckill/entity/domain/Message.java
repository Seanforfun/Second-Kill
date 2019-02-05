package io.seanforfun.seckill.entity.domain;

import io.seanforfun.seckill.utils.FormatUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/5 10:07
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Slf4j
@Getter
public class Message {
    @Setter
    private Long id;
    @NotNull
    @Setter
    private Long fromUser;
    @NotNull
    @Setter
    private Long toUser;
    @NotNull
    @Length(min = 1, max = 65535)
    @Setter
    private String title;
    @NotNull
    @Length(min = 1, max = 65535)
    @Setter
    private String msg;
    @Setter
    private Boolean hasRead;

    private Long sendTime;

    private String sendTimeVo;

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
        this.sendTimeVo= FormatUtils.formatDateTime(sendTime);
    }
}
