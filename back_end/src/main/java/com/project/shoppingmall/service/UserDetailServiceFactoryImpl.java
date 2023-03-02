package com.project.shoppingmall.service;

import com.project.shoppingmall.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceFactoryImpl extends UserDetailServiceFactory {
    private final UserService userService;
    private final SellerService sellerService;

    public UserDetailsService newInstance(RoleType role) {
        return switch (role) {
            case USER -> userService;
            case SELLER -> sellerService;
        };
    }
}
