package com.project.shoppingmall.utils;

import com.project.shoppingmall.config.QueryDslConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Import({QueryDslConfig.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableProjectQueryDslConfiguration {
}
