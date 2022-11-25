package com.project.shoppingmall.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor @Getter
public enum ErrorCode {
    IS_NOT_BEARER_TOKEN(HttpStatus.UNAUTHORIZED, "Bearer 토큰이 아닙니다."),
    NO_AUTHENTICATION_IN_HEADER(HttpStatus.UNAUTHORIZED, "헤더의 Authentication key가 존재하지 않습니다."),
    MALFORMED_JWT(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "기간 만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
    ILLEGALARGUMENT_JWT(HttpStatus.UNAUTHORIZED, "JWT 토큰이 잘못되었습니다."),
    ACCOUNT_NOT_FOUNDED(HttpStatus.NOT_FOUND, "해당 계정은 찾을 수가 없습니다."),
    INVALID_ROLETYPE(HttpStatus.BAD_REQUEST, "해당 RoleType은 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
