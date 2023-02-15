package com.project.shoppingmall.service;

import com.project.shoppingmall.model.response.SearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface KeywordService {
    Page<SearchResponse> searchByKeyword(String keyword, Pageable pageable);
}
