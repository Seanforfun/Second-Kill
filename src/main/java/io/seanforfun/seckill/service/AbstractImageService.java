package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.ImageDao;
import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.enums.ImageFormat;
import io.seanforfun.seckill.entity.enums.ImageSource;
import io.seanforfun.seckill.entity.enums.ImageType;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.redis.ImageKey;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.redis.VehicleKey;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.service.ebi.ImageEbi;
import io.seanforfun.seckill.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.print.DocFlavor;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Configuration
@ConfigurationProperties
@PropertySource(value="classpath:/properties/image.properties")
public abstract class AbstractImageService implements ImageEbi<Image, Image> {

    @Autowired
    private ObjectFactory<Image> imageFactory;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ImageDao imageDao;

    protected void checkImage(String name, String path, byte[] imageBytes){
        if(StringUtils.isEmpty(name)){
            throw new GlobalException(CodeMsg.IMAGE_NAME_EMPTY_MSG);
        }
        if(imageBytes == null || imageBytes.length == 0){
            throw new GlobalException(CodeMsg.IMAGE_CONTENT_EMPTY_MSG);
        }
        if(StringUtils.isEmpty(path)){
            throw new GlobalException(CodeMsg.PATH_EMPTY_ERROR_MSG);
        }
    }

    @Override
    public Image getInitializedImage(String name, ImageSource imageSource, byte[] imageBytes,
                                        ImageType imageType, Long associateId){
        Image emptyImage = imageFactory.getObject();
        String suffix = name.substring(name.lastIndexOf('.') + 1);
        emptyImage.setImageFormat(checkImageFormat(suffix));
        emptyImage.setId(SnowFlakeUtils.getSnowFlakeId());
        emptyImage.setName(name);
        emptyImage.setSource(imageSource);
        emptyImage.setImageByte(imageBytes);
        emptyImage.setType(imageType);
        emptyImage.setAssociateId(associateId);
        emptyImage.setExist(Image.IMAGE_EXIST);
        return emptyImage;
    }

    public static ImageFormat checkImageFormat(String suffix){
        String uppercaseSuffix = suffix.toUpperCase();
        switch (uppercaseSuffix) {
            case "PNG":
                return ImageFormat.PNG_IMAGE;
            case "GIF":
                return ImageFormat.GIF_IMAGE;
            default:
                return ImageFormat.JPEG_IMAGE;
        }
    }

    protected String getLinkFromImage(Image image, ImageSource imageSource){
        if(image == null){
            throw new NullPointerException();
        }
        if(image.getSource() != imageSource){
            throw new GlobalException(CodeMsg.GET_IMAGE_SOURCE_ERROR_MSG);
        }
        String link = image.getLink();
        if(link == null || StringUtils.isEmpty(link)){
            throw new GlobalException(CodeMsg.INVALID_IMAGE_MSG);
        }
        return link;
    }

    @Override
    @Transactional
    public List<Image> uploadImages(Collection<Image> images, ImageType imageType, Long associateId) throws Exception {
        List<Image> imageList = null;
        int imageNum = images.size();
        float count = 0F;
        // Step 1: Set initial percentage
        redisService.set(VehicleKey.getVehicleUploadPercentageById, "" + associateId, 0F);
        for(Image image : images){
            Image savedImage = uploadImageAsync(image, imageType, associateId);
            if(imageList == null){
                imageList = new LinkedList<>();
            }
            imageList.add(savedImage);
            redisService.set(VehicleKey.getVehicleUploadPercentageById, "" + associateId, (++count) / (float)imageNum);
        }
        return imageList;
    }

    @Override
    @Transactional
    public void deleteImages(Collection<Image> images) throws Exception {
        for(Image image : images){
            deleteImage(image);
        }
    }

    @Override
    public Image getImage(Long id) throws Exception{
        Image image;
        image = redisService.get(ImageKey.getImageById, "" + id, Image.class);
        if(image != null){
            return image;
        }
        image =  imageDao.getImageById(id);
        redisService.set(ImageKey.getImageById, "" + id, Image.class);
        return image;
    }

    @Override
    public List<Image> getImageListByVehicleId(Long vehicleId) throws Exception{
        List<Image> images = null;
        images = redisService.getList(ImageKey.getImageListByVehicleId, "" + vehicleId, Image.class);
        if(images == null){
            images = imageDao.getImagesByVehicleId(vehicleId);
            //Load image Contents(Base64 String)
            images = getImageBase64StringForImages(images);
            redisService.set(ImageKey.getImageListByVehicleId, "" + vehicleId, images);
        }
        return images;
    }

    @Override
    public List<Image> getImageBase64StringForImages(List<Image> images) throws Exception{
        for(int i = 0; i < images.size(); i++){
            Image image = images.get(i);
            image.setImageBase64String(getBase64String(image));
        }
        return images;
    }
}
