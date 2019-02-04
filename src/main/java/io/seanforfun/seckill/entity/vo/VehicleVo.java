package io.seanforfun.seckill.entity.vo;

import io.seanforfun.seckill.entity.domain.VehicleDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/4 14:01
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Slf4j
@Getter
@Setter
@Component
@Scope("prototype")
@ToString
public class VehicleVo extends PaginationBean {
    private List<VehicleDetail> vehicles;
}
