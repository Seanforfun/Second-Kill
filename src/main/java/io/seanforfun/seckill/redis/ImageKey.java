package io.seanforfun.seckill.redis;

import io.seanforfun.seckill.entity.domain.Image;

public class ImageKey extends BasePrefix {

    private static int IMAGE_EXPIRE = 3600;

    private ImageKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }

    public static ImageKey getImageById = new ImageKey(IMAGE_EXPIRE, "getImageById");
    public static ImageKey getImageListByVehicleId = new ImageKey(IMAGE_EXPIRE, "getImageListByVehicleId");
}
