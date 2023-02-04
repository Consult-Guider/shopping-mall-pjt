package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

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
}
