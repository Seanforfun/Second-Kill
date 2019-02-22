package io.seanforfun.seckill.entity.vo;

import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.domain.Message;
import io.seanforfun.seckill.entity.domain.User;
import io.seanforfun.seckill.entity.domain.VehicleDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/22 16:37
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Slf4j
@Getter
@Setter
@Component
@Scope("prototype")
public class VehicleInfoVo {
    private VehicleDetail vehicleDetail;
    private User user;
    private List<Message> messages;
    private List<Image> vehicleImages;
    private String base64QRString;
}
