package io.seanforfun.seckill.dao;

import io.seanforfun.seckill.entity.domain.Vehicle;
import io.seanforfun.seckill.entity.domain.VehicleDetail;
import io.seanforfun.seckill.entity.vo.VehicleVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/4 14:23
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Mapper
@Repository
public interface VehicleDao {

    /**
     * Read
     */
    @Select("Select count(id) from vehicle where process = #{process}")
    Long getVehicleNumberByProcess(@Param("process") Integer process);

    @Select("Select count(id) from vehicle where process != #{process}")
    Long getVehicleNumberByNotProcess(@Param("process") Integer process);

    @Select("Select id, vin, price, make, model, status, process, year from vehicle where process != #{process} limit #{current}, #{perPage}")
    List<VehicleDetail> getVehicleListPaginationByNotProcess(@Param("current")Long pageIndex,
                                                             @Param("perPage")Long NumPerPage,
                                                             @Param("process") Integer process);

    @Select("Select id, vin, price, make, model, status, process, year from vehicle where process != #{process}")
    List<VehicleDetail> getVehicleListByNotProcess(@Param("process") int process);

    @Select("Select id, vin, price, make, model, status, process, year from vehicle where process = #{process}")
    List<VehicleDetail> getVehicleListByProcess(int vehicleTransactionFinished);

    /**
     * Insert
     */
    @Insert("Insert into vehicle (id, vin, make, model, price, status, transmission, year, process, " +
            "createTime, lastModifyTime, creatorId, lastModifierId) values (#{id}, #{vin}, #{make}, " +
            "#{model}, #{price}, #{status}, #{transmission}, #{year}, #{process}, #{createTime}, #{lastModifyTime}, " +
            "#{creatorId}, #{lastModifierId})")
    void saveVehicle(VehicleDetail vehicleDetail);

    @Insert("Insert into vehicle_detail (id, zip, bodytype, ExteriorColour, mileage, drivetrain, fueltype, " +
            "ENGINE, doors, seatNum, description) values (#{id}, #{zip}, #{bodyType}, #{exteriorColor}, #{mileage}, " +
            "#{driveTrain}, #{fuelType}, #{engine}, #{doors}, #{seatNum}, #{description})")
    void saveVehicleDetail(VehicleDetail vehicleDetail);

    /**
     * Update
     */

    /**
     * Delete
     */

}
