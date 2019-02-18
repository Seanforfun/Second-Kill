package io.seanforfun.seckill.service.ebi;

import io.seanforfun.seckill.entity.domain.VehicleDetail;
import io.seanforfun.seckill.entity.vo.VehicleVo;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
     * @param imageMap
     */
    void saveVehicle(VehicleDetail vehicleDetail, Long creatorId, Map<String, MultipartFile> imageMap) throws Exception;

    /**
     * Get byte array of vehicle's QR code according to vehicle's id.
     * @return
     */
    byte[] getQrCodeById(Long id) throws IOException;


    /**
     * This request receives QR code as request, and response to server side.
     * @param id
     * @return
     */
    VehicleDetail getQRVehicleById(Long id);
}
