package com.project.shoppingmall.utils;

import com.project.shoppingmall.config.FileStorageConfig;
import com.project.shoppingmall.config.S3Config;
import com.project.shoppingmall.config.properties.S3Properties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Import({FileStorageConfig.class, S3Properties.class, S3Config.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableProjectAmazonS3Configuration {
}
