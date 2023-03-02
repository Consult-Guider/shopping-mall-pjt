package com.project.shoppingmall.repository.querydsl;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import com.project.shoppingmall.domain.Review;
import com.project.shoppingmall.repository.CustomReviewRepository;
import com.project.shoppingmall.utils.ElasticSearchOperationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

public class ReviewRepositoryImpl implements CustomReviewRepository {
    private final ElasticSearchOperationsUtil<Review> fetchUtil;

    @Autowired
    public ReviewRepositoryImpl(ElasticsearchOperations elasticsearchClient) {
        this.fetchUtil = new ElasticSearchOperationsUtil<>(Review.class, elasticsearchClient);
    }

    @Override
    public Page<Review> findReviewByItemId(String iid, Pageable pageable) {
        return fetchUtil.fetchWithPageable(
                b -> b.withQuery(q -> q.match(bool -> bool.field("item.id").query(iid))),
                pageable);
    }

    @Override
    public Page<Review> findReviewByUserId(Long uid, Pageable pageable) {
        return fetchUtil.fetchWithPageable(
                b -> b.withQuery(q -> q.match(bool -> bool.field("user.id").query(uid))),
                pageable);
    }

    @Override
    public Page<Review> findReviewBySellerId(Long uid, Pageable pageable) {
        return fetchUtil.fetchWithPageable(
                b -> b.withQuery(q -> q.match(bool -> bool.field("item.seller").query(uid))),
                pageable);
    }

    @Override
    public Page<Review> searchReviewByKeyword(String keyword, Pageable pageable) {
        return fetchUtil.fetchWithPageable(
                b -> b.withQuery(q -> q.bool(
                        bool -> bool.should(
                                s -> s.match(m -> m.field("content").query(keyword)))
                        )
                ),
                pageable);
    }

    @Override
    public Double getAverageByItemId(String iid) {
        String AggName = "AVG";
        Aggregation aggregation = Aggregation.of(
                builder -> builder.avg(t -> t.field("rating"))
        );
        return fetchUtil.fetchAvg(
                        builder -> builder
                                .withQuery(q -> q.match(bool -> bool.field("item.id").query(iid)))
                                .withAggregation(AggName, aggregation)
                )
                .filter(d -> !Double.isNaN(d))
                .filter(d -> !Double.isInfinite(d))
                .orElse(0d);
    }
}
