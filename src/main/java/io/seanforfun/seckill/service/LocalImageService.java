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
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
            //TODO Bug, what if length is bigger than Integer.MAX_VALUE
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

    //Get method
    @Override
    public String getBase64String(Image image) throws IOException {
        // Step 1: Get link
       String link = getLinkFromImage(image, ImageSource.IMAGE_FROM_LOCAL);
        // Step 2: Save image as byte array from local file system.
        RandomAccessFile file = null;
        FileChannel channel = null;
        try {
            file = new RandomAccessFile(link, "r");
            // Image size is not able to bigger than 2047GB.
            int byteNum = (int)file.length();
            ByteBuffer byteBuffer = ByteBuffer.allocate(byteNum);
            channel = file.getChannel();
            channel.read(byteBuffer);
            byte[] array = byteBuffer.array();
            return Base64.encodeBase64String(array);
        } finally {
            if(file != null) file.close();
            if(channel != null) channel.close();
        }
    }

    @Override
    public boolean imageExists(Image image) throws Exception {
        String link = image.getLink();
        File file = new File(link);
        return file.isFile() && file.exists();
    }

    //Deletion methods
    @Override
    @Transactional
    public Image deleteImage(Image image) throws Exception {
        if(image == null || image.getLink() == null){
            throw new NullPointerException();
        }
        String link = image.getLink();
        if(imageExists(image)){
            deleteImage(link, image.getId());
        }
        return image;
    }

    private Image deleteImage(String link, Long id) throws Exception {
        File imageFile = new File(link);
        if(imageFile.exists()){
            imageFile.delete();
        }
        imageDao.updateImageExistById(id,Image.IMAGE_NOT_EXIST);
        return null;
    }
}

