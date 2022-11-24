package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Admin;
import com.project.shoppingmall.utils.SetProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("Admin Repository Test")
@SetProfile
@DataJpaTest
class AdminRepositoryTest {
    @Autowired
    AdminRepository adminRepository;

    @Test
    @DisplayName("[실존 객체 조회][findByEmail] 이메일을 이용한 엔티티 호출")
    void givenExistedEmail_whenCallFindByEmail_thenReturnEntity() {
        // given
        String email = "iksadeast@gmail.com";

        // when
        Optional<Admin> entity = adminRepository.findByEmail(email);

        // then
        assertThat(entity.isPresent()).isTrue();
        entity.map(Object::toString).ifPresent(log::debug);
    }

    @Test
    @DisplayName("[허구 객체 조회][findByEmail] 이메일을 이용한 엔티티 호출")
    void givenNotExistedEmail_whenCallFindByEmail_thenReturnEmpty() {
        // given
        String email = "iksadeast2@gmail.com";

        // when
        Optional<Admin> entity = adminRepository.findByEmail(email);

        // then
        assertThat(entity.isPresent()).isFalse();
    }
}