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
    public AmazonS3 client(){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsAccessSecret);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }
}
