package com.project.shoppingmall.controller;

import com.project.shoppingmall.model.SellerDto;
import com.project.shoppingmall.model.request.SellerCreateRequest;
import com.project.shoppingmall.model.request.SellerUpdateRequest;
import com.project.shoppingmall.model.response.Response;
import com.project.shoppingmall.model.response.SellerReadResponse;
import com.project.shoppingmall.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/seller")
@RestController
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;

    @PostMapping
    public Response<Void> createUser(@RequestBody @Valid SellerCreateRequest request) {
        sellerService.createUser(request);
        return Response.success();
    }

    @GetMapping("/{uid}")
    public Response<SellerReadResponse> readUser(@PathVariable Long uid) {
        SellerReadResponse dto = sellerService.readUser(uid);
        return Response.success(dto);
    }

    @PutMapping("/{uid}")
    public Response<Void> updateUser(
            @PathVariable Long uid,
            @RequestBody @Valid SellerUpdateRequest request
    ) {
        sellerService.updateUser(uid, request);
        return Response.success();
    }

    @DeleteMapping("/{uid}")
    public Response<Void> deleteUser(@PathVariable Long uid) {
        sellerService.deleteUser(uid);
        return Response.success();
    }

    @GetMapping("/principal")
    public Response<SellerReadResponse> readPrincipal(@AuthenticationPrincipal @Valid SellerDto principal) {
        return Response.success(SellerReadResponse.fromDto(principal));
    }

    @PutMapping("/principal")
    public Response<Void> updatePrincipal(
            @AuthenticationPrincipal @Valid SellerDto principal,
            @RequestBody @Valid SellerUpdateRequest request
    ) {
        sellerService.updatePrincipal(principal, request);
        return Response.success();
    }

    @DeleteMapping("/principal")
    public Response<Void> deletePrincipal(@AuthenticationPrincipal @Valid SellerDto principal) {
        sellerService.deletePrincipal(principal);
        return Response.success();
    }
}
