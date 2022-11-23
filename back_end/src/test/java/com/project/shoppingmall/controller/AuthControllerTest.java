package com.project.shoppingmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.model.request.LoginRequest;
import com.project.shoppingmall.model.response.LoginResponse;
import com.project.shoppingmall.service.AuthService;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.utils.EnableProjectSecurityConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AuthController Controller Test")
@EnableProjectSecurityConfiguration
@WithAnonymousUser
@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private AuthService authService;
    private final String prefix = "/api/v1/auth";
    private final LoginRequest loginFixture = new LoginRequest();

    @Test
    @DisplayName("[정상 작동][post][/api/v1/auth] 로그인 성공 시, 토큰 발급")
    public void givenExistedAccount_whenCallDoLogin_thenReturnToken() throws Exception {
        // given
        given(authService.doLogin(any(LoginRequest.class)))
                .willReturn(LoginResponse.builder().token("mock token").build());

        // when
        RequestBuilder request = post(prefix)
                .content(objectMapper.writeValueAsBytes(loginFixture))
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("[비정상 작동][post][/api/v1/auth] 허구 계정으로 로그인 시, 에러 발생")
    public void givenNotExistedAccount_whenCallDoLogin_thenThrowError() throws Exception {
        // given
        ErrorCode code = ErrorCode.ACCOUNT_NOT_FOUNDED;

        given(authService.doLogin(any(LoginRequest.class)))
                .willThrow(new AuthenticationException(code));

        // when
        RequestBuilder request = post(prefix)
                .content(objectMapper.writeValueAsBytes(loginFixture))
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().is(code.getStatus().value()));

    }

    @Test
    @DisplayName("[비정상 작동][post][/api/v1/auth] 틀린 비밀번호로 로그인 시, 에러 발생")
    public void givenWrongPassword_whenCallDoLogin_thenThrowError() throws Exception {
        // given
        ErrorCode code = ErrorCode.WRONG_PASSWORD;

        given(authService.doLogin(any(LoginRequest.class)))
                .willThrow(new AuthenticationException(code));

        // when
        RequestBuilder request = post(prefix)
                .content(objectMapper.writeValueAsBytes(loginFixture))
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().is(code.getStatus().value()));

    }
}