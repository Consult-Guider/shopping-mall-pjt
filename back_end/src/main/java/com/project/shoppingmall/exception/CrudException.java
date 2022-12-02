package com.project.shoppingmall.exception;

import com.project.shoppingmall.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class CrudException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public CrudException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public String printErrorMessage() {
        return message!=null
                ? String.format("%s\n%s",errorCode.getMessage(), message)
                : errorCode.getMessage();
    }
}
