package com.project.shoppingmall.utils;

public class JsonPathUtil {
    public static String makeBaseJsonPath(String attr) {
        return String.format("$.data.%s", attr);
    }

    public static String makePathJsonPath(Integer index, String attr) {
        return String.format("$.content[%s].%s", index, attr);
    }

    public static String makePathJsonPath(String attr) {
        return String.format("$.content[%s].%s", 0, attr);
    }
}
