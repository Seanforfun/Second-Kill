package io.seanforfun.seckill.service.ebi;

import io.seanforfun.seckill.entity.domain.VehicleDetail;
import io.seanforfun.seckill.entity.vo.VehicleVo;

import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/4 14:00
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public interface VehicleEbi {

    /**
     * @return Total Number of vehicle
     */
     Long getInstockVehicleNumber();

    /**
     * Get pagination vehicles from redis or db.
     * @param vehicleVo
     * @return
     */
    List<VehicleDetail> getInstockVehicleByPagination(VehicleVo vehicleVo);

    /**
     * Get a list contains all instock vehicles
     * @return
     */
    List<VehicleDetail> getInstockVehicles();

    /**
     * Get a list of sold vehicles.
     * @return
     */
    List<VehicleDetail> getSoldVehicles();

    /**
     * Save vehicle information.
     * @param vehicleDetail
     * @param creatorId
     */
    void saveVehicle(VehicleDetail vehicleDetail, Long creatorId);
}
