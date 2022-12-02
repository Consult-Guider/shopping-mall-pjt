package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.Admin;
import com.project.shoppingmall.model.AdminDto;
import com.project.shoppingmall.model.request.AdminCreateRequest;
import com.project.shoppingmall.model.request.AdminUpdateRequest;
import com.project.shoppingmall.model.response.AdminReadResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AdminService extends
        UserDetailsService,
        CrudService<AdminCreateRequest, AdminReadResponse, AdminUpdateRequest>,
        CrudPrincipalService<AdminDto, AdminUpdateRequest> {
    void isThereEmailEqualTo(String email);
    Admin loadUserById(Long uid);
}
