package io.seanforfun.seckill.entity.domain;

import io.seanforfun.seckill.utils.VehicleUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
     * 	id BIGINT NOT NULL PRIMARY KEY,
     * 	zip VARCHAR(20),
     * 	TRIM TINYINT,
     * 	bodytype TINYINT,
     * 	ExteriorColour TINYINT,
     * 	mileage INT,
     * 	drivetrain TINYINT,
     * 	fueltype TINYINT,
     * 	ENGINE TINYINT,
     * 	seatingposition TINYINT,
     * 	doors TINYINT
     */
    @Setter
    private String zip;
    @Setter
    private Integer mileage;
    @Setter
    private String trim;

    private Integer bodyType;      //Vo
    private Integer exteriorColor; //Vo
    private Integer driveTrain; //Vo
    private Integer fuelType;   //Vo
    private Integer engine; //Vo
    private Integer seatingPosition; //Vo
    private Integer doors; //Vo

    // Vo
    private String bodyTypeVo;
    private String exteriorColorVo;
    private String driveTrainVo;
    private String fuelTypeVo;
    private String engineVo;
    private String seatingPositionVo;
    private String doorsVo;

    public void setBodyType(Integer bodyType) {
        this.bodyType = bodyType;
        this.bodyTypeVo = VehicleUtils.setBodyTypeVo(bodyTypeVo);
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

    public void setSeatingPosition(Integer seatingPosition) {
        this.seatingPosition = seatingPosition;
        this.seatingPositionVo = VehicleUtils.setSeatingPositionVo(seatingPosition);
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
        this.doorsVo = VehicleUtils.setDoorsVo(doors);
    }
}
