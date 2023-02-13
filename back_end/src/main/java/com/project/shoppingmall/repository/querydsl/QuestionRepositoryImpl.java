package com.project.shoppingmall.repository.querydsl;

import com.project.shoppingmall.domain.Question;
import com.project.shoppingmall.repository.CustomQuestionRepository;
import com.project.shoppingmall.utils.ElasticSearchOperationsUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

public class QuestionRepositoryImpl implements CustomQuestionRepository {
    private final ElasticSearchOperationsUtil<Question> fetchUtil;

    @Autowired
    public QuestionRepositoryImpl(ElasticsearchOperations elasticsearchClient) {
        this.fetchUtil = new ElasticSearchOperationsUtil<>(Question.class, elasticsearchClient);
    }

    @Override
    public Page<Question> findQuestionByItemId(String iid, Pageable pageable) {
        QueryBuilder query = boolQuery()
                .must(matchQuery("itemId", iid))
                .mustNot(existsQuery("parentId"));

        return fetchUtil.fetchWithPageable(query, pageable);
    }

    @Override
    public Page<Question> searchQuestionByKeyword(String keyword, Pageable pageable) {
        QueryBuilder query = boolQuery().should(QueryBuilders.matchQuery("content", keyword));

        return fetchUtil.fetchWithPageable(query, pageable);
    }

    @Override
    public Page<Question> readChildrenByQid(String qid) {
        FieldSortBuilder sortBuilder = new FieldSortBuilder("createdAt");
        sortBuilder.order(SortOrder.ASC);

        QueryBuilder query = matchQuery("parentId", qid);

        return fetchUtil.fetchWithSorting(query, sortBuilder);
    }

    @Override
    public Page<Question> findQuestionByUserId(Long uid, Pageable pageable) {
        QueryBuilder query = boolQuery()
                .must(matchQuery("userId", uid))
                .mustNot(existsQuery("parentId"));

        return fetchUtil.fetchWithPageable(query, pageable);
    }

    @Override
    public Optional<Question> findByIidAndUid(String iid, Long uid) {
        QueryBuilder query = boolQuery()
                .must(matchQuery("userId", uid))
                .must(matchQuery("itemId", iid))
                .mustNot(existsQuery("parentId"));

        return fetchUtil.fetchOne(query);
    }
}
