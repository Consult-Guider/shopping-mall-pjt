package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.nested.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KeywordRepository<T> {
    Page<T> findByKeyword(String keyword, Pageable pageable);
    List<Tag> findDistinctTag();
    Page<Item> findByTagName(String tagName, Pageable pageable);
}
