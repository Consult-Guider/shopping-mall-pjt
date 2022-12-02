package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.Admin;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class AdminUpdateRequestTest {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("[정상 작동][overwrite] 기존 데이터를 덮어씌우는지 체크")
    void givenEntityAndRequest_whenCallOverwrite_thenOverwriteEntity() {
        // given
        Admin entity = new Admin();

        AdminUpdateRequest request = new AdminUpdateRequest();
        request.setPassword("mock password");
        request.setName("mock name");
        request.setPhoneNum("mock changed phoneNum");

        // when
        Admin entityOverwritten = AdminUpdateRequest.overwrite(entity, request, passwordEncoder);

        // then
        assertTrue(passwordEncoder.matches("mock password", entityOverwritten.getPassword()));
        assertThat(entityOverwritten.getPhoneNum()).isEqualTo("mock changed phoneNum");
        assertThat(entityOverwritten.getName()).isEqualTo("mock name");

        log.debug(entityOverwritten.toString());
    }

    @Test
    @DisplayName("[정상 작동][overwrite] null값을 덮어씌울 때, 기존 데이터를 유지하는지 체크")
    void givenEntityAndNullRequest_whenCallOverwrite_thenKeepEntity() {
        // given
        Admin entity = new Admin();
        entity.setPassword(passwordEncoder.encode("mock password"));
        entity.setName("mock name");
        entity.setPhoneNum("mock changed phoneNum");

        AdminUpdateRequest request = new AdminUpdateRequest();

        // when
        Admin entityOverwritten = AdminUpdateRequest.overwrite(entity, request, passwordEncoder);

        // then
        assertTrue(passwordEncoder.matches("mock password", entityOverwritten.getPassword()));
        assertThat(entityOverwritten.getPhoneNum()).isEqualTo("mock changed phoneNum");
        assertThat(entityOverwritten.getName()).isEqualTo("mock name");

        log.debug(entityOverwritten.toString());
    }

    @Test
    @DisplayName("[정상 작동][overwrite] 빈 String값을 덮어씌울 때, 기존 데이터를 유지하는지 체크")
    void givenEntityAndBlankRequest_whenCallOverwrite_thenKeepEntity() {
        // given
        Admin entity = new Admin();
        entity.setPassword(passwordEncoder.encode("mock password"));
        entity.setName("mock name");
        entity.setPhoneNum("mock changed phoneNum");

        AdminUpdateRequest request = new AdminUpdateRequest();
        request.setPassword(" ");
        request.setName(" ");
        request.setPhoneNum(" ");

        // when
        Admin entityOverwritten = AdminUpdateRequest.overwrite(entity, request, passwordEncoder);

        // then
        assertTrue(passwordEncoder.matches("mock password", entityOverwritten.getPassword()));
        assertThat(entityOverwritten.getPhoneNum()).isEqualTo("mock changed phoneNum");
        assertThat(entityOverwritten.getName()).isEqualTo("mock name");

        log.debug(entityOverwritten.toString());
    }
}