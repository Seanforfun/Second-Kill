package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.ImageDao;
import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.enums.ImageSource;
import io.seanforfun.seckill.entity.enums.ImageType;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.redis.ImageKey;
import io.seanforfun.seckill.redis.RedisService;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.service.ebi.ImageEbi;
import io.seanforfun.seckill.utils.SnowFlakeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractImageService implements ImageEbi<MultipartFile, Image> {

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

    protected Image getInitializedImage(String name, ImageSource imageSource, byte[] imageBytes,
                                        ImageType imageType, Long associateId){
        Image emptyImage = imageFactory.getObject();
        emptyImage.setId(SnowFlakeUtils.getSnowFlakeId());
        emptyImage.setName(name);
        emptyImage.setSource(imageSource);
        emptyImage.setImageByte(imageBytes);
        emptyImage.setType(imageType);
        emptyImage.setAssociateId(associateId);
        emptyImage.setExist(Image.IMAGE_EXIST);
        return emptyImage;
    }

    @Override
    @Transactional
    public List<Image> uploadImages(Collection<MultipartFile> images, ImageType imageType, Long associateId) throws Exception {
        List<Image> imageList = null;
        for(MultipartFile image : images){
            Image savedImage = uploadImage(image, imageType, associateId);
            if(imageList == null){
                imageList = new LinkedList<>();
            }
            imageList.add(savedImage);
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

}
