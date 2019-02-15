package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.ImageDao;
import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.enums.ImageSource;
import io.seanforfun.seckill.entity.enums.ImageType;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.service.ebi.ImageEbi;
import io.seanforfun.seckill.utils.MD5Utils;
import io.seanforfun.seckill.utils.SnowFlakeUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/15 11:45
 * @description: This Service implements ImageEbi and you can use this service to upload
 * Image(s) to localfile system, as well as deletion images.
 * @modified:
 * @version: 0.0.1
 */
@Service
@Configuration
@PropertySource(value = "classpath:/properties/image.properties")
public class LocalImageService implements ImageEbi<MultipartFile, Image> {

    @Value("${image.local.path}")
    @Getter
    @Setter
    private String path;

    @Value("${image.local.dict-num}")
    @Getter
    @Setter
    private Integer dictNum;

    @Autowired
    private ImageDao imageDao;

    // Create methods
    @Override
    @Transactional
    public Image uploadImage(MultipartFile image, ImageType imageType, Long associateId) throws Exception {
        // Check parameters
        String name = image.getName();
        if(StringUtils.isEmpty(name)){
            throw new GlobalException(CodeMsg.IMAGE_NAME_EMPTY_MSG);
        }
        byte[] imageBytes = image.getBytes();
        if(imageBytes == null || imageBytes.length == 0){
            throw new GlobalException(CodeMsg.IMAGE_CONTENT_EMPTY_MSG);
        }
        if(StringUtils.isEmpty(path)){
            throw new GlobalException(CodeMsg.PATH_EMPTY_ERROR_MSG);
        }
        // Set Image detail
        Image emptyImage = new Image();
        emptyImage.setId(SnowFlakeUtils.getSnowFlakeId());
        emptyImage.setName(name);
        emptyImage.setSource(ImageSource.IMAGE_FROM_LOCAL);
        emptyImage.setImageByte(imageBytes);
        emptyImage.setType(imageType);
        emptyImage.setAssociateId(associateId);
        emptyImage.setLink(MD5Utils.localImagePath(name, "" + emptyImage.getId(), path, dictNum));

        //Save Image to file system
        String link = emptyImage.getLink();
        File directory = new File(link.substring(0, link.lastIndexOf(File.separatorChar) + 1));;
        if(!directory.exists()){
            directory.mkdirs();
        }

        File imageFile = new File(emptyImage.getLink());
        if(!imageFile.exists()){
            boolean createdImage = imageFile.createNewFile();
            if(!createdImage){
                throw new GlobalException(CodeMsg.SERVER_ERROR_MSG);
            }
        }
        Path imagePath = Paths.get(emptyImage.getLink());
        AsynchronousFileChannel imageChannel = null;
        try {
            imageChannel = AsynchronousFileChannel.open(imagePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            ByteBuffer imageBuffer = ByteBuffer.allocate(imageBytes.length);
            imageBuffer.put(imageBytes);
            imageBuffer.flip();
            Future<Integer> writeAction = imageChannel.write(imageBuffer, 0);
            // Save image information into database.
            imageDao.saveImageInfo(emptyImage);
            while (!writeAction.isDone());
            writeAction.get();
        }finally {
            if(imageChannel != null){
                imageChannel.close();
            }
        }
        return emptyImage;
    }

    @Override
    @Transactional
    public List<Image> uploadImages(Collection<MultipartFile> images, ImageType imageType, Long associateId) throws Exception {
        List<Image> imagesList = new LinkedList<>();
        for(MultipartFile image : images){
            Image singleImage = uploadImage(image, imageType, associateId);
            imagesList.add(singleImage);
        }
        return imagesList;
    }

    // Read methods
    @Override
    public Image getImage(String link) throws Exception {
        return null;
    }

    //Deletion methods
    @Override
    public Image deleteImage(Long id) throws Exception {
        return null;
    }

    @Override
    public Image deleteImageByLink(String link) throws Exception {
        File imageFile = new File(link);
        if(imageFile.exists()){
            imageFile.delete();
        }
        return null;
    }

    @Override
    public Image deleteImages(Collection<Long> imagesId) throws Exception {
        return null;
    }

    @Override
    public Image deleteImagesByLink(Collection<String> links) throws Exception {
        for(String link : links){
            deleteImageByLink(link);
        }
        return null;
    }
}

