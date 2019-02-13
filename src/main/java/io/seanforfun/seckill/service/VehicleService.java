package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.VehicleDao;
import io.seanforfun.seckill.entity.domain.Vehicle;
import io.seanforfun.seckill.entity.domain.VehicleDetail;
import io.seanforfun.seckill.entity.vo.VehicleVo;
import io.seanforfun.seckill.service.ebi.VehicleEbi;
import io.seanforfun.seckill.utils.SnowFlakeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/4 14:04
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
public class VehicleService implements VehicleEbi {

    @Autowired
    private VehicleDao vehicleDao;

    @Override
    public Long getInstockVehicleNumber() {
        return vehicleDao.getVehicleNumberByNotProcess(Vehicle.VEHICLE_TRANSACTION_FINISHED);
    }

    @Override
    public List<VehicleDetail> getInstockVehicleByPagination(VehicleVo vehicleVo) {
        Long currentPageIndex = (vehicleVo.getCurrentPageNum() - 1) * vehicleVo.getNumPerPage();
        List<VehicleDetail> instockVehicles = vehicleDao.getVehicleListPaginationByNotProcess(currentPageIndex, vehicleVo.getNumPerPage(), Vehicle.VEHICLE_TRANSACTION_FINISHED);
        return instockVehicles;
    }

    @Override
    public List<VehicleDetail> getInstockVehicles() {
        return vehicleDao.getVehicleListByNotProcess(Vehicle.VEHICLE_TRANSACTION_FINISHED);
    }

    @Override
    public List<VehicleDetail> getSoldVehicles() {
        return vehicleDao.getVehicleListByProcess(Vehicle.VEHICLE_TRANSACTION_FINISHED);
    }

    @Override
    @Transactional
    public void saveVehicle(VehicleDetail vehicleDetail, Long creatorId) {
        vehicleDetail.setId(SnowFlakeUtils.getSnowFlakeId());
        vehicleDetail.setCreatorId(creatorId);
        vehicleDetail.setCreateTime(System.currentTimeMillis());
        vehicleDetail.setLastModifierId(Vehicle.VEHICLE_NEVER_MODIFIED_USER);
        vehicleDetail.setLastModifyTime(Vehicle.VEHICLE_NEVER_MODIFIED);
        vehicleDao.saveVehicle(vehicleDetail);
        vehicleDao.saveVehicleDetail(vehicleDetail);
    }
}
