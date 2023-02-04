package com.project.shoppingmall.service;

import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.QuestionCreateRequest;
import com.project.shoppingmall.model.request.QuestionUpdateRequest;
import com.project.shoppingmall.model.response.QuestionReadResponse;
import com.project.shoppingmall.model.response.QuestionSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    void create(String iid, QuestionCreateRequest request, LoginDto loginDto);

    Page<QuestionReadResponse> readByIid(String iid, Pageable pageable);

    Page<QuestionSearchResponse> searchByKeyword(String keyword, Pageable pageable);

    void update(String qid, QuestionUpdateRequest request, LoginDto loginDto);

    void delete(String qid, LoginDto loginDto);

    Page<QuestionReadResponse> readChildrenByQid(String qid);

    void createChildrenByQid(String qid, QuestionCreateRequest request, LoginDto loginDto);

    Page<QuestionReadResponse> readByUid(Long uid, Pageable pageable);

    QuestionReadResponse readByIidAndUid(String iid, Long uid);
}
