package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomQuestionRepository {
    Page<Question> findQuestionByItemId(String iid, Pageable pageable);
    Page<Question> searchQuestionByKeyword(String keyword, Pageable pageable);
    Page<Question> readChildrenByQid(String qid);
    Page<Question> findQuestionByUserId(Long uid, Pageable pageable);
    Optional<Question> findByIidAndUid(String iid, Long uid);
}
