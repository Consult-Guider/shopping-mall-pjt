package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Question;
import com.project.shoppingmall.utils.EnableProjectElasticSearchConfiguration;
import com.project.shoppingmall.utils.EnableProjectQueryDslConfiguration;
import com.project.shoppingmall.utils.FixtureFactory;
import com.project.shoppingmall.utils.SetProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Question Repository Test")
@SetProfile
@EnableProjectElasticSearchConfiguration
@EnableProjectQueryDslConfiguration
@DataJpaTest
class QuestionRepositoryTest {
    @Autowired
    QuestionRepository questionRepository;

    @Test
    @DisplayName("[정상 구동][findQuestionByItemId] 특정 상품에 관련된 QnA 로드.")
    void givenItemId_whenCallFindQuestionByItemId_thenReturnQuestions() {
        // given
        List<Question> questionList = new ArrayList<>();
        questionList.add(FixtureFactory.questionFixture(1L, "item1"));
        questionList.add(FixtureFactory.questionFixture(2L, "item1"));
        questionList.add(FixtureFactory.questionFixture(3L, "item1"));
        questionList.add(FixtureFactory.questionFixture(1L, "item2"));
        questionList.add(FixtureFactory.questionFixture(2L, "item2"));
        questionList.add(FixtureFactory.questionFixture(3L, "item2"));
        Iterable<Question> questionListSaved = questionRepository.saveAll(questionList);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Question> page = questionRepository.findQuestionByItemId("item1", pageable);
        questionRepository.deleteAll(questionListSaved);

        // then
        page.map(Question::toString).forEach(log::debug);
        page.forEach(question -> assertThat(question.getItemId()).isEqualTo("item1"));
    }

    @Test
    @DisplayName("[정상 구동][findQuestionByItemId] 특정 상품에 관련된 QnA 로드 시, 정렬 기능 및 페이징 기능 정상 작동.")
    void givenItemIdAndSort_whenCallFindQuestionByItemId_thenReturnQuestions() {
        // given
        List<Question> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.questionFixture(1L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(2L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(3L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(4L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(5L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(6L, "item1"));
        Iterable<Question> questionListSaved = questionRepository.saveAll(reviewList);

        int size = 4;
        PageRequest pageAsc = PageRequest.of(0, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        PageRequest pageDesc = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // when
        Page<Question> hitsAsc = questionRepository.findQuestionByItemId("item1", pageAsc);
        Page<Question> hitsDesc = questionRepository.findQuestionByItemId("item1", pageDesc);

        questionRepository.deleteAll(questionListSaved);

        // then
        log.debug("생성일자 기준 오름차순");
        hitsAsc.map(Question::toString).forEach(log::debug);

        log.debug("생성일자 기준 내림차순");
        hitsDesc.map(Question::toString).forEach(log::debug);

        List<LocalDateTime> listAsc = hitsAsc.stream().map(Question::getCreatedAt).toList();
        List<LocalDateTime> listDesc = hitsDesc.stream().map(Question::getCreatedAt).toList();

        // 오름, 내림차순 확인.
        int firstIdx = 0;
        int lastIdx = listAsc.size() - 1;
        assertThat(listAsc.get(firstIdx).isBefore(listAsc.get(lastIdx)) || listAsc.get(firstIdx).isEqual(listAsc.get(lastIdx))).isTrue();
        assertThat(listDesc.get(firstIdx).isAfter(listDesc.get(lastIdx)) || listDesc.get(firstIdx).isEqual(listDesc.get(lastIdx))).isTrue();

        // 페이징 확인.
        assertThat(listAsc.size()).isEqualTo(size);
        assertThat(listDesc.size()).isEqualTo(size);
    }

    @Test
    @DisplayName("[정상 구동][findQuestionByItemId] 특정 상품에 관련된 QnA 로드 시, countQuery 정상 작동.")
    void givenItemId_whenCallFindQuestionByItemId_thenReturnTotalPages() {
        // given
        List<Question> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.questionFixture(1L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(2L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(3L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(4L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(5L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(6L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(11L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(12L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(13L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(14L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(15L, "item1"));
        reviewList.add(FixtureFactory.questionFixture(16L, "item1"));
        Iterable<Question> questionListSaved = questionRepository.saveAll(reviewList);

        int size = 4;
        PageRequest page = PageRequest.of(0, size);

        // when
        Page<Question> hits = questionRepository.findQuestionByItemId("item1", page);

        questionRepository.deleteAll(questionListSaved);

        // then
        log.debug("검색된 총 갯수: {}, 실제 갯수: {}", hits.getTotalElements(), reviewList.size());
        assertThat(hits.getTotalElements()).isNotEqualTo(size);
    }

    @Test
    @DisplayName("[정상 구동][searchQuestionByKeyword] 특정 상품에 관련된 QnA 로드.")
    void givenKeyword_whenCallSearchQuestionByKeyword_thenReturnQuestions() {
        // given
        String keyword = "가상의";

        List<Question> questionList = new ArrayList<>();
        questionList.add(FixtureFactory.questionFixture(1L, "item1", "QnA1"));
        questionList.add(FixtureFactory.questionFixture(2L, "item1", "QnA2"));
        questionList.add(FixtureFactory.questionFixture(3L, "item1", "QnA3"));
        questionList.add(FixtureFactory.questionFixture(1L, "item2", "가상의 QnA4"));
        questionList.add(FixtureFactory.questionFixture(2L, "item2", "가상의 가상의 가상의 QnA5"));
        questionList.add(FixtureFactory.questionFixture(3L, "item2", "가상의 가상의 QnA6"));
        Iterable<Question> questionListSaved = questionRepository.saveAll(questionList);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Question> page = questionRepository.searchQuestionByKeyword(keyword, pageable);
        questionRepository.deleteAll(questionListSaved);

        // then
        page.map(Question::toString).forEach(log::debug);

        page.forEach(question -> assertThat(question.getContent().contains(keyword)).isTrue());
    }

    @Test
    @DisplayName("[정상 구동][searchQuestionByKeyword] 특정 상품에 관련된 QnA 로드할 때, 정렬 기능 및 페이징 기능 정상 작동.")
    void givenKeywordAndSort_whenCallSearchQuestionByKeyword_thenReturnQuestions() {
        // given
        String keyword = "리뷰";

        List<Question> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.questionFixture(1L, "item1", "리뷰 1"));
        reviewList.add(FixtureFactory.questionFixture(2L, "item1", "리뷰 2"));
        reviewList.add(FixtureFactory.questionFixture(3L, "item1", "리뷰 3"));
        reviewList.add(FixtureFactory.questionFixture(1L, "item2", "리뷰 4"));
        reviewList.add(FixtureFactory.questionFixture(2L, "item2", "리뷰 5"));
        reviewList.add(FixtureFactory.questionFixture(3L, "item2", "리뷰 6"));
        Iterable<Question> reviewListSaved = questionRepository.saveAll(reviewList);

        int size = 4;
        PageRequest pageAsc = PageRequest.of(0, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        PageRequest pageDesc = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // when
        Page<Question> hitsAsc = questionRepository.searchQuestionByKeyword(keyword, pageAsc);
        Page<Question> hitsDesc = questionRepository.searchQuestionByKeyword(keyword, pageDesc);

        questionRepository.deleteAll(reviewListSaved);

        // then
        log.debug("생성일자 기준 오름차순");
        hitsAsc.map(Question::toString).forEach(log::debug);

        log.debug("생성일자 기준 내림차순");
        hitsDesc.map(Question::toString).forEach(log::debug);

        List<LocalDateTime> listAsc = hitsAsc.stream().map(Question::getCreatedAt).toList();
        List<LocalDateTime> listDesc = hitsDesc.stream().map(Question::getCreatedAt).toList();

        int firstIdx = 0;
        int lastIdx = listAsc.size() - 1;
        assertThat(listAsc.get(firstIdx).isBefore(listAsc.get(lastIdx)) || listAsc.get(firstIdx).isEqual(listAsc.get(lastIdx))).isTrue();
        assertThat(listDesc.get(firstIdx).isAfter(listDesc.get(lastIdx)) || listDesc.get(firstIdx).isEqual(listDesc.get(lastIdx))).isTrue();

        assertThat(listAsc.size()).isEqualTo(size);
        assertThat(listDesc.size()).isEqualTo(size);
    }

    @Test
    @DisplayName("[정상 구동][searchQuestionByKeyword] 특정 상품에 관련된 QnA 로드 시, countQuery 정상 작동.")
    void givenKeywordAndSort_whenCallSearchQuestionByKeyword_thenReturnTotalPages() {
        // given
        List<Question> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.questionFixture(1L, "item1", "QnA 1"));
        reviewList.add(FixtureFactory.questionFixture(2L, "item1", "QnA 1"));
        reviewList.add(FixtureFactory.questionFixture(3L, "item1", "QnA 1"));
        reviewList.add(FixtureFactory.questionFixture(4L, "item1", "QnA 1"));
        reviewList.add(FixtureFactory.questionFixture(5L, "item1", "QnA 1"));
        reviewList.add(FixtureFactory.questionFixture(6L, "item1", "QnA 1"));
        reviewList.add(FixtureFactory.questionFixture(11L, "item1", "QnA 1"));
        reviewList.add(FixtureFactory.questionFixture(12L, "item1", "QnA 1"));
        reviewList.add(FixtureFactory.questionFixture(13L, "item1", "QnA 1"));
        reviewList.add(FixtureFactory.questionFixture(14L, "item1", "QnA 1"));
        reviewList.add(FixtureFactory.questionFixture(15L, "item1", "QnA 1"));
        reviewList.add(FixtureFactory.questionFixture(16L, "item1", "QnA 1"));
        Iterable<Question> questionListSaved = questionRepository.saveAll(reviewList);

        int size = 4;
        PageRequest page = PageRequest.of(0, size);

        // when
        Page<Question> hits = questionRepository.searchQuestionByKeyword("QnA", page);

        questionRepository.deleteAll(questionListSaved);

        // then
        log.debug("검색된 총 갯수: {}, 실제 갯수: {}", hits.getTotalElements(), reviewList.size());
        assertThat(hits.getTotalElements()).isEqualTo(reviewList.size());
    }
}