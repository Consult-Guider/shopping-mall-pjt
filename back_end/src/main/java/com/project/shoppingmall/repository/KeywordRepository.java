package com.project.shoppingmall.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface KeywordRepository<T> {
    Page<T> findByKeyword(String keyword, Pageable pageable);
}
