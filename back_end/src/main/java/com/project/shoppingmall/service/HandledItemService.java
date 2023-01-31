package com.project.shoppingmall.service;

import com.project.shoppingmall.model.request.HandledItemCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HandledItemService extends CrudServiceVerifyingAuth<String, HandledItemCreateRequest, HandledItemReadResponse, HandledItemUpdateRequest> {
    HandledItemReadResponse readItemAll(String id);
    Page<HandledItemReadResponse> searchItem(String keyword, Pageable pageable);
}
