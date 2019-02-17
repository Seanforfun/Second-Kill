package io.seanforfun.seckill.entity.domain;

import io.seanforfun.seckill.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/7 22:00
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Component
@Slf4j
public class VehicleFactory {
    public VehicleDetail createBlankVehicle(Long creatorId){
        VehicleDetail vehicleDetail = new VehicleDetail();
        vehicleDetail.setId(SnowFlakeUtils.getSnowFlakeId());
        vehicleDetail.setCreateTime(System.currentTimeMillis());
        vehicleDetail.setCreatorId(creatorId);
        vehicleDetail.setLastModifierId(Vehicle.VEHICLE_NEVER_MODIFIED_USER);
        vehicleDetail.setLastModifyTime(Vehicle.VEHICLE_NEVER_MODIFIED);
        return vehicleDetail;
    }
}
