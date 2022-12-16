package com.project.shoppingmall.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.project.shoppingmall.config.properties.S3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class S3Config {
    private final S3Properties properties;

    @Bean
    public AmazonS3Client amazonS3Client() {
        S3Properties.Credentials credentials = properties.getCredentials();
        S3Properties.Region region = properties.getRegion();

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(credentials.getAccessKey(), credentials.getSecretKey());
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region.getStatic())
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}