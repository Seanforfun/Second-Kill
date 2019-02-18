package io.seanforfun.seckill.service;

import io.seanforfun.seckill.dao.ImageDao;
import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.enums.ImageSource;
import io.seanforfun.seckill.entity.enums.ImageType;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.service.ebi.ImageEbi;
import io.seanforfun.seckill.utils.MD5Utils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
public class LocalImageService extends AbstractImageService implements ImageEbi<MultipartFile, Image> {

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
        byte[] imageBytes = image.getBytes();
        checkImage(name, path, imageBytes);
        // Set Image detail
        Image emptyImage = getInitializedImage(name, ImageSource.IMAGE_FROM_LOCAL,
                imageBytes, imageType, associateId);
        emptyImage.setLink(MD5Utils.localImagePath(name, "" + emptyImage.getId(), path, dictNum, ImageType.VEHICLE_IMAGE));

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
    public Image deleteImage(Image image) throws Exception {
        if(image == null || image.getLink() == null){
            throw new NullPointerException();
        }
        String link = image.getLink();
        return deleteImage(link);
    }

    private Image deleteImage(String link) throws Exception {
        File imageFile = new File(link);
        if(imageFile.exists()){
            imageFile.delete();
        }
        imageDao.deleteImageByLink(link);
        return null;
    }

    @Override
    public void deleteImages(Collection<Image> images) throws Exception {
        List<String> links = images.stream().map(Image::getLink).collect(Collectors.toList());
        deleteImagesByLink(links);
    }

    /**
     * Delete all saveImages by link
     * @param links
     * @return
     * @throws Exception
     */
    private void deleteImagesByLink(Collection<String> links) throws Exception {
        for(String link : links){
            deleteImage(link);
        }
    }
}

