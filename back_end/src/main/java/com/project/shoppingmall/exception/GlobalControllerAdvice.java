package com.project.shoppingmall.exception;

import com.project.shoppingmall.model.response.Response;
import com.project.shoppingmall.type.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> errorHandler(AuthenticationException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.printErrorMessage(), e.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> errorHandler(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.error(e.getMessage(), ErrorCode.INVALID_PARAMETER));
    }

    @ExceptionHandler(CrudException.class)
    public ResponseEntity<?> errorHandler(CrudException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.printErrorMessage(), e.getErrorCode()));
    }
}
