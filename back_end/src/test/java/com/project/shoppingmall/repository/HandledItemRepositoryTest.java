package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.HandledItem;
import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.nested.Option;
import com.project.shoppingmall.type.HandledType;
import com.project.shoppingmall.type.ProcessType;
import com.project.shoppingmall.utils.EnableProjectElasticSearchConfiguration;
import com.project.shoppingmall.utils.EnableProjectQueryDslConfiguration;
import com.project.shoppingmall.utils.SetProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@Slf4j
@DisplayName("HandledItem Repository Test")
@SetProfile
@EnableProjectElasticSearchConfiguration
@EnableProjectQueryDslConfiguration
@DataJpaTest
class HandledItemRepositoryTest {
    @Autowired
    HandledItemRepository handledItemRepository;

    @Test
    void test() {
        // given
        Item item = Item.builder().id("mock item id").build();
        Option option = Option.builder().name("mock option name").build();

        HandledItem handledItem = HandledItem.builder()
                .item(item)
                .userId(1L)
                .handledType(HandledType.PURCHASE)
                .ProcessType(ProcessType.ONGOING)
                .count(2L)
                .optionList(List.of(option))
                .build();

        HandledItem handledItemSaved = handledItemRepository.save(handledItem);

        // when
        Iterable<HandledItem> entities = handledItemRepository.findAll();
        handledItemRepository.delete(handledItemSaved);

        // then
        for(HandledItem i : entities) {
            log.debug(i.toString());
        }
    }
}