package com.project.shoppingmall.utils;

public class UrlPrefixManager {
    public static final String API_VERSION = "/api/v1";

    public static String addPrefix(String url) {
        return String.format("%s%s", API_VERSION, url);
    }
}
