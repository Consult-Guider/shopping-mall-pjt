package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomReviewRepository {
    Page<Review> findReviewByItemId(String iid, Pageable pageable);

    Page<Review> searchReviewByKeyword(String keyword, Pageable pageable);

    Double getAverageByItemId(String iid);

    Page<Review> findReviewByUserId(Long uid, Pageable pageable);
}
