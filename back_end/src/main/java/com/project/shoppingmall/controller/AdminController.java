package com.project.shoppingmall.controller;

import com.project.shoppingmall.model.AdminDto;
import com.project.shoppingmall.model.request.AdminCreateRequest;
import com.project.shoppingmall.model.request.AdminUpdateRequest;
import com.project.shoppingmall.model.response.AdminReadResponse;
import com.project.shoppingmall.model.response.Response;
import com.project.shoppingmall.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping
    public Response<Void> createUser(@RequestBody @Valid AdminCreateRequest request) {
        adminService.create(request);
        return Response.success();
    }

    @GetMapping("/{uid}")
    public Response<AdminReadResponse> readUser(@PathVariable Long uid) {
        AdminReadResponse dto = adminService.read(uid);
        return Response.success(dto);
    }

    @PutMapping("/{uid}")
    public Response<Void> updateUser(
            @PathVariable Long uid,
            @RequestBody @Valid AdminUpdateRequest request
    ) {
        adminService.update(uid, request);
        return Response.success();
    }

    @DeleteMapping("/{uid}")
    public Response<Void> deleteUser(@PathVariable Long uid) {
        adminService.delete(uid);
        return Response.success();
    }

    @GetMapping("/principal")
    public Response<AdminReadResponse> readPrincipal(@AuthenticationPrincipal @Valid AdminDto principal) {
        return Response.success(AdminReadResponse.fromDto(principal));
    }

    @PutMapping("/principal")
    public Response<Void> updatePrincipal(
            @AuthenticationPrincipal @Valid AdminDto principal,
            @RequestBody @Valid AdminUpdateRequest request
    ) {
        adminService.updatePrincipal(principal, request);
        return Response.success();
    }

    @DeleteMapping("/principal")
    public Response<Void> deletePrincipal(@AuthenticationPrincipal @Valid AdminDto principal) {
        adminService.deletePrincipal(principal);
        return Response.success();
    }
}
