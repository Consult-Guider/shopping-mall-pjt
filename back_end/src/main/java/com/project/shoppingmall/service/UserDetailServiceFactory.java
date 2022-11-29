package com.project.shoppingmall.service;

import com.project.shoppingmall.type.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

public abstract class UserDetailServiceFactory {
    @Autowired protected UserService userService;
    @Autowired protected SellerService sellerService;
    @Autowired protected AdminService adminService;

    public UserDetailsService newInstance(RoleType role) {
        return switch (role) {
            case USER -> userService;
            case SELLER -> sellerService;
            case ADMIN -> adminService;
        };
    }

    public abstract UserDetailsService getService(RoleType role);
}
