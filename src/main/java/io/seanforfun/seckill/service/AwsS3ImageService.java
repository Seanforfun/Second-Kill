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
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

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
public class AwsS3ImageService extends AbstractImageService implements ImageEbi<Image, Image> {

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    @Qualifier("asyncClient")
    private S3AsyncClient s3AsyncClient;

    @Autowired
    private AwsCredentials credentials;

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
    public Image uploadImage(Image image, ImageType imageType, Long associateId) throws Exception {
        // Step 1: Get initialized message instance.
        // Did that in controller level
        // Step 2: Upload image to AWS.
        InputStream is = null;
        String bucketName;
        try{
            is = new ByteArrayInputStream(image.getImageByte());
            bucketName = imageType == ImageType.USER_IMAGE ? userBucketName : vehicleBucketName;
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(is.available());
            // Only owner has the right to read and write.
            s3Client.putObject(new PutObjectRequest(bucketName, "" + image.getId(), is, meta)
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

    @Override
    @Transactional
    public Image uploadImageAsync(Image image, ImageType imageType, Long associateId) throws IOException {
        // Step 1: Get initialized message instance.
        // Already did that in controller level.
        // Step 2: Upload image to AWS.
        String bucketName = null;
        byte[] imageBytes = image.getImageByte();
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(imageBytes.length);
            byteBuffer.put(imageBytes);
            bucketName = imageType == ImageType.USER_IMAGE ? userBucketName : vehicleBucketName;
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(imageBytes.length);
            CompletableFuture<PutObjectResponse> future = s3AsyncClient.putObject(software.amazon.awssdk.services.s3.model.PutObjectRequest.builder()
                            .acl(ObjectCannedACL.BUCKET_OWNER_FULL_CONTROL)
                            .bucket(bucketName)
                            .key("" + image.getId())
                            .build(),
                    AsyncRequestBody.fromByteBuffer(byteBuffer));
            future.whenComplete((resp, err) -> {
               if(err != null){
                   err.printStackTrace();
                   throw new GlobalException(CodeMsg.AWS_FILE_UPLOAD_FAILED_MSG);
               }
            });
        } catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(CodeMsg.AWS_FILE_UPLOAD_FAILED_MSG);
        }
        String s3FileLink = AwsS3Utils.getS3FileLink(awsRegion, bucketName, "" + image.getId());
        image.setLink(s3FileLink);
        // Step 3: Save image information to Database.
        imageDao.saveImageInfo(image);
        return image;
    }

    // Get image
    @Override
    public String getBase64String(Image image) throws IOException {
        String bucketName = image.getType() == ImageType.VEHICLE_IMAGE ? vehicleBucketName : userBucketName;
        S3Object response = s3Client.getObject(new GetObjectRequest(bucketName, "" + image.getId()));
        int contentLen = (int)response.getObjectMetadata().getContentLength();
        InputStream content = null;
        ByteArrayInputStream byteInputStream = null;
        byte[] storeBuf = new byte[contentLen];
        // Load data from AWS S3 to local buffer "storeBuf"
        try{
            content = response.getObjectContent();
            int contentLength = (int)response.getObjectMetadata().getContentLength();
            byte[] imageByte = new byte[2048];
            byteInputStream = new ByteArrayInputStream(storeBuf);
            int read;
            int sum = 0;
            while ((read = content.read(imageByte)) > 0){
                sum += read;
                byteInputStream.read(imageByte, 0, read);
            }
            if (sum != contentLen){
                throw new GlobalException(CodeMsg.LOAD_IMAGE_BYTE_ERROR_MSG);
            }
        }catch (Exception e){
            throw new GlobalException(CodeMsg.LOAD_IMAGE_BYTE_ERROR_MSG);
        }finally {
            if(content != null) content.close();
            if(byteInputStream != null) byteInputStream.close();
        }
        return Base64.encodeBase64String(storeBuf);
    }

    public String getBase64StringAsync(Image image) throws Exception {
        String bucketName = image.getType() == ImageType.USER_IMAGE ? userBucketName : vehicleBucketName;
        CompletableFuture<ResponseBytes<GetObjectResponse>> getFuture = s3AsyncClient.getObject(software.amazon.awssdk.services.s3.model.GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key("" + image.getId())
                        .build(),
                AsyncResponseTransformer.toBytes());
        CompletableFuture<String> handle = getFuture.handle((resp, err) -> {
            if(err != null){
                throw new GlobalException(CodeMsg.LOAD_IMAGE_BYTE_ERROR_MSG);
            }
            byte[] imageBytes = resp.asByteArray();
            return Base64.encodeBase64String(imageBytes);
        });
        return handle.get();
    }

    @Override
    public boolean imageExists(Image image) throws Exception {
        String bucketName = image.getType() == ImageType.USER_IMAGE ? userBucketName : vehicleBucketName;
        return s3Client.doesObjectExist(bucketName, "" + image.getId());
    }

    // Remove image.
    @Override
    public Image deleteImage(Image image) throws Exception {
        String bucketName = image.getType() == ImageType.USER_IMAGE ? userBucketName : vehicleBucketName;
        if(imageExists(image)){
            try {
                s3Client.deleteObject(new DeleteObjectRequest(bucketName, "" + image.getId()));
            }catch (Exception e){
                throw new GlobalException(CodeMsg.AWS_DELETE_IMAGE_ERROR_MSG);
            }
        }
        imageDao.updateImageExistById(image.getId(), Image.IMAGE_NOT_EXIST);
        return image;
    }

    public Image deleteImageAsync(Image image) throws  Exception{
        String bucketName = image.getType() == ImageType.USER_IMAGE ? userBucketName : vehicleBucketName;
        CompletableFuture<DeleteObjectResponse> deleteFuture = s3AsyncClient.deleteObject(software.amazon.awssdk.services.s3.model.DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key("" + image.getId())
                .build());
        deleteFuture.whenComplete((resp, err) -> {
            if(err != null){
                err.printStackTrace();
                throw new GlobalException(CodeMsg.AWS_DELETE_IMAGE_ERROR_MSG);
            }
        });
        imageDao.updateImageExistById(image.getId(), Image.IMAGE_NOT_EXIST);
        return image;
    }
}
