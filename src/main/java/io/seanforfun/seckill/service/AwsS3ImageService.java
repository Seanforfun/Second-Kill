package io.seanforfun.seckill.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import io.seanforfun.seckill.dao.ImageDao;
import io.seanforfun.seckill.entity.domain.Image;
import io.seanforfun.seckill.entity.enums.ImageSource;
import io.seanforfun.seckill.entity.enums.ImageType;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.result.CodeMsg;
import io.seanforfun.seckill.service.ebi.ImageEbi;
import io.seanforfun.seckill.utils.AwsS3Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/18 17:09
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
@Configuration
@PropertySource(value = "classpath:/properties/image.properties")
public class AwsS3ImageService extends AbstractImageService implements ImageEbi<MultipartFile, Image> {

    @Autowired
    private AmazonS3 client;

    @Value("${image.aws.bucket.userBucket}")
    private String userBucketName;

    @Value("${image.aws.bucket.vehicleBucket}")
    private String vehicleBucketName;

    @Value("${image.aws.region}")
    private String awsRegion;

    @Autowired
    private ImageDao imageDao;

    // Add image
    @Override
    public Image uploadImage(MultipartFile file, ImageType imageType, Long associateId) throws Exception {
        // Step 1: Get initialized message instance.
        Image image = getInitializedImage(file.getName(), ImageSource.IMAGE_FROM_FROM_AWS,
                file.getBytes(), imageType, associateId);
        // Step 2: Upload image to AWS.
        InputStream is = null;
        String bucketName = null;
        try{
            is = file.getInputStream();
            bucketName = imageType == ImageType.USER_IMAGE ? userBucketName : vehicleBucketName;
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(is.available());
            // Only owner has the right to read and write.
            client.putObject(new PutObjectRequest(bucketName, "" + image.getId(), is, meta)
                    .withCannedAcl(CannedAccessControlList.Private));
        } catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(CodeMsg.AWS_FILE_UPLOAD_FAILED_MSG);
        }finally {
            if(is != null){
                is.close();
            }
        }
        String s3FileLink = AwsS3Utils.getS3FileLink(awsRegion, bucketName, "" + image.getId());
        image.setLink(s3FileLink);

        // Step 3: Save image information to Database.
        imageDao.saveImageInfo(image);
        return image;
    }

    // Get image
    @Override
    // TODO bug here.
    public String getBase64String(Image image) throws IOException {
        String bucketName = image.getType() == ImageType.VEHICLE_IMAGE ? vehicleBucketName : userBucketName;
        S3Object response = client.getObject(new GetObjectRequest(bucketName, "" + image.getId()));
        InputStream content = response.getObjectContent();
        File file = new File("F:\\upload\\aws.png");
        OutputStream os = new FileOutputStream(file);
        int contentLength = (int)response.getObjectMetadata().getContentLength();
        byte[] imageByte = new byte[1024];
        int sum = 0;
        int load = 0;
        while ((load = content.read(imageByte, 0, 1024)) != -1){
            sum += load;
            os.write(imageByte);
        }
        System.out.println(sum);
//        os.flush();
//        os.close();
//        content.close();
        return null;
    }

    // Remove image.
    @Override
    public Image deleteImage(Image image) throws Exception {
        String bucketName = image.getType() == ImageType.USER_IMAGE ? userBucketName : vehicleBucketName;
        client.deleteObject(new DeleteObjectRequest(bucketName, "" + image.getId()));
        imageDao.updateImageExistById(image.getId(), Image.IMAGE_NOT_EXIST);
        return image;
    }
}
