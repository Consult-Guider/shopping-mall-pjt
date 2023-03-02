package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.Seller;
import com.project.shoppingmall.model.SellerDto;
import com.project.shoppingmall.model.request.SellerCreateRequest;
import com.project.shoppingmall.model.request.SellerUpdateRequest;
import com.project.shoppingmall.model.response.SellerReadResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SellerService extends
        UserDetailsService,
        CrudService<Long, SellerCreateRequest, SellerReadResponse, SellerUpdateRequest>,
        CrudPrincipalService<SellerDto, SellerUpdateRequest> {
    void isThereEmailEqualTo(String email);
    Seller loadUserById(Long uid);
}
