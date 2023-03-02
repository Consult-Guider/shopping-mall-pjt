package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Question;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QuestionRepository extends ElasticsearchRepository<Question, String>, CustomQuestionRepository {
}
