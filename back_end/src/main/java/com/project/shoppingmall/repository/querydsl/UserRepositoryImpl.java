package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.QUser;
import com.project.shoppingmall.domain.User;
import com.project.shoppingmall.repository.LoginRepository;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryImpl implements LoginRepository<User> {
    private final JPQLQueryFactory queryFactory;

    @Override
    public Optional<User> findByEmail(String username) {
        QUser user = QUser.user;
        return Optional.ofNullable(queryFactory
                .select(user).from(user)
                .where(user.email.eq(username))
                .fetchOne());
    }
}
