package io.seanforfun.seckill.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/4 13:15
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class VehicleUtils {

    //Convertible, Sedan, Van/Minivan, Couple, SUV/Crossover, Wagon, Hatchback, Truck
    public static final Integer BODY_TYPE_CONVERTIBLE = 1;
    public static final String BODY_TYPE_CONVERTIBLE_STR = "Convertible";
    public static final Integer BODY_TYPE_SEDAN = 2;
    public static final String BODY_TYPE_SEDAN_STR = "Sedan";
    public static final Integer BODY_TYPE_VAN = 3;
    public static final String BODY_TYPE_VAN_STR = "Van/Minivan";
    public static final Integer BODY_TYPE_COUPLE = 4;
    public static final String BODY_TYPE_COUPLE_STR = "Couple";
    public static final Integer BODY_TYPE_SUV = 5;
    public static final String BODY_TYPE_SUV_STR = "SUV/Crossover";
    public static final Integer BODY_TYPE_WAGON = 6;
    public static final String BODY_TYPE_WAGON_STR = "Wagon";
    public static final Integer BODY_TYPE_HATCHBACK = 7;
    public static final String BODY_TYPE_HATCHBACK_STR = "Hatchback";
    public static final Integer BODY_TYPE_TRUCK = 8;
    public static final String BODY_TYPE_TRUCK_STR = "Truck";
    public static Map<Integer, String> BODY_TYPE_MAP = null;

    static {
        BODY_TYPE_MAP = new HashMap<>();
        BODY_TYPE_MAP.put(BODY_TYPE_CONVERTIBLE, BODY_TYPE_CONVERTIBLE_STR);
        BODY_TYPE_MAP.put(BODY_TYPE_SEDAN, BODY_TYPE_SEDAN_STR);
        BODY_TYPE_MAP.put(BODY_TYPE_VAN, BODY_TYPE_VAN_STR);
        BODY_TYPE_MAP.put(BODY_TYPE_COUPLE, BODY_TYPE_COUPLE_STR);
        BODY_TYPE_MAP.put(BODY_TYPE_SUV, BODY_TYPE_SUV_STR);
        BODY_TYPE_MAP.put(BODY_TYPE_WAGON, BODY_TYPE_WAGON_STR);
        BODY_TYPE_MAP.put(BODY_TYPE_HATCHBACK, BODY_TYPE_HATCHBACK_STR);
        BODY_TYPE_MAP.put(BODY_TYPE_TRUCK, BODY_TYPE_TRUCK_STR);
    }
    public static String setBodyTypeVo(Integer bodyTypeVo) {
        return BODY_TYPE_MAP.get(bodyTypeVo);
    }

    public static final Integer Aluminum = 0;
    public static final String Aluminum_str = "Aluminum";
    public static final Integer Beige = 1;
    public static final String Beige_str = "Beige";
    public static final Integer Black = 2;
    public static final String Black_str = "Black";
    public static final Integer Blue = 3;
    public static final String Blue_str = "Blue";
    public static final Integer Brown = 4;
    public static final String Brown_str = "Brown";
    public static final Integer Bronze = 5;
    public static final String Bronze_str = "Bronze";
    public static final Integer Claret = 6;
    public static final String Claret_str = "Claret";
    public static final Integer Copper = 7;
    public static final String Copper_str = "Copper";
    public static final Integer Cream = 8;
    public static final String Cream_str = "Cream";
    public static final Integer Gold = 9;
    public static final String Gold_str = "Gold";
    public static final Integer Gray = 10;
    public static final String Gray_str = "Gray";
    public static final Integer Green = 11;
    public static final String Green_str = "Green";
    public static final Integer Maroon = 12;
    public static final String Maroon_str = "Maroon";
//    public static final Integer Metallic = 13;
//    public static final String Metallic_str = "Metallic";
    public static final Integer Navy = 14;
    public static final String Navy_str = "Navy";
    public static final Integer Orange = 15;
    public static final String Orange_str = "Orange";
    public static final Integer Pink = 16;
    public static final String Pink_str = "Pink";
    public static final Integer Purple = 17;
    public static final Integer Red = 18;
    public static final String Purple_str = "Purple";
    public static final String Red_str = "Red";
    public static final Integer Rose = 19;
    public static final String Rose_str = "Rose";
    public static final Integer Rust = 20;
    public static final String Rust_str = "Rust";
    public static final Integer Silver = 21;
    public static final String Silver_str = "Silver";
    public static final Integer Tan = 22;
    public static final String Tan_str = "Tan";
    public static final Integer Turquoise = 23;
    public static final String Turquoise_str = "Turquoise";
    public static final Integer White = 24;
    public static final String White_str = "White";
    public static final Integer Yellow = 25;
    public static final String Yellow_str = "Yellow";

    public static Map<Integer, String > EXTERIOR_COLOR_MAP = null;
    static{
        EXTERIOR_COLOR_MAP = new HashMap<>();
        EXTERIOR_COLOR_MAP.put(Aluminum, Aluminum_str);
        EXTERIOR_COLOR_MAP.put(Beige, Beige_str);
        EXTERIOR_COLOR_MAP.put(Black, Black_str);
        EXTERIOR_COLOR_MAP.put(Blue, Blue_str);
        EXTERIOR_COLOR_MAP.put(Brown, Brown_str);
        EXTERIOR_COLOR_MAP.put(Bronze, Bronze_str);
        EXTERIOR_COLOR_MAP.put(Claret, Claret_str);
        EXTERIOR_COLOR_MAP.put(Copper, Copper_str);
        EXTERIOR_COLOR_MAP.put(Cream, Cream_str);
        EXTERIOR_COLOR_MAP.put(Gold, Gold_str);
        EXTERIOR_COLOR_MAP.put(Gray, Gray_str);
        EXTERIOR_COLOR_MAP.put(Green, Green_str);
        EXTERIOR_COLOR_MAP.put(Maroon, Maroon_str);
//        EXTERIOR_COLOR_MAP.put(Metallic, Metallic_str);
        EXTERIOR_COLOR_MAP.put(Navy, Navy_str);
        EXTERIOR_COLOR_MAP.put(Orange, Orange_str);
        EXTERIOR_COLOR_MAP.put(Pink, Pink_str);
        EXTERIOR_COLOR_MAP.put(Purple, Purple_str);
        EXTERIOR_COLOR_MAP.put(Red, Red_str);
        EXTERIOR_COLOR_MAP.put(Rose, Rose_str);
        EXTERIOR_COLOR_MAP.put(Rust, Rust_str);
        EXTERIOR_COLOR_MAP.put(Silver, Silver_str);
        EXTERIOR_COLOR_MAP.put(Tan, Tan_str);
        EXTERIOR_COLOR_MAP.put(Turquoise, Turquoise_str);
        EXTERIOR_COLOR_MAP.put(White, White_str);
        EXTERIOR_COLOR_MAP.put(Yellow, Yellow_str);
    }

    public static String setExteriorColorVo(Integer exteriorColor) {
        return EXTERIOR_COLOR_MAP.get(exteriorColor);
    }

    public static final Integer DRIVE_TRAIN_AWD = 0;
    public static final String DRIVE_TRAIN_AWD_STR = "AWD/4WD";
    public static final Integer DRIVE_TRAIN_FWD = 1;
    public static final String DRIVE_TRAIN_FWD_STR = "Front Wheel Drive";
    public static final Integer DRIVE_TRAIN_RWD = 2;
    public static final String DRIVE_TRAIN_RWD_STR = "Rear Wheel Drive";
    public static Map<Integer, String> DRIVE_TRAIN_MAP = null;

    static {
        DRIVE_TRAIN_MAP = new HashMap<>();
        DRIVE_TRAIN_MAP.put(DRIVE_TRAIN_AWD, DRIVE_TRAIN_AWD_STR);
        DRIVE_TRAIN_MAP.put(DRIVE_TRAIN_FWD, DRIVE_TRAIN_FWD_STR);
        DRIVE_TRAIN_MAP.put(DRIVE_TRAIN_RWD, DRIVE_TRAIN_RWD_STR);
    }
    public static String setDriveTrainVo(Integer driveTrain) {
        return DRIVE_TRAIN_MAP.get(driveTrain);
    }

    public static Integer FUEL_TYPE_GAS = 0;
    public static String FUEL_TYPE_GAS_STR = "Gasoline";
    public static Integer FUEL_TYPE_DIESEL = 1;
    public static String FUEL_TYPE_DIESEL_STR = "Diesel";
    public static Integer FUEL_TYPE_ALTERNATIVE = 2;
    public static String FUEL_TYPE_ALTERNATIVE_STR = "Alternative";
    public static Integer FUEL_TYPE_ELECTRICITY = 3;
    public static String FUEL_TYPE_ELECTRICITY_STR = "Electric";
    public static Integer FUEL_TYPE_HYBIRD = 4;
    public static String FUEL_TYPE_HYBIRD_STR = "Hybird";
    public static Map<Integer, String> FUEL_TYPE_MAP = null;

    static {
        FUEL_TYPE_MAP = new HashMap<>();
        FUEL_TYPE_MAP.put(FUEL_TYPE_GAS, FUEL_TYPE_GAS_STR);
        FUEL_TYPE_MAP.put(FUEL_TYPE_DIESEL, FUEL_TYPE_DIESEL_STR);
        FUEL_TYPE_MAP.put(FUEL_TYPE_ALTERNATIVE, FUEL_TYPE_ALTERNATIVE_STR);
        FUEL_TYPE_MAP.put(FUEL_TYPE_ELECTRICITY, FUEL_TYPE_HYBIRD_STR);
    }
    public static String setFuelTypeVo(Integer fuelType) {
        return FUEL_TYPE_MAP.get(fuelType);
    }

    public static Integer ENGINE_3_CYLINDER = 0;
    public static String ENGINE_3_CYLINDER_STR = "3 Cylinder";
    public static Integer ENGINE_4_CYLINDER = 1;
    public static String ENGINE_4_CYLINDER_STR = "4 Cylinder";
    public static Integer ENGINE_5_CYLINDER = 2;
    public static String ENGINE_5_CYLINDER_STR = "5 Cylinder";
    public static Integer ENGINE_6_CYLINDER = 3;
    public static String ENGINE_6_CYLINDER_STR = "6 Cylinder";
    public static Integer ENGINE_8_CYLINDER = 4;
    public static String ENGINE_8_CYLINDER_STR = "8 Cylinder";
    public static Integer ENGINE_10_CYLINDER = 5;
    public static String ENGINE_10_CYLINDER_STR = "10 Cylinder";
    public static Integer ENGINE_12_CYLINDER = 6;
    public static String ENGINE_12_CYLINDER_STR = "12 Cylinder";
    public static Integer ENGINE_16_CYLINDER = 7;
    public static String ENGINE_16_CYLINDER_STR = "16 Cylinder";
    public static Integer ENGINE_ELECTRIC = 8;
    public static String ENGINE_ELECTRIC_STR = "Electric";
    public static Integer ENGINE_HYBIRD = 9;
    public static String ENGINE_HYBIRD_STR = "Hybrid";
    public static Integer ENGINE_ROTARY_ENGINE = 10;
    public static String ENGINE_ROTARY_ENGINE_STR = "Rotary Engine";
    public static Integer ENGINE_FUEL_CELL = 11;
    public static String ENGINE_FUEL_CELL_STR = "Fuel Cell";

    public static Map<Integer, String> ENGINE_MAP = null;

    static {
        ENGINE_MAP = new HashMap<>();
        ENGINE_MAP.put(ENGINE_3_CYLINDER, ENGINE_3_CYLINDER_STR);
        ENGINE_MAP.put(ENGINE_4_CYLINDER, ENGINE_4_CYLINDER_STR);
        ENGINE_MAP.put(ENGINE_5_CYLINDER, ENGINE_5_CYLINDER_STR);
        ENGINE_MAP.put(ENGINE_6_CYLINDER, ENGINE_6_CYLINDER_STR);
        ENGINE_MAP.put(ENGINE_8_CYLINDER, ENGINE_8_CYLINDER_STR);
        ENGINE_MAP.put(ENGINE_10_CYLINDER, ENGINE_10_CYLINDER_STR);
        ENGINE_MAP.put(ENGINE_12_CYLINDER, ENGINE_12_CYLINDER_STR);
        ENGINE_MAP.put(ENGINE_16_CYLINDER, ENGINE_16_CYLINDER_STR);
        ENGINE_MAP.put(ENGINE_ELECTRIC, ENGINE_ELECTRIC_STR);
        ENGINE_MAP.put(ENGINE_HYBIRD, ENGINE_HYBIRD_STR);
        ENGINE_MAP.put(ENGINE_ROTARY_ENGINE, ENGINE_ROTARY_ENGINE_STR);
        ENGINE_MAP.put(ENGINE_FUEL_CELL, ENGINE_FUEL_CELL_STR);
    }
    public static String setEngineVo(Integer engine) {
        return ENGINE_MAP.get(engine);
    }

    public static final Integer DOOR_TWO = 0;
    public static final String DOOR_TWO_STR = "Two Doors";
    public static final Integer DOOR_THREE= 1;
    public static final String DOOR_THREE_STR= "Three Doors";
    public static final Integer DOOR_FOUR = 2;
    public static final String DOOR_FOUR_STR = "Four Doors";
    public static final Integer DOOR_FIVE = 3;
    public static final String DOOR_FIVE_STR = "Five Doors";

    public static Map<Integer, String> DOOR_MAP = null;

    static {
        DOOR_MAP = new HashMap<>();
        DOOR_MAP.put(DOOR_TWO, DOOR_TWO_STR);
        DOOR_MAP.put(DOOR_THREE, DOOR_THREE_STR);
        DOOR_MAP.put(DOOR_FOUR, DOOR_FOUR_STR);
        DOOR_MAP.put(DOOR_FIVE, DOOR_FIVE_STR);
    }

    public static String setDoorsVo(Integer doors) {
        return DOOR_MAP.get(doors);
    }

    public static Integer VEHICLE_ONE_SEAT = 1;
    public static String VEHICLE_ONE_SEAT_STR = "One seat";
    public static Integer VEHICLE_TWO_SEAT = 2;
    public static String VEHICLE_TWO_SEAT_STR = "Two seats";
    public static Integer VEHICLE_THREE_SEAT = 3;
    public static String VEHICLE_THREE_SEAT_STR = "Three seats";
    public static Integer VEHICLE_FOUR_SEAT = 4;
    public static String VEHICLE_FOUR_SEAT_STR = "Four seats";
    public static Integer VEHICLE_FIVE_SEAT = 5;
    public static String VEHICLE_FIVE_SEAT_STR = "Five seats";
    public static Integer VEHICLE_SIX_SEAT = 6;
    public static String VEHICLE_SIX_SEAT_STR = "Six seats";
    public static Integer VEHICLE_SEVEN_SEAT = 7;
    public static String VEHICLE_SEVEN_SEAT_STR = "Seven seats";
    public static Integer VEHICLE_SEVEN_PLUS_SEAT = 8;
    public static String VEHICLE_SEVEN_PLUS_SEAT_STR = "Seven plus seats";

    public static Map<Integer, String> VEHICLE_SEAT_MAP = null;

    static{
        VEHICLE_SEAT_MAP = new HashMap<>();
        VEHICLE_SEAT_MAP.put(VEHICLE_ONE_SEAT, VEHICLE_ONE_SEAT_STR);
        VEHICLE_SEAT_MAP.put(VEHICLE_TWO_SEAT, VEHICLE_TWO_SEAT_STR);
        VEHICLE_SEAT_MAP.put(VEHICLE_THREE_SEAT, VEHICLE_THREE_SEAT_STR);
        VEHICLE_SEAT_MAP.put(VEHICLE_FOUR_SEAT, VEHICLE_FOUR_SEAT_STR);
        VEHICLE_SEAT_MAP.put(VEHICLE_FIVE_SEAT, VEHICLE_FIVE_SEAT_STR);
        VEHICLE_SEAT_MAP.put(VEHICLE_SIX_SEAT, VEHICLE_SIX_SEAT_STR);
        VEHICLE_SEAT_MAP.put(VEHICLE_SEVEN_SEAT, VEHICLE_SEVEN_SEAT_STR);
    }

    public static String setSeatNumVo(Integer seatNum) {
        if(seatNum > 7){
            return VEHICLE_SEVEN_PLUS_SEAT_STR;
        }else{
            return VEHICLE_SEAT_MAP.get(seatNum);
        }
    }
}
