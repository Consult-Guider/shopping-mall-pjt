package com.project.shoppingmall.utils;

import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Profile을 일괄 변경 가능하도록 하기 위해 만든 어노테이션.
@ActiveProfiles({"local"})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SetProfile {
}
