package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.AdImg;
import com.project.shoppingmall.domain.QAdImg;
import com.project.shoppingmall.repository.CustomAdImgRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class AdImgRepositoryImpl implements CustomAdImgRepository {
    private final JPQLQueryFactory queryFactory;

    @Override
    public Page<AdImg> findAllValid(Pageable pageable) {
        QAdImg entity = QAdImg.adImg;
        QueryResults<AdImg> results = queryFactory
                .select(entity).from(entity)
                .where(entity.endAt.after(LocalDateTime.now()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Optional<AdImg> findOneRandomly() {
        QAdImg entity = QAdImg.adImg;
        JPQLQuery<AdImg> query = queryFactory
                .select(entity).from(entity)
                .where(
                        entity.startAt.before(LocalDateTime.now()),
                        entity.endAt.after(LocalDateTime.now())
                );
        long offset = makeRandom(query.fetchCount());

        return Optional.ofNullable(query.offset(offset).limit(1).fetchOne());
    }

    private long makeRandom(long total) {
        return (long) Math.floor(Math.random() * total);
    }
}
