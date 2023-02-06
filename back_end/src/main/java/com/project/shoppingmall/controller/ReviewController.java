package com.project.shoppingmall.controller;

import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.ReviewCreateRequest;
import com.project.shoppingmall.model.request.ReviewUpdateRequest;
import com.project.shoppingmall.model.response.Response;
import com.project.shoppingmall.model.response.ReviewReadResponse;
import com.project.shoppingmall.model.response.ReviewSearchResponse;
import com.project.shoppingmall.model.response.ReviewStatisticsResponse;
import com.project.shoppingmall.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/item/{iid}/review")
    public Response<Void> create(
            @PathVariable String iid,
            @RequestBody ReviewCreateRequest request,
            @AuthenticationPrincipal LoginDto loginDto
    ) {
        reviewService.create(iid, request, loginDto);
        return Response.success();
    }

    @GetMapping("/item/{iid}/review")
    public Response<Page<ReviewReadResponse>> readByItemId(
            @PathVariable String iid,
            @PageableDefault Pageable pageable
            ) {
        Page<ReviewReadResponse> pages = reviewService.readByIid(iid, pageable);
        return Response.success(pages);
    }

    @GetMapping("/user/principal/review")
    public Response<Page<ReviewReadResponse>> readByUserId(
            @AuthenticationPrincipal LoginDto loginDto,
            @PageableDefault Pageable pageable
    ) {
        Page<ReviewReadResponse> pages = reviewService.readByUid(loginDto, pageable);
        return Response.success(pages);
    }

    @GetMapping("/review")
    public Response<Page<ReviewSearchResponse>> search(
            @RequestParam String keyword,
            @PageableDefault Pageable pageable
    ) {
        Page<ReviewSearchResponse> pages = reviewService.searchByKeyword(keyword, pageable);
        return Response.success(pages);
    }

    @GetMapping("/item/{iid}/review/statistics")
    public Response<ReviewStatisticsResponse> search(@PathVariable String iid) {
        ReviewStatisticsResponse dto = reviewService.calculate(iid);
        return Response.success(dto);
    }

    @PutMapping("/review/{rid}")
    public Response<Void> update(
            @AuthenticationPrincipal LoginDto loginDto,
            @PathVariable String rid,
            @RequestBody ReviewUpdateRequest request
            ) {
        reviewService.update(rid, request, loginDto);
        return Response.success();
    }

    @DeleteMapping("/review/{rid}")
    public Response<Void> delete(
            @AuthenticationPrincipal LoginDto loginDto,
            @PathVariable String rid
    ) {
        reviewService.delete(rid, loginDto);
        return Response.success();
    }
}
