package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.nested.Tag;
import com.project.shoppingmall.repository.KeywordRepository;
import com.project.shoppingmall.utils.ElasticSearchOperationsUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Collection;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

public class ItemRepositoryImpl implements KeywordRepository<Item> {
    private final ElasticSearchOperationsUtil<Item> fetchUtil;

    @Autowired
    public ItemRepositoryImpl(ElasticsearchOperations elasticsearchClient) {
        this.fetchUtil = new ElasticSearchOperationsUtil<>(Item.class, elasticsearchClient);
    }

    @Override
    public Page<Item> findByKeyword(String keyword, Pageable pageable) {
        QueryBuilder query = boolQuery()
                .should(matchQuery("name", keyword));

        return fetchUtil.fetchWithPageable(query, pageable);
    }

    @Override
    public List<Tag> findDistinctTag() {
        String AGG_NAME = "distinct_tags";
        TermsAggregationBuilder query = terms(AGG_NAME).field("tagList.name");

        return fetchUtil.fetchAggregation(matchAllQuery(), ParsedStringTerms.class, AGG_NAME, query)
                .map(ParsedStringTerms::getBuckets)
                .stream().flatMap(Collection::stream)
                .map(MultiBucketsAggregation.Bucket::getKeyAsString)
                .map(s -> Tag.builder().name(s).build())
                .toList();
    }

    @Override
    public Page<Item> findByTagName(String tagName, Pageable pageable) {
        QueryBuilder query = matchQuery("tagList.name", tagName);

        return fetchUtil.fetchWithPageable(query, pageable);
    }
}
