package com.project.shoppingmall.exception;

import com.project.shoppingmall.model.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> errorHandler(AuthenticationException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.printErrorMessage()));
    }
}
