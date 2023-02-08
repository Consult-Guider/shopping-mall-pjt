package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.Review;
import com.project.shoppingmall.repository.CustomReviewRepository;
import com.project.shoppingmall.utils.ElasticSearchOperationsUtil;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

public class ReviewRepositoryImpl implements CustomReviewRepository {
    private final ElasticSearchOperationsUtil<Review> fetchUtil;

    @Autowired
    public ReviewRepositoryImpl(ElasticsearchOperations elasticsearchClient) {
        this.fetchUtil = new ElasticSearchOperationsUtil<>(Review.class, elasticsearchClient);
    }

    private MatchQueryBuilder queryForFindWithItemId(String iid) {
        return matchQuery("item.id", iid);
    }
    private MatchQueryBuilder queryForFindWithUserId(Long uid) {
        return matchQuery("user.id", uid);
    }
    private MatchQueryBuilder queryForFindWithSellerId(Long uid) {
        return matchQuery("item.seller", uid);
    }

    @Override
    public Page<Review> findReviewByItemId(String iid, Pageable pageable) {
        QueryBuilder query = queryForFindWithItemId(iid);

        return fetchUtil.fetchWithPageable(query, pageable);
    }

    @Override
    public Page<Review> findReviewByUserId(Long uid, Pageable pageable) {
        QueryBuilder query = queryForFindWithUserId(uid);

        return fetchUtil.fetchWithPageable(query, pageable);
    }

    @Override
    public Page<Review> findReviewBySellerId(Long uid, Pageable pageable) {
        QueryBuilder query = queryForFindWithSellerId(uid);

        return fetchUtil.fetchWithPageable(query, pageable);
    }

    @Override
    public Page<Review> searchReviewByKeyword(String keyword, Pageable pageable) {
        QueryBuilder query = boolQuery().should(QueryBuilders.matchQuery("content", keyword));

        return fetchUtil.fetchWithPageable(query, pageable);
    }

    @Override
    public Double getAverageByItemId(String iid) {
        String AggName = "AVG";
        AvgAggregationBuilder aggregation = AggregationBuilders.avg(AggName).field("rating");

        return fetchUtil.fetchAggregation(queryForFindWithItemId(iid), Avg.class, AggName, aggregation)
                .map(Avg::getValue)
                .filter(d -> !Double.isNaN(d))
                .filter(d -> !Double.isInfinite(d))
                .orElse(0d);
    }
}
