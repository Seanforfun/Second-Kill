package io.seanforfun.seckill.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import io.seanforfun.seckill.exceptions.GlobalException;
import io.seanforfun.seckill.result.CodeMsg;
import reactor.util.annotation.Nullable;

import java.util.List;

public class AwsS3Utils {

    /**
     * Get all bucket for current user.
     * @param client
     * @return All Buckets
     */
    public static List<Bucket> getAllBuckets(AmazonS3 client){
        return client.listBuckets();
    }

    /**
     * Create a bucket using bucket name and region.
     * @param client
     * @param bucketName
     * @param awsRegion
     */
    public static void createBucket(AmazonS3 client, String bucketName, String awsRegion){
        if(!client.doesBucketExistV2(bucketName)){
            // TODO, Need to fill the specified error.
            throw new GlobalException(CodeMsg.AWS_BUCKET_DUPLICATE_NAME_MSG);
        }else{
            if(awsRegion == null){
                client.createBucket(bucketName);
            }else {
                client.createBucket(new CreateBucketRequest(bucketName, awsRegion));
            }
        }
    }

    /**
     * Check if bucket with name exists.
     * @param client
     * @param bucketName
     * @return
     */
    public static boolean bucketExists(AmazonS3 client, String bucketName){
        List<Bucket> buckets = client.listBuckets();
        for(Bucket bucket : buckets){
            if(bucket.getName().equals(bucketName)){
                return true;
            }
        }
        return false;
    }

    public static String getS3FileLink(@Nullable String region, String bucketName, String key){
        StringBuffer sb = new StringBuffer("http://s3");
        if(region != null){
            sb.append("-" + region);
        }
        sb.append(".amazonaws.com/");
        sb.append(bucketName + "/");
        sb.append(key);
        return sb.toString();
    }

}
