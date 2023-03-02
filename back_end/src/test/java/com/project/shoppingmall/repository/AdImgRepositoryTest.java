package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.AdImg;
import com.project.shoppingmall.utils.EnableProjectQueryDslConfiguration;
import com.project.shoppingmall.utils.SetProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("AdImg Repository Test")
@SetProfile
@EnableProjectQueryDslConfiguration
@DataJpaTest
class AdImgRepositoryTest {
    @Autowired
    AdImgRepository adImgRepository;

    @Test
    @DisplayName("[유효 객체 조회][findAllValid] 아직 광고 만료일을 지나지 않은 엔티티 호출.")
    void givenExistedEmail_whenCallFindByEmail_thenReturnEntity() {
        // given
        Pageable page = PageRequest.of(0, 10);

        // when
        Page<AdImg> entity = adImgRepository.findAllValid(page);

        // then
        assertThat(entity.getTotalElements()).isNotZero();
        entity.map(Object::toString).forEach(log::debug);
    }

    @Test
    @DisplayName("[랜덤 객체 조회][findOneRandomly] 광고 유효 기간 내의 임의의 엔티티 호출.")
    void givenNothing_whenCallFindOneRandomly_thenReturnEntity() {
        // given
        List<AdImg> entities = new ArrayList<>();
        boolean result = true;

        // when
        Optional<AdImg> entity = adImgRepository.findOneRandomly();

        entities.add(adImgRepository.findOneRandomly().orElse(null));
        entities.add(adImgRepository.findOneRandomly().orElse(null));
        entities.add(adImgRepository.findOneRandomly().orElse(null));
        entities.add(adImgRepository.findOneRandomly().orElse(null));
        entities.add(adImgRepository.findOneRandomly().orElse(null));

        for(AdImg adImg : entities) {
            result &= entity.map(adImg::equals).orElse(false);
        }

        // then
        assertThat(result).isFalse();
        entity.map(Object::toString).ifPresent(log::debug);
        entities.stream().map(Objects::toString).forEach(log::debug);
    }

}