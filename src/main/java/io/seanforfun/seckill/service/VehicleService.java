package io.seanforfun.seckill.service;

import io.nayuki.qrcodegen.QrCode;
import io.seanforfun.seckill.dao.VehicleDao;
import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.domain.Vehicle;
import io.seanforfun.seckill.entity.domain.VehicleDetail;
import io.seanforfun.seckill.entity.enums.ImageType;
import io.seanforfun.seckill.entity.vo.VehicleVo;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.service.ebi.ImageEbi;
import io.seanforfun.seckill.service.ebi.VehicleEbi;
import io.seanforfun.seckill.utils.SnowFlakeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    @Qualifier("imgurService")
    private ImageEbi imageService;

    @Autowired
    private ServerProperties serverProperties;

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
    public void saveVehicle(VehicleDetail vehicleDetail, Long creatorId, Map<String, MultipartFile> imageMap) throws Exception {
        // Step 1: Fill vehicle detail object
        vehicleDetail.setId(SnowFlakeUtils.getSnowFlakeId());
        vehicleDetail.setCreatorId(creatorId);
        vehicleDetail.setCreateTime(System.currentTimeMillis());
        vehicleDetail.setLastModifierId(Vehicle.VEHICLE_NEVER_MODIFIED_USER);
        vehicleDetail.setLastModifyTime(Vehicle.VEHICLE_NEVER_MODIFIED);

        // Need to find a image database for saving all images, for example S3, imgur, local
        // Step 1: Save images to S3, and get the reference.
        // Step 2: Save image info to database.
        List<Image> images = null;
        try {
            images = imageService.uploadImages(imageMap.values(), ImageType.VEHICLE_IMAGE, vehicleDetail.getId());
            if(images == null || images.size() == 0){
                throw new GlobalException(CodeMsg.NO_VEHICLE_IMAGE_SAVED_ERROR_MSG);
            }
            vehicleDao.saveVehicle(vehicleDetail);
            vehicleDao.saveVehicleDetail(vehicleDetail);
        }catch (Exception e){
            // Step 1: Roll back for images.
            if(images != null){
                imageService.deleteImages(images);
            }
            // Step 2: Throw the exception for transaction.
            throw e;
        }
    }

    private String createQRCodeHttpRequest(Long id){
        return "http://" + serverProperties.getAddress() + ":" + serverProperties.getPort() + "/vehicle/qr/" + id;
    }

    @Override
    public byte[] getQrCodeById(Long id) throws IOException {
        String qrText = createQRCodeHttpRequest(id);
        // Up to 30 % error correction.
        QrCode.Ecc ecc = QrCode.Ecc.HIGH;
        QrCode qrCode = QrCode.encodeText(qrText, ecc);
        BufferedImage bufferedImage = qrCode.toImage(20, 10);
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            // Release the stream.
            byte[] resBytes = byteArrayOutputStream.toByteArray();
            return resBytes;
        }finally {
            if(byteArrayOutputStream != null){
                byteArrayOutputStream.close();
            }
        }
    }

    @Override
    public VehicleDetail getQRVehicleById(Long id) {
        return null;
    }
}
