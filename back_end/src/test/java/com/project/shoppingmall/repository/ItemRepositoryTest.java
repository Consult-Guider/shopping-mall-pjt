package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.nested.Tag;
import com.project.shoppingmall.type.RoleType;
import com.project.shoppingmall.utils.EnableProjectElasticSearchConfiguration;
import com.project.shoppingmall.utils.EnableProjectQueryDslConfiguration;
import com.project.shoppingmall.utils.SetProfile;
import com.project.shoppingmall.utils.WithAuthenticationPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Item Repository Test")
@SetProfile
@EnableProjectElasticSearchConfiguration
@EnableProjectQueryDslConfiguration
@DataJpaTest
class ItemRepositoryTest {
    private final List<Item> fixtures = new ArrayList<>();
    private final Set<String> tableTagNames = new HashSet<>();
    @Autowired
    ItemRepository itemRepository;
    private List<Item> items;

    @BeforeEach
    void saveTestCase() {
        // fixtures
        List<Item> fixturesBeforeSaving = new ArrayList<>();

        Tag tag1 = Tag.builder().name("컴퓨터").build();
        Tag tag2 = Tag.builder().name("TV").build();
        Tag tag3 = Tag.builder().name("가전제품").build();
        tableTagNames.add(tag1.getName());
        tableTagNames.add(tag2.getName());
        tableTagNames.add(tag3.getName());

        Item entity1 = new Item();
        entity1.setName("GPU 4080");
        entity1.setPrice(15000L);
        entity1.addTagList(tag1);
        entity1.addTagList(tag2);
        entity1.setSeller(1L);
        fixturesBeforeSaving.add(entity1);

        Item entity2 = new Item();
        entity2.setName("GPU 1080");
        entity2.setPrice(10000L);
        entity2.addTagList(tag1);
        entity2.addTagList(tag3);
        fixturesBeforeSaving.add(entity2);

        // save all
        Iterable<Item> itemsSaved = itemRepository.saveAll(fixturesBeforeSaving);

        items = StreamSupport.stream(itemsSaved.spliterator(), false).toList();
        fixtures.addAll(items);
    }

    @AfterEach
    void deleteTestCase() {
        itemRepository.deleteAll(fixtures);
    }

    @Test
    @WithAuthenticationPrincipal(role = RoleType.SELLER)
    @DisplayName("[정상 구동]전체 목록을 조회하기 위한 함수.")
    void given_whenCallFindAll_then() {
        // given

        // when
        Iterable<Item> all = itemRepository.findAll();
        Stream<Item> stream = StreamSupport.stream(all.spliterator(), false);

        // then
        stream.map(Item::toString).forEach(log::debug);

    }

    @Test
    @DisplayName("[정상 구동][findByKeyword] keyword를 이용해서 제목 검색을 시도할 때")
    void givenTitleKeyword_whenCallFindByKeyword_thenGivePageOfItem() {
        // given
        String keyword = "4080";
        PageRequest page = PageRequest.of(0, 10);

        // when
        Page<Item> hits = itemRepository.findByKeyword(keyword, page);

        // then
        hits.map(Item::toString).forEach(log::debug);
        hits.map(Item::getName).forEach(name -> assertThat(name).contains(keyword));
    }

    @Test
    @DisplayName("[정상 구동][findByKeyword] keyword를 이용해서 태그 검색을 시도할 때")
    void givenTagKeyword_whenCallFindByKeyword_thenGivePageOfItem() {
        // given
        String keyword = "가전제품";
        PageRequest page = PageRequest.of(0, 10);

        // when
        Page<Item> hits = itemRepository.findByKeyword(keyword, page);

        // then
        hits.map(Item::toString).forEach(log::debug);
        hits.map(item -> item.getTagList().stream().map(Tag::getName).toList())
                .forEach(nameList -> assertThat(nameList).contains(keyword));
    }

    @Test
    @DisplayName("[정상 구동][findByKeyword] keyword를 이용해서 검색을 시도할 때, 정렬 기능 정상 작동.")
    void givenKeywordAndSort_whenCallFindByKeyword_thenGivePageOfItem() {
        // given
        String keyword = "GPU";
        PageRequest pageAsc = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "price"));
        PageRequest pageDesc = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "price"));

        // when
        Page<Item> hitsAsc = itemRepository.findByKeyword(keyword, pageAsc);
        Page<Item> hitsDesc = itemRepository.findByKeyword(keyword, pageDesc);

        // then
        log.debug("가격 기준 오름차순");
        hitsAsc.map(Item::toString).forEach(log::debug);

        log.debug("가격 기준 내림차순");
        hitsDesc.map(Item::toString).forEach(log::debug);

        List<Long> listAsc = hitsAsc.stream().map(Item::getPrice).toList();
        List<Long> listDesc = hitsDesc.stream().map(Item::getPrice).toList();
        assertThat(listAsc.get(0) <= listAsc.get(1)).isTrue();
        assertThat(listDesc.get(0) >= listDesc.get(1)).isTrue();
    }

    @Test
    @DisplayName("[정상 구동][findByKeyword] keyword를 이용해서 검색을 시도할 때, countQuery 정상 작동.")
    void givenKeyword_whenCallFindByKeyword_thenGiveTotalPages() {
        // given
        String keyword = "GPU";
        PageRequest page = PageRequest.of(0, 1);

        // when
        Page<Item> hits = itemRepository.findByKeyword(keyword, page);

        // then
        log.debug("검색된 총 갯수: {}, 실제 갯수: {}", hits.getTotalElements(), items.size());
        assertThat(hits.getTotalElements()).isNotEqualTo(page.getPageSize());
    }

    @Test
    @DisplayName("[정상 구동][findDistinctTag] Item에 종속된 Tags 호출.")
    void givenNothing_whenCallFindDistinctTag_thenGiveListOfTags() {
        // given

        // when
        List<Tag> hits = itemRepository.findDistinctTag();

        // then
        List<String> tagList = hits.stream().map(Tag::getName).toList();
        hits.forEach(tag -> {
            log.debug("검색된 태그명: {}", tag.getName());
        });
        tableTagNames.forEach(tag -> {
            assertThat(tag).isIn(tagList);
        });
    }

    @Test
    @DisplayName("[정상 구동][findByTagName] 주어진 Tag명을 내포한 아이템 검색.")
    void givenTagName_whenCallFindByTagName_thenGivePageOfItems() {
        // given
        String tagName = "TV";
        PageRequest pageable = PageRequest.of(0, 10);

        // when
        Page<Item> hits = itemRepository.findByTagName(tagName, pageable);

        // then
        hits.forEach(item -> {
            log.debug("검색된 상품명: {}", item.getName());

            List<String> tagNameList = item.getTagList().stream().map(Tag::getName).toList();
            assertThat(tagName).isIn(tagNameList);
        });
    }
}