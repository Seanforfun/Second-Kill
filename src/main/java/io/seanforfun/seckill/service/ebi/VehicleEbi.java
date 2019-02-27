package io.seanforfun.seckill.service.ebi;

import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.domain.VehicleDetail;
import io.seanforfun.seckill.entity.vo.VehicleVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
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
     * @param images
     */
    Long saveVehicle(VehicleDetail vehicleDetail, Long creatorId, Collection<Image> images) throws Exception;

    /**
     * Get byte array of vehicle's QR code according to vehicle's id.
     * @return
     */
    String getBase64QrCodeById(Long id) throws IOException;


    /**
     * This request receives QR code as request, and response to server side.
     * @param id
     * @return
     */
    VehicleDetail getQRVehicleById(Long id);

    /**
     * Get vehicle information by vehicle id.
     * @param id
     */
    VehicleDetail getVehicleInfoById(Long id);

    /**
     * Get Image list according to associateId id.
     * @param id
     * @return
     */
    List<Image> getVehicleImagesById(Long id) throws Exception;
}
