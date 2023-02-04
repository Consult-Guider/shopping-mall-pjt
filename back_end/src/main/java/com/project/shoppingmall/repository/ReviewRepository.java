package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Review;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ReviewRepository extends ElasticsearchRepository<Review, String>, CustomReviewRepository {
}
