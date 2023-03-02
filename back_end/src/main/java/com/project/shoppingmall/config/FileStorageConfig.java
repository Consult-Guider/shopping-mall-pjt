package com.project.shoppingmall.config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.project.shoppingmall.config.properties.S3Properties;
import com.project.shoppingmall.utils.fileManager.FileManager;
import com.project.shoppingmall.utils.fileManager.S3FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

@Configuration
@RequiredArgsConstructor
public class FileStorageConfig {
    private final S3Properties properties;
    private final AmazonS3Client amazonS3Client;

    @Bean
    public FileManager<MultipartFile, String> fileManager() {
        return new S3FileManager(amazonS3Client, properties);
    }
}
