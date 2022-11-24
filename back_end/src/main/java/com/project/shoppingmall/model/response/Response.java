package com.project.shoppingmall.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class Response<T> {
    private final String status;
    private final T data;

    public static <T> Response<T> success(T data) {
        return new Response<>("SUCCESS", data);
    }

    public static Response<Void> error(String errorMessage) {
        return new Response<>(errorMessage, null);
    }
}
