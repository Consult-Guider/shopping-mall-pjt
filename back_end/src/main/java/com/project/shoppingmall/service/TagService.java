package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.nested.Tag;
import com.project.shoppingmall.model.response.ItemReadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    Page<Tag> getRandomTags(Pageable pageable);
    Page<ItemReadResponse> getItems(String tagName, Pageable pageable);
}
