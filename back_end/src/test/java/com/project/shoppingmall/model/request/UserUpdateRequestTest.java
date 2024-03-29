package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class UserUpdateRequestTest {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("[정상 작동][overwrite] 기존 데이터를 덮어씌우는지 체크")
    void givenEntityAndRequest_whenCallOverwrite_thenOverwriteEntity() {
        // given
        User entity = new User();

        UserUpdateRequest request = new UserUpdateRequest();
        request.setPassword("mock password");
        request.setName("mock name");
        request.setPhoneNum("mock changed phoneNum");
        request.setAddress("mock address");

        // when
        User entityOverwritten = request.overwrite(entity, passwordEncoder);

        // then
        assertTrue(passwordEncoder.matches("mock password", entityOverwritten.getPassword()));
        assertThat(entityOverwritten.getPhoneNum()).isEqualTo("mock changed phoneNum");
        assertThat(entityOverwritten.getName()).isEqualTo("mock name");
        assertThat(entityOverwritten.getAddress()).isEqualTo("mock address");

        log.debug(entityOverwritten.toString());
    }

    @Test
    @DisplayName("[정상 작동][overwrite] null값을 덮어씌울 때, 기존 데이터를 유지하는지 체크")
    void givenEntityAndNullRequest_whenCallOverwrite_thenKeepEntity() {
        // given
        User entity = new User();
        entity.setPassword(passwordEncoder.encode("mock password"));
        entity.setName("mock name");
        entity.setPhoneNum("mock changed phoneNum");
        entity.setAddress("mock address");

        UserUpdateRequest request = new UserUpdateRequest();

        // when
        User entityOverwritten = request.overwrite(entity, passwordEncoder);

        // then
        assertTrue(passwordEncoder.matches("mock password", entityOverwritten.getPassword()));
        assertThat(entityOverwritten.getPhoneNum()).isEqualTo("mock changed phoneNum");
        assertThat(entityOverwritten.getName()).isEqualTo("mock name");
        assertThat(entityOverwritten.getAddress()).isEqualTo("mock address");

        log.debug(entityOverwritten.toString());
    }

    @Test
    @DisplayName("[정상 작동][overwrite] 빈 String값을 덮어씌울 때, 기존 데이터를 유지하는지 체크")
    void givenEntityAndBlankRequest_whenCallOverwrite_thenKeepEntity() {
        // given
        User entity = new User();
        entity.setPassword(passwordEncoder.encode("mock password"));
        entity.setName("mock name");
        entity.setPhoneNum("mock changed phoneNum");
        entity.setAddress("mock address");

        UserUpdateRequest request = new UserUpdateRequest();
        request.setPassword(" ");
        request.setName(" ");
        request.setPhoneNum(" ");
        request.setAddress(" ");

        // when
        User entityOverwritten = request.overwrite(entity, passwordEncoder);

        // then
        assertTrue(passwordEncoder.matches("mock password", entityOverwritten.getPassword()));
        assertThat(entityOverwritten.getPhoneNum()).isEqualTo("mock changed phoneNum");
        assertThat(entityOverwritten.getName()).isEqualTo("mock name");
        assertThat(entityOverwritten.getAddress()).isEqualTo("mock address");

        log.debug(entityOverwritten.toString());
    }
}