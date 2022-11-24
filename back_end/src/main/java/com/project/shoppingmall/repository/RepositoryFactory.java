package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.LoginEntity;
import com.project.shoppingmall.type.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RepositoryFactory {
    @Autowired private UserRepository userRepository;
    @Autowired private SellerRepository sellerRepository;
    @Autowired private AdminRepository adminRepository;

    public Optional<? extends LoginEntity> getFinderByEmail(RoleType role, String email) {
        return switch (role) {
            case USER -> userRepository.findByEmail(email);
            case SELLER -> sellerRepository.findByEmail(email);
            case ADMIN -> adminRepository.findByEmail(email);
        };
    }
}
