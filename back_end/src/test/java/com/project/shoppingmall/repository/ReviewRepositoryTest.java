package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Review;
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
@DisplayName("Review Repository Test")
@SetProfile
@EnableProjectElasticSearchConfiguration
@EnableProjectQueryDslConfiguration
@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("[정상 구동][findReviewByItemId] 특정 상품에 관련된 리뷰 로드.")
    void givenItemId_whenCallFindReviewByItemId_thenReturnReviews() {
        // given
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.reviewFixture(1L, "item1"));
        reviewList.add(FixtureFactory.reviewFixture(2L, "item1"));
        reviewList.add(FixtureFactory.reviewFixture(3L, "item1"));
        reviewList.add(FixtureFactory.reviewFixture(1L, "item2"));
        reviewList.add(FixtureFactory.reviewFixture(2L, "item2"));
        reviewList.add(FixtureFactory.reviewFixture(3L, "item2"));
        Iterable<Review> reviewListSaved = reviewRepository.saveAll(reviewList);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Review> page = reviewRepository.findReviewByItemId("item1", pageable);
        reviewRepository.deleteAll(reviewListSaved);

        // then
        page.map(Review::toString).forEach(log::debug);
        page.forEach(review -> assertThat(review.getItem().getId()).isEqualTo("item1"));
    }

    @Test
    @DisplayName("[정상 구동][findReviewByItemId] 특정 상품에 관련된 리뷰 로드 시, 정렬 기능 및 페이징 기능 정상 작동.")
    void givenItemIdAndSort_whenCallFindReviewByItemId_thenReturnReviews() {
        // given
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.reviewFixture(1L, "item1"));
        reviewList.add(FixtureFactory.reviewFixture(2L, "item1"));
        reviewList.add(FixtureFactory.reviewFixture(3L, "item1"));
        reviewList.add(FixtureFactory.reviewFixture(4L, "item1"));
        reviewList.add(FixtureFactory.reviewFixture(5L, "item1"));
        reviewList.add(FixtureFactory.reviewFixture(6L, "item1"));
        Iterable<Review> reviewListSaved = reviewRepository.saveAll(reviewList);

        int size = 4;
        PageRequest pageAsc = PageRequest.of(0, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        PageRequest pageDesc = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // when
        Page<Review> hitsAsc = reviewRepository.findReviewByItemId("item1", pageAsc);
        Page<Review> hitsDesc = reviewRepository.findReviewByItemId("item1", pageDesc);

        reviewRepository.deleteAll(reviewListSaved);

        // then
        log.debug("생성일자 기준 오름차순");
        hitsAsc.map(Review::toString).forEach(log::debug);

        log.debug("생성일자 기준 내림차순");
        hitsDesc.map(Review::toString).forEach(log::debug);

        List<LocalDateTime> listAsc = hitsAsc.stream().map(Review::getCreatedAt).toList();
        List<LocalDateTime> listDesc = hitsDesc.stream().map(Review::getCreatedAt).toList();

        int firstIdx = 0;
        int lastIdx = listAsc.size() - 1;
        assertThat(listAsc.get(firstIdx).isBefore(listAsc.get(lastIdx)) || listAsc.get(firstIdx).isEqual(listAsc.get(lastIdx))).isTrue();
        assertThat(listDesc.get(firstIdx).isAfter(listDesc.get(lastIdx)) || listDesc.get(firstIdx).isEqual(listDesc.get(lastIdx))).isTrue();

        assertThat(listAsc.size()).isEqualTo(size);
        assertThat(listDesc.size()).isEqualTo(size);
    }

    @Test
    @DisplayName("[정상 구동][findReviewByUserId] 특정 사용자와 관련된 리뷰 로드.")
    void givenUserId_whenCallFindReviewByUserId_thenReturnReviews() {
        // given
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.reviewFixture(1L, "item1"));
        reviewList.add(FixtureFactory.reviewFixture(2L, "item1"));
        reviewList.add(FixtureFactory.reviewFixture(3L, "item1"));
        reviewList.add(FixtureFactory.reviewFixture(1L, "item2"));
        reviewList.add(FixtureFactory.reviewFixture(2L, "item2"));
        reviewList.add(FixtureFactory.reviewFixture(3L, "item2"));
        Iterable<Review> reviewListSaved = reviewRepository.saveAll(reviewList);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Review> page = reviewRepository.findReviewByUserId(1L, pageable);
        reviewRepository.deleteAll(reviewListSaved);

        // then
        page.map(Review::toString).forEach(log::debug);
        page.forEach(review -> assertThat(review.getUser().getId()).isEqualTo(1L));
    }

    @Test
    @DisplayName("[정상 구동][findReviewByUserId] 특정 사용자와 관련된 리뷰 로드 시, 정렬 기능 및 페이징 기능 정상 작동.")
    void givenUserIdAndSort_whenCallFindReviewByUserId_thenReturnReviews() {
        // given
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.reviewFixture(1L, "item1"));
        reviewList.add(FixtureFactory.reviewFixture(1L, "item2"));
        reviewList.add(FixtureFactory.reviewFixture(1L, "item3"));
        reviewList.add(FixtureFactory.reviewFixture(1L, "item4"));
        reviewList.add(FixtureFactory.reviewFixture(1L, "item5"));
        reviewList.add(FixtureFactory.reviewFixture(1L, "item6"));
        Iterable<Review> reviewListSaved = reviewRepository.saveAll(reviewList);

        int size = 4;
        PageRequest pageAsc = PageRequest.of(0, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        PageRequest pageDesc = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // when
        Page<Review> hitsAsc = reviewRepository.findReviewByUserId(1L, pageAsc);
        Page<Review> hitsDesc = reviewRepository.findReviewByUserId(1L, pageDesc);

        reviewRepository.deleteAll(reviewListSaved);

        // then
        log.debug("생성일자 기준 오름차순");
        hitsAsc.map(Review::toString).forEach(log::debug);

        log.debug("생성일자 기준 내림차순");
        hitsDesc.map(Review::toString).forEach(log::debug);

        List<LocalDateTime> listAsc = hitsAsc.stream().map(Review::getCreatedAt).toList();
        List<LocalDateTime> listDesc = hitsDesc.stream().map(Review::getCreatedAt).toList();

        int firstIdx = 0;
        int lastIdx = listAsc.size() - 1;
        assertThat(listAsc.get(firstIdx).isBefore(listAsc.get(lastIdx)) || listAsc.get(firstIdx).isEqual(listAsc.get(lastIdx))).isTrue();
        assertThat(listDesc.get(firstIdx).isAfter(listDesc.get(lastIdx)) || listDesc.get(firstIdx).isEqual(listDesc.get(lastIdx))).isTrue();

        assertThat(listAsc.size()).isEqualTo(size);
        assertThat(listDesc.size()).isEqualTo(size);
    }

    @Test
    @DisplayName("[정상 구동][findReviewBySellerId] 특정 판매자와 관련된 리뷰 로드.")
    void givenSellerId_whenCallFindReviewBySellerId_thenReturnReviews() {
        // given
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.reviewFixture(1L));
        reviewList.add(FixtureFactory.reviewFixture(2L));
        reviewList.add(FixtureFactory.reviewFixture(3L));
        reviewList.add(FixtureFactory.reviewFixture(1L));
        reviewList.add(FixtureFactory.reviewFixture(2L));
        reviewList.add(FixtureFactory.reviewFixture(3L));
        Iterable<Review> reviewListSaved = reviewRepository.saveAll(reviewList);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Review> page = reviewRepository.findReviewBySellerId(1L, pageable);
        reviewRepository.deleteAll(reviewListSaved);

        // then
        page.map(Review::toString).forEach(log::debug);
        page.forEach(review -> assertThat(review.getItem().getSeller()).isEqualTo(1L));
    }

    @Test
    @DisplayName("[정상 구동][findReviewBySellerId] 특정 판매자와 관련된 리뷰 로드 시, 정렬 기능 및 페이징 기능 정상 작동.")
    void givenSellerIdAndSort_whenCallFindReviewBySellerId_thenReturnReviews() {
        // given
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.reviewFixture(1L));
        reviewList.add(FixtureFactory.reviewFixture(1L));
        reviewList.add(FixtureFactory.reviewFixture(1L));
        reviewList.add(FixtureFactory.reviewFixture(1L));
        reviewList.add(FixtureFactory.reviewFixture(1L));
        reviewList.add(FixtureFactory.reviewFixture(1L));
        Iterable<Review> reviewListSaved = reviewRepository.saveAll(reviewList);

        int size = 4;
        PageRequest pageAsc = PageRequest.of(0, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        PageRequest pageDesc = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // when
        Page<Review> hitsAsc = reviewRepository.findReviewBySellerId(1L, pageAsc);
        Page<Review> hitsDesc = reviewRepository.findReviewBySellerId(1L, pageDesc);

        reviewRepository.deleteAll(reviewListSaved);

        // then
        log.debug("생성일자 기준 오름차순");
        hitsAsc.map(Review::toString).forEach(log::debug);

        log.debug("생성일자 기준 내림차순");
        hitsDesc.map(Review::toString).forEach(log::debug);

        List<LocalDateTime> listAsc = hitsAsc.stream().map(Review::getCreatedAt).toList();
        List<LocalDateTime> listDesc = hitsDesc.stream().map(Review::getCreatedAt).toList();

        int firstIdx = 0;
        int lastIdx = listAsc.size() - 1;
        assertThat(listAsc.get(firstIdx).isBefore(listAsc.get(lastIdx)) || listAsc.get(firstIdx).isEqual(listAsc.get(lastIdx))).isTrue();
        assertThat(listDesc.get(firstIdx).isAfter(listDesc.get(lastIdx)) || listDesc.get(firstIdx).isEqual(listDesc.get(lastIdx))).isTrue();

        assertThat(listAsc.size()).isEqualTo(size);
        assertThat(listDesc.size()).isEqualTo(size);
    }

    @Test
    @DisplayName("[정상 구동][searchReviewByKeyword] keyword를 이용해서 검색을 시도할 때")
    void givenKeyword_whenCallSearchReviewByKeyword_thenReturnReviews() {
        // given
        String keyword = "가상의";

        List<Review> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.reviewFixture(1L, "item1", "리뷰1"));
        reviewList.add(FixtureFactory.reviewFixture(2L, "item1", "리뷰2"));
        reviewList.add(FixtureFactory.reviewFixture(3L, "item1", "리뷰3"));
        reviewList.add(FixtureFactory.reviewFixture(1L, "item2", "가상의 리뷰4"));
        reviewList.add(FixtureFactory.reviewFixture(2L, "item2", "가상의 가상의 가상의 리뷰5"));
        reviewList.add(FixtureFactory.reviewFixture(3L, "item2", "가상의 가상의 리뷰6"));
        Iterable<Review> reviewListSaved = reviewRepository.saveAll(reviewList);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Review> page = reviewRepository.searchReviewByKeyword(keyword, pageable);
        reviewRepository.deleteAll(reviewListSaved);

        // then
        page.map(Review::toString).forEach(log::debug);

        page.forEach(review -> assertThat(review.getContent().contains(keyword)).isTrue());
    }

    @Test
    @DisplayName("[정상 구동][searchReviewByKeyword] keyword를 이용해서 검색을 시도할 때, 정렬 기능 및 페이징 기능 정상 작동.")
    void givenKeywordAndSort_whenCallSearchReviewByKeyword_thenReturnReviews() {
        // given
        String keyword = "리뷰";

        List<Review> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.reviewFixture(1L, "item1", "리뷰 1"));
        reviewList.add(FixtureFactory.reviewFixture(2L, "item1", "리뷰 2"));
        reviewList.add(FixtureFactory.reviewFixture(3L, "item1", "리뷰 3"));
        reviewList.add(FixtureFactory.reviewFixture(1L, "item2", "리뷰 4"));
        reviewList.add(FixtureFactory.reviewFixture(2L, "item2", "리뷰 5"));
        reviewList.add(FixtureFactory.reviewFixture(3L, "item2", "리뷰 6"));
        Iterable<Review> reviewListSaved = reviewRepository.saveAll(reviewList);

        int size = 4;
        PageRequest pageAsc = PageRequest.of(0, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        PageRequest pageDesc = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // when
        Page<Review> hitsAsc = reviewRepository.searchReviewByKeyword(keyword, pageAsc);
        Page<Review> hitsDesc = reviewRepository.searchReviewByKeyword(keyword, pageDesc);

        reviewRepository.deleteAll(reviewListSaved);

        // then
        log.debug("생성일자 기준 오름차순");
        hitsAsc.map(Review::toString).forEach(log::debug);

        log.debug("생성일자 기준 내림차순");
        hitsDesc.map(Review::toString).forEach(log::debug);

        List<LocalDateTime> listAsc = hitsAsc.stream().map(Review::getCreatedAt).toList();
        List<LocalDateTime> listDesc = hitsDesc.stream().map(Review::getCreatedAt).toList();

        int firstIdx = 0;
        int lastIdx = listAsc.size() - 1;
        assertThat(listAsc.get(firstIdx).isBefore(listAsc.get(lastIdx)) || listAsc.get(firstIdx).isEqual(listAsc.get(lastIdx))).isTrue();
        assertThat(listDesc.get(firstIdx).isAfter(listDesc.get(lastIdx)) || listDesc.get(firstIdx).isEqual(listDesc.get(lastIdx))).isTrue();

        assertThat(listAsc.size()).isEqualTo(size);
        assertThat(listDesc.size()).isEqualTo(size);
    }

    @Test
    @DisplayName("[정상 구동][getAverage] 특정 아이템의 리뷰 평균을 구할 때, 정상 작동.")
    void givenItemId_whenCallGetAverage_thenReturnAverage() {
        // given
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(FixtureFactory.reviewFixture("상품 1", 10));
        reviewList.add(FixtureFactory.reviewFixture("상품 1", 20));
        reviewList.add(FixtureFactory.reviewFixture("상품 1", 30));
        reviewList.add(FixtureFactory.reviewFixture("상품 1", 40));
        reviewList.add(FixtureFactory.reviewFixture("상품 1", 50));
        Iterable<Review> reviewListSaved = reviewRepository.saveAll(reviewList);

        // when
        Double average = reviewRepository.getAverageByItemId("상품 1");
        reviewRepository.deleteAll(reviewListSaved);

        // then
        log.debug("평점 평균값: {}", average);

        double trueAvg = reviewList.stream().mapToInt(Review::getRating).average().orElse(0d);
        assertThat(average).isEqualTo(trueAvg);
    }
}