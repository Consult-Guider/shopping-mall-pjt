package com.project.shoppingmall.utils;

import co.elastic.clients.elasticsearch._types.aggregations.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.Aggregation;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class ElasticSearchOperationsUtil<T> {
    private final Class<T> clazz;
    private final ElasticsearchOperations elasticsearchClient;

    public Page<T> fetchWithPageable(Function<NativeQueryBuilder, NativeQueryBuilder> queryFunction, Pageable pageable) {
        SearchHits<T> result = elasticsearchClient.search(
                queryFunction.apply(NativeQuery.builder())
                        .withPageable(pageable)
                        .build(),
                this.clazz
        );

        List<T> items = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(items, pageable, result.getTotalHits());
    }

    public Optional<T> fetchOne(Function<NativeQueryBuilder, NativeQueryBuilder> queryFunction) {
        SearchHits<T> result = elasticsearchClient.search(
                queryFunction.apply(NativeQuery.builder()).build(),
                this.clazz
        );

        return Optional.of(result)
                .filter(SearchHits::hasSearchHits)
                .map(x -> x.getSearchHit(0))
                .map(SearchHit::getContent);
    }

    public Stream<Aggregate> fetchAggregation(
            Function<NativeQueryBuilder, NativeQueryBuilder> queryFunction
    ) {
        SearchHits<T> result = elasticsearchClient.search(
                queryFunction.apply(NativeQuery.builder()).build(),
                this.clazz
        );

        List<ElasticsearchAggregation> aggregations = (List) result.getAggregations().aggregations();
        return aggregations.stream()
                .map(ElasticsearchAggregation::aggregation)
                .map(Aggregation::getAggregate);
    }

    public Map<String, Long> fetchCountsDict(
            Function<NativeQueryBuilder, NativeQueryBuilder> queryFunction
    ) {
        return fetchAggregation(queryFunction)
                .map(Aggregate::sterms)
                .map(StringTermsAggregate::buckets)
                .map(Buckets::array)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(StringTermsBucket::key, StringTermsBucket::docCount));
    }

    public Optional<Double> fetchAvg(
            Function<NativeQueryBuilder, NativeQueryBuilder> queryFunction
    ) {
        return fetchAggregation(queryFunction)
                .map(Aggregate::avg)
                .map(AvgAggregate::value)
                .findFirst();
    }
}
