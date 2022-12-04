package com.project.shoppingmall.utils;

import com.project.shoppingmall.type.RoleType;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithSecurityContextFactoryImpl.class)
public @interface WithAuthenticationPrincipal {
    RoleType role() default RoleType.USER;
}
