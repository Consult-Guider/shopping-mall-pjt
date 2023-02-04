package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.Question;
import com.project.shoppingmall.repository.CustomQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements CustomQuestionRepository {
    private final ElasticsearchOperations elasticsearchClient;

    @Override
    public Page<Question> findQuestionByItemId(String iid, Pageable pageable) {
        QueryBuilder query = boolQuery()
                .must(matchQuery("itemId", iid))
                .mustNot(existsQuery("parentId"));

        SearchHits<Question> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withPageable(pageable)
                        .build(),
                Question.class
        );

        List<Question> items = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(items, pageable, result.getTotalHits());
    }

    @Override
    public Page<Question> searchQuestionByKeyword(String keyword, Pageable pageable) {
        QueryBuilder query = boolQuery().should(QueryBuilders.matchQuery("content", keyword));

        SearchHits<Question> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withPageable(pageable)
                        .build(),
                Question.class
        );

        List<Question> items = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(items, pageable, result.getTotalHits());
    }

    @Override
    public Page<Question> readChildrenByQid(String qid) {
        FieldSortBuilder sortBuilder = new FieldSortBuilder("createdAt");
        sortBuilder.order(SortOrder.DESC);

        QueryBuilder query = matchQuery("parentId", qid);

        SearchHits<Question> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withSorts(sortBuilder)
                        .build(),
                Question.class
        );

        List<Question> items = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(items, Pageable.unpaged(), result.getTotalHits());
    }

    @Override
    public Page<Question> findQuestionByUserId(Long uid, Pageable pageable) {
        QueryBuilder query = boolQuery()
                .must(matchQuery("userId", uid))
                .mustNot(existsQuery("parentId"));

        SearchHits<Question> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withPageable(pageable)
                        .build(),
                Question.class
        );

        List<Question> items = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
        return new PageImpl<>(items, Pageable.unpaged(), result.getTotalHits());
    }

    @Override
    public Optional<Question> findByIidAndUid(String iid, Long uid) {
        FieldSortBuilder sortBuilder = new FieldSortBuilder("createdAt");
        sortBuilder.order(SortOrder.DESC);

        QueryBuilder query = boolQuery()
                .must(matchQuery("userId", uid))
                .must(matchQuery("itemId", iid))
                .mustNot(existsQuery("parentId"));

        SearchHits<Question> result = elasticsearchClient.search(
                new NativeSearchQueryBuilder()
                        .withQuery(query)
                        .withSorts(sortBuilder)
                        .build(),
                Question.class
        );

        return Optional.of(result)
                .filter(SearchHits::hasSearchHits)
                .map(x -> x.getSearchHit(0))
                .map(SearchHit::getContent);
    }
}
