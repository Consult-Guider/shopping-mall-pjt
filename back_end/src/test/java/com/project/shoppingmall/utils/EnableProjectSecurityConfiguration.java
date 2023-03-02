package com.project.shoppingmall.utils;

import com.project.shoppingmall.config.AuthenticationConfig;
import com.project.shoppingmall.config.PasswordEncoderConfig;
import com.project.shoppingmall.config.properties.JwtProperties;
import com.project.shoppingmall.exception.JwtAccessDeniedHandler;
import com.project.shoppingmall.exception.JwtAuthenticationEntryPoint;
import com.project.shoppingmall.service.UserDetailServiceFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@MockBean({UserDetailServiceFactory.class, JwtAuthenticationEntryPoint.class, JwtAccessDeniedHandler.class})
@Import({AuthenticationConfig.class, JwtProperties.class, PasswordEncoderConfig.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableProjectSecurityConfiguration {
}
