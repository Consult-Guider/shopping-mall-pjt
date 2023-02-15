package com.project.shoppingmall.service;

import com.project.shoppingmall.model.request.ItemCreateRequest;
import com.project.shoppingmall.model.request.ItemUpdateRequest;
import com.project.shoppingmall.model.response.ItemReadResponse;

public interface ItemService extends
        CrudServiceVerifyingAuth<String, ItemCreateRequest, ItemReadResponse, ItemUpdateRequest>,
        KeywordService {
    ItemReadResponse readItemAll(String id);
}
