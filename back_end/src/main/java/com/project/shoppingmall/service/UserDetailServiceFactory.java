package com.project.shoppingmall.service;

import com.project.shoppingmall.type.RoleType;
import org.springframework.security.core.userdetails.UserDetailsService;

public abstract class UserDetailServiceFactory {
    public abstract UserDetailsService newInstance(RoleType role);

    public UserDetailsService getService(RoleType role) {
        return newInstance(role);
    }
}
