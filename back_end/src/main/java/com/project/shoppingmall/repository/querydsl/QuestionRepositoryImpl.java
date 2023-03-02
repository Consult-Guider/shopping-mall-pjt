package com.project.shoppingmall.repository.querydsl;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.project.shoppingmall.domain.Question;
import com.project.shoppingmall.repository.CustomQuestionRepository;
import com.project.shoppingmall.utils.ElasticSearchOperationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Optional;

public class QuestionRepositoryImpl implements CustomQuestionRepository {
    private final ElasticSearchOperationsUtil<Question> fetchUtil;

    @Autowired
    public QuestionRepositoryImpl(ElasticsearchOperations elasticsearchClient) {
        this.fetchUtil = new ElasticSearchOperationsUtil<>(Question.class, elasticsearchClient);
    }

    @Override
    public Page<Question> findQuestionByItemId(String iid, Pageable pageable) {
        Query itemIdQuery = new Query.Builder().match(q -> q.field("item.id").query(iid)).build();
        Query parentIdQuery = new Query.Builder().exists(q -> q.field("parentId")).build();
        return fetchUtil.fetchWithPageable(
                b -> b.withQuery(q -> q.bool(bool -> bool.must(itemIdQuery).mustNot(parentIdQuery))),
                pageable);
    }

    @Override
    public Page<Question> searchQuestionByKeyword(String keyword, Pageable pageable) {
        Query query = new Query.Builder().match(q -> q.field("content").query(keyword)).build();
        return fetchUtil.fetchWithPageable(
                b -> b.withQuery(q -> q.bool(bool -> bool.should(query))),
                pageable);
    }

    @Override
    public Page<Question> readChildrenByQid(String qid) {
        return fetchUtil.fetchWithPageable(
                b -> b.withSort(Sort.by(Sort.Direction.ASC, "createdAt"))
                        .withQuery(q -> q.match(m -> m.field("parentId").query(qid))),
                Pageable.unpaged());
    }

    @Override
    public Page<Question> findQuestionByUserId(Long uid, Pageable pageable) {
        Query userIdQuery = new Query.Builder().match(q -> q.field("user.id").query(uid)).build();
        Query parentIdQuery = new Query.Builder().exists(q -> q.field("parentId")).build();
        return fetchUtil.fetchWithPageable(
                b -> b.withQuery(q -> q.bool(bool -> bool.must(userIdQuery).mustNot(parentIdQuery))),
                pageable);
    }

    @Override
    public Optional<Question> findByIidAndUid(String iid, Long uid) {
        Query userIdQuery = new Query.Builder().match(q -> q.field("user.id").query(uid)).build();
        Query itemIdQuery = new Query.Builder().match(q -> q.field("item.id").query(uid)).build();
        Query parentIdQuery = new Query.Builder().exists(q -> q.field("parentId")).build();
        return fetchUtil.fetchOne(
                b -> b.withQuery(q -> q.bool(bool -> bool
                        .must(userIdQuery)
                        .must(itemIdQuery)
                        .mustNot(parentIdQuery))
                )
        );
    }
}
