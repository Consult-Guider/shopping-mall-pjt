package com.project.shoppingmall.service;

import com.project.shoppingmall.model.request.AdImgCreateRequest;
import com.project.shoppingmall.model.request.AdImgUpdateRequest;
import com.project.shoppingmall.model.response.AdImgReadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdImgService extends CrudService<Long, AdImgCreateRequest, AdImgReadResponse, AdImgUpdateRequest> {
    Page<AdImgReadResponse> read(Pageable pageable);
}
