package com.project.shoppingmall.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor @Getter @Builder
public class LoginResponse {
    private final String token;
}
