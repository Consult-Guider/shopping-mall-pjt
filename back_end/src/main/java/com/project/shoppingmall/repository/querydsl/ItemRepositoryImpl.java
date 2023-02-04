package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.nested.Tag;
import com.project.shoppingmall.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements KeywordRepository<Item> {
    private final ElasticsearchOperations elasticsearchClient;

    @Override
    public Page<Item> findByKeyword(String keyword, Pageable pageable) {
        QueryBuilder query = boolQuery()
                .should(matchQuery("name", keyword));

        SearchHits<Item> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withPageable(pageable)
                        .build(),
                Item.class
        );

        List<Item> items = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(items, pageable, result.getTotalHits());
    }

    @Override
    public List<Tag> findDistinctTag() {
        String AGG_NAME = "distinct_tags";
        TermsAggregationBuilder query = terms(AGG_NAME).field("tagList.name");

        SearchHits<Item> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(matchAllQuery())
                        .withAggregations(query)
                        .build(),
                Item.class
        );

        return Optional.of(result)
                .map(SearchHits::getAggregations)
                .map(ElasticsearchAggregations.class::cast)
                .map(ElasticsearchAggregations::aggregations)
                .map(agg -> agg.get(AGG_NAME))
                .map(ParsedStringTerms.class::cast)
                .map(ParsedStringTerms::getBuckets)
                .stream().flatMap(Collection::stream)
                .map(MultiBucketsAggregation.Bucket::getKeyAsString)
                .map(s -> Tag.builder().name(s).build())
                .toList();
    }

    @Override
    public Page<Item> findByTagName(String tagName, Pageable pageable) {
        QueryBuilder query = matchQuery("tagList.name", tagName);

        SearchHits<Item> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withPageable(pageable)
                        .build(),
                Item.class
        );

        List<Item> items = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(items, pageable, result.getTotalHits());
    }
}
