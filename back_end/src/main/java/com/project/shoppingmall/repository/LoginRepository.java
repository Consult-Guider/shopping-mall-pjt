package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.LoginEntity;

import java.util.Optional;

public interface LoginRepository<T extends LoginEntity> {
    Optional<T> findByEmail(String username);
}
