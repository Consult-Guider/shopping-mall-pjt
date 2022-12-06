package com.project.shoppingmall.service;

import com.project.shoppingmall.model.response.ItemReadResponse;
import com.project.shoppingmall.model.request.ItemCreateRequest;
import com.project.shoppingmall.model.request.ItemUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService extends CrudServiceVerifyingAuth<String, ItemCreateRequest, ItemReadResponse, ItemUpdateRequest> {
    ItemReadResponse readItemAll(String id);
    Page<ItemReadResponse> searchItem(String keyword, Pageable pageable);
}
