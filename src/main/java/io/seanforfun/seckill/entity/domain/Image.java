package io.seanforfun.seckill.entity.domain;

import io.seanforfun.seckill.entity.enums.ImageSource;
import io.seanforfun.seckill.entity.enums.ImageType;
import io.seanforfun.seckill.utils.MD5Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/15 11:52
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Component
@Scope("prototype")
@Slf4j
@Getter
@Setter
public class Image {
    private Long id;
    private String link;
    private byte[] ImageByte;
    private String name;
    private ImageSource source;
    private ImageType type;
    private Long associateId;
}
