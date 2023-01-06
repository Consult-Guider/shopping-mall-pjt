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

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Admin Repository Test")
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

}