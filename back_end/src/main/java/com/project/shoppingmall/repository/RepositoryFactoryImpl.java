package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.LoginEntity;
import com.project.shoppingmall.type.RoleType;

import java.util.Optional;

public class RepositoryFactoryImpl extends RepositoryFactory {
    public Optional<? extends LoginEntity> getFinderByEmail(RoleType role, String email) {
        return newInstance(role).findByEmail(email);
    }
}
