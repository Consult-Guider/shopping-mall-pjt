package com.project.shoppingmall.repository.querydsl;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.project.shoppingmall.domain.HandledItem;
import com.project.shoppingmall.repository.CustomHandledItemRepository;
import com.project.shoppingmall.type.ProcessType;
import com.project.shoppingmall.utils.ElasticSearchOperationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Map;

public class HandledItemRepositoryImpl implements CustomHandledItemRepository {
    private final ElasticSearchOperationsUtil<HandledItem> fetchUtil;

    @Autowired
    public HandledItemRepositoryImpl(ElasticsearchOperations elasticsearchClient) {
        this.fetchUtil = new ElasticSearchOperationsUtil<>(HandledItem.class, elasticsearchClient);
    }

    @Override
    public Page<HandledItem> findXxxByUser(
            ProcessType processType,
            Long uid,
            Pageable pageable
    ) {
        Query processTypeQuery = new Query.Builder().match(q -> q.field("ProcessType").query(processType.name())).build();
        Query userIdQuery = new Query.Builder().match(q -> q.field("user.id").query(uid)).build();
        return fetchUtil.fetchWithPageable(
                b -> b.withQuery(q -> q.bool(bool -> bool.must(processTypeQuery, userIdQuery))),
                pageable);
    }

    @Override
    public Page<HandledItem> findXxxBySeller(
            ProcessType processType,
            Long uid,
            Pageable pageable
    ) {
        Query processTypeQuery = new Query.Builder().match(q -> q.field("ProcessType").query(processType.name())).build();
        Query sellerIdQuery = new Query.Builder().match(q -> q.field("item.seller").query(uid)).build();
        return fetchUtil.fetchWithPageable(
                b -> b.withQuery(q -> q.bool(bool -> bool.must(processTypeQuery, sellerIdQuery))),
                pageable);
    }

    @Override
    public Map<String, Long> countPaymentByProcessTypeWithUserId(Long uid) {
        String AGG_NAME = "countByProcessType";
        Aggregation aggregation = Aggregation.of(
                builder -> builder.terms(t -> t.field("ProcessType"))
        );
        return fetchUtil.fetchCountsDict(
                builder -> builder
                        .withQuery(b -> b.match(m -> m.field("user.id").query(uid)))
                        .withAggregation(AGG_NAME, aggregation)
        );
    }

    @Override
    public Map<String, Long> countPaymentByProcessTypeWithSellerId(Long uid) {
        String AGG_NAME = "countByProcessType";
        Aggregation aggregation = Aggregation.of(
                builder -> builder.terms(t -> t.field("ProcessType"))
        );
        return fetchUtil.fetchCountsDict(
                builder -> builder
                        .withQuery(b -> b.match(m -> m.field("item.seller").query(uid)))
                        .withAggregation(AGG_NAME, aggregation)
        );
    }
}
