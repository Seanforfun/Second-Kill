package io.seanforfun.seckill.service;

import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.enums.ImageSource;
import io.seanforfun.seckill.entity.enums.ImageType;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.utils.SnowFlakeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractImageService {

    @Autowired
    private ObjectFactory<Image> imageFactory;

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
        return emptyImage;
    }
}
