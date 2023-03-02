package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.LoginEntity;
import com.project.shoppingmall.type.RoleType;

import java.util.Optional;

public abstract class RepositoryFactory {
    public abstract LoginRepository<?> newInstance(RoleType role);

    public Optional<? extends LoginEntity> getFinderByEmail(RoleType role, String email) {
        return newInstance(role).findByEmail(email);
    }
}
