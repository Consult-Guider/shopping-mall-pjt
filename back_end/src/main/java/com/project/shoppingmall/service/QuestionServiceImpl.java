package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.LoginEntity;
import com.project.shoppingmall.domain.Question;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.QuestionCreateRequest;
import com.project.shoppingmall.model.request.QuestionUpdateRequest;
import com.project.shoppingmall.model.response.QuestionReadResponse;
import com.project.shoppingmall.model.response.SearchResponse;
import com.project.shoppingmall.repository.ItemRepository;
import com.project.shoppingmall.repository.QuestionRepository;
import com.project.shoppingmall.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private  final QuestionRepository questionRepository;
    private final ItemRepository itemRepository;

    private Item loadItemById(String iid) {
        return itemRepository.findById(iid)
                .orElseThrow(() ->
                        new CrudException(
                                ErrorCode.ITEM_NOT_FOUNDED,
                                String.format("조회를 시도한 Id: %s", iid)
                        )
                );
    }

    private Question loadQuestionById(String qid) {
        return questionRepository.findById(qid)
                .orElseThrow(() ->
                        new CrudException(
                                ErrorCode.QUESTION_NOT_FOUNDED,
                                String.format("조회에 사용된 ID: %s", qid)
                        )
                );
    }

    private void isQuestionYours(LoginDto loginDto, Question entity) {
        LoginEntity trg = entity.getUser()!=null ? entity.getUser() : entity.getSeller();

        if(!loginDto.getId().equals(trg.getId())) {
            throw new CrudException(
                    ErrorCode.NO_OWNERSHIP,
                    String.format("로그인된 유저 아이디: %s\nQnA의 작성자 아이디: %s", loginDto.getId(), trg.getId())
            );
        }
    }

    @Override
    public void create(String iid, QuestionCreateRequest request, LoginDto loginDto) {
        Item item = loadItemById(iid);
        Question entity = Question.fromDto(null, item, request, loginDto, () -> {
            throw new CrudException(ErrorCode.FORBIDDEN, "QnA 기능은 소비자와 판매자만 이용 가능.");
        });
        questionRepository.save(entity);
    }

    @Override
    public Page<QuestionReadResponse> readByIid(String iid, Pageable pageable) {
        return questionRepository.findQuestionByItemId(iid, pageable)
                .map(QuestionReadResponse::fromEntity);
    }

    @Override
    public Page<QuestionReadResponse> readByUid(Long uid, Pageable pageable) {
        return questionRepository.findQuestionByUserId(uid, pageable)
                .map(QuestionReadResponse::fromEntity);
    }

    @Override
    public QuestionReadResponse readByIidAndUid(String iid, Long uid) {
        return questionRepository.findByIidAndUid(iid, uid)
                .map(QuestionReadResponse::fromEntity)
                .orElseThrow(() ->
                        new CrudException(
                                ErrorCode.QUESTION_NOT_FOUNDED,
                                String.format("조회를 시도한 ItemId: %s, UserId: %s", iid, uid)
                        )
                );
    }

    @Override
    public Page<SearchResponse> searchByKeyword(String keyword, Pageable pageable) {
        return questionRepository.searchQuestionByKeyword(keyword, pageable)
                .map(SearchResponse::fromEntity);
    }

    @Override
    public void createChildrenByQid(String qid, QuestionCreateRequest request, LoginDto loginDto) {
        Question parent = loadQuestionById(qid);
        Question entity = Question.fromDto(qid, parent.getItem(), request, loginDto, () -> {
            throw new CrudException(ErrorCode.FORBIDDEN, "QnA 기능은 소비자와 판매자만 이용 가능.");
        });
        questionRepository.save(entity);
    }

    @Override
    public Page<QuestionReadResponse> readChildrenByQid(String qid) {
        return questionRepository.readChildrenByQid(qid)
                .map(QuestionReadResponse::fromEntity);
    }

    @Override
    public void update(String qid, QuestionUpdateRequest request, LoginDto loginDto) {
        Question entity = loadQuestionById(qid);
        Question entityOverWritten = request.overwrite(entity);

        isQuestionYours(loginDto, entity);
        questionRepository.save(entityOverWritten);
    }

    @Override
    public void delete(String qid, LoginDto loginDto) {
        Question entity = loadQuestionById(qid);

        isQuestionYours(loginDto, entity);
        questionRepository.delete(entity);
    }
}
