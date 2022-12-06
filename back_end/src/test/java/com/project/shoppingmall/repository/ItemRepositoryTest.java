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

import java.util.*;
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
    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void saveTestCase() {
        // fixtures
        List<Item> fixturesBeforeSaving = new ArrayList<>();

        Tag tag = new Tag();
        tag.setName("컴퓨터");

        Item entity1 = new Item();
        entity1.setName("GPU 4080");
        entity1.setPrice(15000L);
        entity1.addTagList(tag);
        entity1.setSeller(1L);
        fixturesBeforeSaving.add(entity1);

        Item entity2 = new Item();
        entity2.setName("GPU 1080");
        entity2.setPrice(10000L);
        fixturesBeforeSaving.add(entity2);

        // save all
        Iterable<Item> itemsSaved = itemRepository.saveAll(fixturesBeforeSaving);
        fixtures.addAll(StreamSupport.stream(itemsSaved.spliterator(), false).toList());
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
    @DisplayName("[정상 구동][findByKeyword] keyword를 이용해서 검색을 시도할 때")
    void givenKeyword_whenCallFindByKeyword_thenGivePageOfItem() {
        // given
        String keyword = "4080";
        PageRequest page = PageRequest.of(0, 10);

        // when
        Page<Item> hits = itemRepository.findByKeyword(keyword, page);

        // then
        hits.map(Item::toString).forEach(log::debug);
        hits.map(Item::getName).forEach(name -> assertThat(name).contains(keyword));
    }
}