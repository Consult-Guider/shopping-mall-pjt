package com.project.shoppingmall.repository;

import com.project.shoppingmall.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryFactoryImpl extends RepositoryFactory {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final AdminRepository adminRepository;
    public LoginRepository<?> newInstance(RoleType role) {
        return switch (role) {
            case USER -> userRepository;
            case SELLER -> sellerRepository;
            case ADMIN -> adminRepository;
        };
    }
}
