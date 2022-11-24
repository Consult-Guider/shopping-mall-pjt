package com.project.shoppingmall.service;

import com.project.shoppingmall.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailServiceFactory {
    private final UserService userService;
    private final SellerService sellerService;
    private final AdminService adminService;

    public UserDetailsService getService(RoleType role) {
        return switch (role) {
            case USER -> userService;
            case SELLER -> sellerService;
            case ADMIN -> adminService;
        };
    }
}
