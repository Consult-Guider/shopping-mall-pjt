package com.project.shoppingmall.service;

import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.ReviewCreateRequest;
import com.project.shoppingmall.model.request.ReviewUpdateRequest;
import com.project.shoppingmall.model.response.ReviewReadResponse;
import com.project.shoppingmall.model.response.ReviewSearchResponse;
import com.project.shoppingmall.model.response.ReviewStatisticsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    void create(String iid, ReviewCreateRequest request, LoginDto loginDto);

    Page<ReviewReadResponse> readByIid(String iid, Pageable pageable);

    Page<ReviewReadResponse> readByUid(Long id, Pageable pageable);

    Page<ReviewSearchResponse> searchByKeyword(String keyword, Pageable pageable);

    void update(String rid, ReviewUpdateRequest request, LoginDto loginDto);

    void delete(String rid, LoginDto loginDto);

    ReviewStatisticsResponse calculate(String iid);
}
