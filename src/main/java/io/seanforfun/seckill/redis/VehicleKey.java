package io.seanforfun.seckill.redis;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/4 13:55
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
public class VehicleKey extends BasePrefix {
    private static final int VEHICLE_EXPIRE_SECOND = 300;
    private static final int VEHICLE_QR_EXPIRE_SECOND = NEVER_EXPIRE;

    public VehicleKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }

    public VehicleKey(String prefix) {
        super(prefix);
    }
    public static VehicleKey getKeyForId = new VehicleKey(VEHICLE_EXPIRE_SECOND, "id");
    public static VehicleKey getGetKeyForList = new VehicleKey(VEHICLE_EXPIRE_SECOND, "list");
    public static VehicleKey getVehicleById = new VehicleKey(VEHICLE_EXPIRE_SECOND, "vehicle_id");
    public static VehicleKey getVehicleUploadPercentageById = new VehicleKey(VEHICLE_EXPIRE_SECOND, "uploadPercent");
    public static VehicleKey vehicleVinExists = new VehicleKey(NEVER_EXPIRE, "vinExist");

    public static VehicleKey getQRById = new VehicleKey(VEHICLE_QR_EXPIRE_SECOND, "vehicle_qr");
}
