package com.project.shoppingmall.controller;

import com.project.shoppingmall.model.request.LoginRequest;
import com.project.shoppingmall.model.response.LoginResponse;
import com.project.shoppingmall.model.response.Response;
import com.project.shoppingmall.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping
    public Response<LoginResponse> doLogin(@RequestBody @Valid LoginRequest request) {
        LoginResponse token = authService.doLogin(request);
        return Response.success(token);
    }
}
