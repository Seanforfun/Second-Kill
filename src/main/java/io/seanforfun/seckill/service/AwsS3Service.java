package io.seanforfun.seckill.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

@Service
@Configuration
@PropertySource(value = "classpath:/properties/image.properties")
public class AwsS3Service {

    @Value("${image.aws.accessKey}")
    private String awsAccessKey;

    @Value("${image.aws.accessSecret}")
    private String awsAccessSecret;

    @Value("${image.aws.region}")
    private String region;


    @Bean
    public AmazonS3 s3Client(){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsAccessSecret);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }

    @Bean
    public S3AsyncClient asyncClient(){
        return S3AsyncClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKey, awsAccessSecret)))
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    public AwsCredentials credentials(){
        return AwsBasicCredentials.create(awsAccessKey, awsAccessSecret);
    }
}
