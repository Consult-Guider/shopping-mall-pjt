package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.QSeller;
import com.project.shoppingmall.domain.Seller;
import com.project.shoppingmall.repository.LoginRepository;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class SellerRepositoryImpl implements LoginRepository<Seller> {
    private final JPQLQueryFactory queryFactory;

    @Override
    public Optional<Seller> findByEmail(String username) {
        QSeller seller = QSeller.seller;
        return Optional.ofNullable(queryFactory
                .select(seller).from(seller)
                .where(seller.email.eq(username))
                .fetchOne());
    }
}
