package com.project.shoppingmall.utils.fileManager;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.shoppingmall.config.properties.S3Properties;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class S3FileManager implements FileManager<MultipartFile, String> {
    private final AmazonS3Client amazonS3Client;
    private final S3Properties properties;
    private final String bucketName;

    public S3FileManager(AmazonS3Client amazonS3Client, S3Properties properties) {
        this.amazonS3Client = amazonS3Client;
        this.properties = properties;

        this.bucketName = properties.getS3().getBucket();
    }

    @Override
    public String saveFile(MultipartFile src, String trgPath) throws IOException {
        final ObjectMetadata metaData = new ObjectMetadata();

        try(InputStream inputStream = src.getInputStream()) {
            amazonS3Client.putObject(bucketName, trgPath, inputStream, metaData);
        }
        return amazonS3Client.getUrl(bucketName, trgPath).toString();
    }

    @Override
    public void deleteFile(String trgPath) {
        amazonS3Client.deleteObject(bucketName, trgPath);
    }
}
