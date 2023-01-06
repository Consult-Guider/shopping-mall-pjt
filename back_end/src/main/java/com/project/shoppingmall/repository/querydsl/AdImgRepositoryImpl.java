package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.AdImg;
import com.project.shoppingmall.domain.QAdImg;
import com.project.shoppingmall.repository.ExceptOverdueAdImgRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AdImgRepositoryImpl implements ExceptOverdueAdImgRepository {
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
}
