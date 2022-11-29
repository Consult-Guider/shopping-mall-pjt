package com.project.shoppingmall.service;

import com.project.shoppingmall.model.request.LoginRequest;
import com.project.shoppingmall.model.response.LoginResponse;

public interface AuthService {
    LoginResponse doLogin(LoginRequest request);
}
