package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.User;
import com.project.shoppingmall.model.UserDto;
import com.project.shoppingmall.model.request.UserCreateRequest;
import com.project.shoppingmall.model.request.UserUpdateRequest;
import com.project.shoppingmall.model.response.UserReadResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends
        UserDetailsService,
        CrudService<Long, UserCreateRequest, UserReadResponse, UserUpdateRequest>,
        CrudPrincipalService<UserDto, UserUpdateRequest> {
    void isThereEmailEqualTo(String email);
    User loadUserById(Long uid);
}
