package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.Review;
import com.project.shoppingmall.repository.CustomReviewRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements CustomReviewRepository {
    private final ElasticsearchOperations elasticsearchClient;

    private MatchQueryBuilder queryForFindWithItemId(String iid) {
        return matchQuery("itemId", iid);
    }
    private MatchQueryBuilder queryForFindWithUserId(Long iid) {
        return matchQuery("userId", iid);
    }

    @Override
    public Page<Review> findReviewByItemId(String iid, Pageable pageable) {
        QueryBuilder query = queryForFindWithItemId(iid);

        SearchHits<Review> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withPageable(pageable)
                        .build(),
                Review.class
        );

        List<Review> items = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(items, pageable, result.getTotalHits());
    }

    @Override
    public Page<Review> findReviewByUserId(Long uid, Pageable pageable) {
        QueryBuilder query = queryForFindWithUserId(uid);

        SearchHits<Review> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withPageable(pageable)
                        .build(),
                Review.class
        );

        List<Review> items = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(items, pageable, result.getTotalHits());
    }

    @Override
    public Page<Review> searchReviewByKeyword(String keyword, Pageable pageable) {
        QueryBuilder query = boolQuery().should(QueryBuilders.matchQuery("content", keyword));

        SearchHits<Review> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withPageable(pageable)
                        .build(),
                Review.class
        );

        List<Review> items = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(items, pageable, result.getTotalHits());
    }

    @Override
    public Double getAverageByItemId(String iid) {
        String AggName = "AVG";
        AvgAggregationBuilder aggregation = AggregationBuilders.avg(AggName).field("rating");

        SearchHits<Review> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(queryForFindWithItemId(iid))
                        .withAggregations(aggregation)
                        .build(),
                Review.class
        );

        return Optional.of(result)
                .map(SearchHits::getAggregations)
                .map(ElasticsearchAggregations.class::cast)
                .map(ElasticsearchAggregations::aggregations)
                .map(aggregations -> aggregations.get(AggName))
                .map(Avg.class::cast)
                .map(Avg::getValue)
                .orElse(0d);
    }
}
