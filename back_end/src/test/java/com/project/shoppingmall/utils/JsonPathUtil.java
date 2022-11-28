package com.project.shoppingmall.utils;

public class JsonPathUtil {
    public static String makeBaseJsonPath(String attr) {
        return String.format("$.data.%s", attr);
    }
}
