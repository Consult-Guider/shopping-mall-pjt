package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.HandledItem;
import com.project.shoppingmall.repository.CustomHandledItemRepository;
import com.project.shoppingmall.type.ProcessType;
import com.project.shoppingmall.utils.ElasticSearchOperationsUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

public class HandledItemRepositoryImpl implements CustomHandledItemRepository {
    private final ElasticSearchOperationsUtil<HandledItem> fetchUtil;

    @Autowired
    public HandledItemRepositoryImpl(ElasticsearchOperations elasticsearchClient) {
        this.fetchUtil = new ElasticSearchOperationsUtil<>(HandledItem.class, elasticsearchClient);
    }

    @Override
    public Page<HandledItem> findXxxAs(
            ProcessType processType,
            Pageable pageable
    ) {
        QueryBuilder query = boolQuery()
                .must(matchQuery("ProcessType", processType));
        return fetchUtil.fetchWithPageable(query, pageable);
    }

    @Override
    public Map<String, Long> countPaymentByProcessType() {
        QueryBuilder query = matchAllQuery();
        String AGG_NAME = "countByProcessType";
        TermsAggregationBuilder aggregation = terms(AGG_NAME).field("ProcessType");

        return fetchUtil.fetchAggregation(query, ParsedStringTerms.class, AGG_NAME, aggregation)
                .map(ParsedStringTerms::getBuckets)
                .stream().flatMap(Collection::stream)
                .collect(Collectors.toMap(Terms.Bucket::getKeyAsString, Terms.Bucket::getDocCount));
    }
}
