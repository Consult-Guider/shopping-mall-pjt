package com.project.shoppingmall.controller;

import com.project.shoppingmall.model.UserDto;
import com.project.shoppingmall.model.request.UserCreateRequest;
import com.project.shoppingmall.model.request.UserUpdateRequest;
import com.project.shoppingmall.model.response.Response;
import com.project.shoppingmall.model.response.UserReadResponse;
import com.project.shoppingmall.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public Response<Void> createUser(@RequestBody @Valid UserCreateRequest request) {
        userService.createUser(request);
        return Response.success();
    }

    @GetMapping("/{uid}")
    public Response<UserReadResponse> readUser(@PathVariable Long uid) {
        UserReadResponse dto = userService.readUser(uid);
        return Response.success(dto);
    }

    @PutMapping("/{uid}")
    public Response<Void> updateUser(
            @PathVariable Long uid,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        userService.updateUser(uid, request);
        return Response.success();
    }

    @DeleteMapping("/{uid}")
    public Response<Void> deleteUser(@PathVariable Long uid) {
        userService.deleteUser(uid);
        return Response.success();
    }

    @GetMapping("/principal")
    public Response<UserReadResponse> readPrincipal(@AuthenticationPrincipal @Valid UserDto principal) {
        return Response.success(UserReadResponse.fromDto(principal));
    }

    @PutMapping("/principal")
    public Response<Void> updatePrincipal(
            @AuthenticationPrincipal @Valid UserDto principal,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        userService.updatePrincipal(principal, request);
        return Response.success();
    }

    @DeleteMapping("/principal")
    public Response<Void> deletePrincipal(@AuthenticationPrincipal @Valid UserDto principal) {
        userService.deletePrincipal(principal);
        return Response.success();
    }
}
