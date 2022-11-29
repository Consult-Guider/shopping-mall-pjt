package com.project.shoppingmall.service;

import com.project.shoppingmall.type.RoleType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceFactoryImpl extends UserDetailServiceFactory {
    public UserDetailsService getService(RoleType role) {
        return newInstance(role);
    }
}
