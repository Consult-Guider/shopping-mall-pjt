package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.LoginEntity;
import com.project.shoppingmall.type.RoleType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class RepositoryFactory {
    @Autowired protected UserRepository userRepository;
    @Autowired protected SellerRepository sellerRepository;
    @Autowired protected AdminRepository adminRepository;

    public LoginRepository<?> newInstance(RoleType role) {
        return switch (role) {
            case USER -> userRepository;
            case SELLER -> sellerRepository;
            case ADMIN -> adminRepository;
        };
    }

    public abstract Optional<? extends LoginEntity> getFinderByEmail(RoleType role, String email);
}
