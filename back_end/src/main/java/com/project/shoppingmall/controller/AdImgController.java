package com.project.shoppingmall.controller;

import com.project.shoppingmall.model.request.AdImgCreateRequest;
import com.project.shoppingmall.model.request.AdImgUpdateRequest;
import com.project.shoppingmall.model.response.AdImgReadResponse;
import com.project.shoppingmall.model.response.Response;
import com.project.shoppingmall.service.AdImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/adimg")
@RestController
@RequiredArgsConstructor
public class AdImgController {
    private final AdImgService adImgService;

    @PostMapping
    public Response<Void> createUser( @ModelAttribute @Valid AdImgCreateRequest request) {
        adImgService.create(request);
        return Response.success();
    }

    @GetMapping
    public Response<Page<AdImgReadResponse>> readUser(@PageableDefault Pageable pageable) {
        Page<AdImgReadResponse> pages = adImgService.read(pageable);
        return Response.success(pages);
    }

    @GetMapping("/{aid}")
    public Response<AdImgReadResponse> readUser(@PathVariable Long aid) {
        AdImgReadResponse dto = adImgService.read(aid);
        return Response.success(dto);
    }

    @PutMapping("/{aid}")
    public Response<Void> updateUser(
            @PathVariable Long aid,
            @ModelAttribute @Valid AdImgUpdateRequest request
    ) {
        adImgService.update(aid, request);
        return Response.success();
    }

    @DeleteMapping("/{aid}")
    public Response<Void> deleteUser(@PathVariable Long aid) {
        adImgService.delete(aid);
        return Response.success();
    }
}
