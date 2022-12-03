package com.project.shoppingmall.utils;

import com.project.shoppingmall.config.ElasticSearchConfig;
import com.project.shoppingmall.config.JpaConfig;
import com.project.shoppingmall.config.properties.ElasticSearchProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Import({ElasticSearchConfig.class, ElasticSearchProperties.class, JpaConfig.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableProjectElasticSearchConfiguration {
}
