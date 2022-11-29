package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.Admin;
import com.project.shoppingmall.domain.QAdmin;
import com.project.shoppingmall.repository.LoginRepository;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AdminRepositoryImpl implements LoginRepository<Admin> {
    private final JPQLQueryFactory queryFactory;

    @Override
    public Optional<Admin> findByEmail(String username) {
        QAdmin admin = QAdmin.admin;
        return Optional.ofNullable(queryFactory
                .select(admin).from(admin)
                .where(admin.email.eq(username))
                .fetchOne());
    }
}
