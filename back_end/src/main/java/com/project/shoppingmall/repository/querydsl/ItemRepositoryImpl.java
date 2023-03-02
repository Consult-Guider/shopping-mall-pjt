package com.project.shoppingmall.repository.querydsl;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.nested.Tag;
import com.project.shoppingmall.repository.KeywordRepository;
import com.project.shoppingmall.utils.ElasticSearchOperationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.List;

public class ItemRepositoryImpl implements KeywordRepository<Item> {
    private final ElasticSearchOperationsUtil<Item> fetchUtil;

    @Autowired
    public ItemRepositoryImpl(ElasticsearchOperations elasticsearchClient) {
        this.fetchUtil = new ElasticSearchOperationsUtil<>(Item.class, elasticsearchClient);
    }

    @Override
    public Page<Item> findByKeyword(String keyword, Pageable pageable) {
        Query nameQuery = new Query.Builder().match(q -> q.field("name").query(keyword)).build();
        Query tagNameQuery = new Query.Builder().match(q -> q.field("tagList.name").query(keyword)).build();
        return fetchUtil.fetchWithPageable(
                b -> b.withQuery(q -> q.bool(bool -> bool.should(nameQuery, tagNameQuery))),
                pageable
        );
    }

    @Override
    public List<Tag> findDistinctTag() {
        String AGG_NAME = "distinct_tags";
        Aggregation aggregation = Aggregation.of(
                builder -> builder.terms(t -> t.field("tagList.name"))
        );
        return fetchUtil.fetchCountsDict(
                builder -> builder
                        .withQuery(b -> b.matchAll(m -> m))
                        .withAggregation(AGG_NAME, aggregation)
                ).keySet().stream()
                .map(s -> Tag.builder().name(s).build())
                .toList();
    }

    @Override
    public Page<Item> findByTagName(String tagName, Pageable pageable) {
        return fetchUtil.fetchWithPageable(
                b -> b.withQuery(
                        q -> q.match(m -> m.field("tagList.name").query(tagName))
                ),
                pageable);
    }
}
