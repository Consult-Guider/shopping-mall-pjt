package com.project.shoppingmall.utils;

import lombok.AllArgsConstructor;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilder;
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

@AllArgsConstructor
public class ElasticSearchOperationsUtil<T> {
    private final Class<T> clazz;
    private final ElasticsearchOperations elasticsearchClient;

    public Page<T> fetchWithPageable(QueryBuilder query, Pageable pageable) {
        SearchHits<T> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withPageable(pageable)
                        .build(),
                this.clazz
        );

        List<T> items = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(items, pageable, result.getTotalHits());
    }

    public Page<T> fetchWithSorting(QueryBuilder query, SortBuilder<?> sortBuilder) {
        SearchHits<T> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withSorts(sortBuilder)
                        .build(),
                this.clazz
        );

        List<T> items = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(items, Pageable.unpaged(), result.getTotalHits());
    }

    public List<T> fetch(QueryBuilder query) {
        SearchHits<T> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .build(),
                this.clazz
        );

        return result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
    }

    public Optional<T> fetchOne(QueryBuilder query) {
        SearchHits<T> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .build(),
                this.clazz
        );

        return Optional.of(result)
                .filter(SearchHits::hasSearchHits)
                .map(x -> x.getSearchHit(0))
                .map(SearchHit::getContent);
    }

    public <U> Optional<U> fetchAggregation(
            QueryBuilder query,
            Class<U> castTo,
            String AggName,
            AbstractAggregationBuilder<?>... aggregationBuilder
    ) {
        SearchHits<T> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withAggregations(aggregationBuilder)
                        .build(),
                this.clazz
        );

        return Optional.of(result)
                .map(SearchHits::getAggregations)
                .map(ElasticsearchAggregations.class::cast)
                .map(ElasticsearchAggregations::aggregations)
                .map(aggregations -> aggregations.get(AggName))
                .map(castTo::cast);
    }
}
