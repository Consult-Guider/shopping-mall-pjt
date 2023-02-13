package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.HandledItem;
import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.User;
import com.project.shoppingmall.domain.nested.Option;
import com.project.shoppingmall.type.ProcessType;
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
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
    @DisplayName("[정상 구동][save] HandledItem 엔티티가 오류없이 정상작동하는지 확인.")
    void givenHandledItemEntity_whenSaveEntity_thenSaveEntity() {
        // given
        Item item = Item.builder().id("mock item id").build();
        Option option = Option.builder().name("mock option name").build();
        User user = FixtureFactory.userDtoFixture.toEntity();

        HandledItem handledItem = HandledItem.builder()
                .item(item)
                .user(user)
                .ProcessType(ProcessType.CANCEL)
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

    @Test
    @DisplayName("[정상 구동][findXxxAs] 특정 상태의 데이터를 조회.")
    void givenEntities_whenFindXxxAs_thenReturnEntities() {
        // given
        List<HandledItem> entities = new ArrayList<>();
        entities.add(HandledItem.builder()
                .ProcessType(ProcessType.READY)
                .build());
        entities.add(HandledItem.builder()
                .ProcessType(ProcessType.CANCEL)
                .build());
        entities.add(HandledItem.builder()
                .ProcessType(ProcessType.DONE)
                .build());
        entities.add(HandledItem.builder()
                .ProcessType(ProcessType.READY)
                .build());
        entities.add(HandledItem.builder()
                .ProcessType(ProcessType.CANCEL)
                .build());
        entities.add(HandledItem.builder()
                .ProcessType(ProcessType.DONE)
                .build());
        Iterable<HandledItem> entitiesSaved = handledItemRepository.saveAll(entities);

        ProcessType processType = ProcessType.DONE;

        // when
        Page<HandledItem> entitiesSearched = handledItemRepository.findXxxAs(
                processType,
                Pageable.unpaged()
        );
        handledItemRepository.deleteAll(entitiesSaved);

        // then
        entitiesSearched.forEach(handledItem -> {
            log.debug(
                    "검색된 데이터의 ProcessType: {}",
                    handledItem.getProcessType()
            );
            assertThat(handledItem.getProcessType()).isEqualTo(processType);
        });
    }

    @Test
    @DisplayName("[정상 구동][countDeliveryByProcessType] 특정 상태의 데이터의 갯수를 셈하여 출력.")
    void givenEntities_whenCountDeliveryByProcessType_thenReturnCountOfEntities() {
        // given
        List<HandledItem> entities = new ArrayList<>();
        entities.add(HandledItem.builder()
                .ProcessType(ProcessType.READY)
                .build());
        entities.add(HandledItem.builder()
                .ProcessType(ProcessType.CANCEL)
                .build());
        entities.add(HandledItem.builder()
                .ProcessType(ProcessType.DONE)
                .build());
        entities.add(HandledItem.builder()
                .ProcessType(ProcessType.READY)
                .build());
        entities.add(HandledItem.builder()
                .ProcessType(ProcessType.CANCEL)
                .build());
        entities.add(HandledItem.builder()
                .ProcessType(ProcessType.DONE)
                .build());
        Iterable<HandledItem> entitiesSaved = handledItemRepository.saveAll(entities);

        // when
        Map<String, Long> mapCount = handledItemRepository.countPaymentByProcessType();
        handledItemRepository.deleteAll(entitiesSaved);

        // then
        mapCount.forEach((s, aLong) -> {
            log.debug("{}: {}", s, aLong);
        });
    }
}