package com.project.shoppingmall.model.response;

import com.project.shoppingmall.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class Response<T> {
    private final String status;
    private final ErrorCode errorCode;
    private final T data;

    public static <T> Response<T> success(T data) {
        return new Response<>("SUCCESS", null, data);
    }

    public static <T> Response<T> success() {
        return new Response<>("SUCCESS", null, null);
    }

    public static Response<Void> error(String errorMessage, ErrorCode errorCode) {
        return new Response<>(errorMessage, errorCode, null);
    }
}
