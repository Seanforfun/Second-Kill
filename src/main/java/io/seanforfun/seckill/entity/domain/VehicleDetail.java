package io.seanforfun.seckill.entity.domain;

import io.seanforfun.seckill.utils.VehicleUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author: Seanforfun
 * @date: Created in 2019/2/4 12:58
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Slf4j
@Getter
public class VehicleDetail extends Vehicle{
    /**
     * 	CREATE TABLE vehicle_detail(
     * 	id BIGINT NOT NULL PRIMARY KEY,
     * 	zip VARCHAR(20),
     * 	bodytype TINYINT,
     * 	ExteriorColour TINYINT,
     * 	mileage DOUBLE,
     * 	drivetrain TINYINT,
     * 	fueltype TINYINT,
     * 	ENGINE TINYINT,
     * 	doors TINYINT,
     * 	seatNum TINYINT,
     * 	description TEXT
     * );
     */

    @Setter
    private String zip;
    @Setter
    private Integer mileage;
    @Setter
    private String description;

    private Integer seatNum;
    private Integer bodyType;      //Vo
    private Integer exteriorColor; //Vo
    private Integer driveTrain; //Vo
    private Integer fuelType;   //Vo
    private Integer engine; //Vo
    private Integer doors; //Vo

    // Vo
    private String seatNumVo;
    private String bodyTypeVo;
    private String exteriorColorVo;
    private String driveTrainVo;
    private String fuelTypeVo;
    private String engineVo;
    private String doorsVo;

    public void setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
        this.seatNumVo = VehicleUtils.setSeatNumVo(seatNum);
    }


    public void setBodyType(Integer bodyType) {
        this.bodyType = bodyType;
        this.bodyTypeVo = VehicleUtils.setBodyTypeVo(bodyType);
    }

    public void setExteriorColor(Integer exteriorColor) {
        this.exteriorColor = exteriorColor;
        this.exteriorColorVo = VehicleUtils.setExteriorColorVo(exteriorColor);
    }

    public void setDriveTrain(Integer driveTrain) {
        this.driveTrain = driveTrain;
        this.driveTrainVo = VehicleUtils.setDriveTrainVo(driveTrain);
    }

    public void setFuelType(Integer fuelType) {
        this.fuelType = fuelType;
        this.fuelTypeVo = VehicleUtils.setFuelTypeVo(fuelType);
    }

    public void setEngine(Integer engine) {
        this.engine = engine;
        this.engineVo = VehicleUtils.setEngineVo(engine);
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
        this.doorsVo = VehicleUtils.setDoorsVo(doors);
    }
}
