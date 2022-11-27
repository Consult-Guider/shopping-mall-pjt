package com.project.shoppingmall.service;

import com.project.shoppingmall.config.properties.JwtProperties;
import com.project.shoppingmall.domain.LoginEntity;
import com.project.shoppingmall.domain.User;
import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.model.request.LoginRequest;
import com.project.shoppingmall.model.response.LoginResponse;
import com.project.shoppingmall.repository.RepositoryFactory;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.type.RoleType;
import com.project.shoppingmall.utils.SetProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;

@MockitoSettings(strictness = Strictness.LENIENT)
@Slf4j
@SetProfile
@DisplayName("Auth Service Test")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private RepositoryFactory repositoryFactory;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtProperties jwtProperties;

    private LoginRequest makeRequestFixture(String email, String password) {
        LoginRequest loginRequestFixture = new LoginRequest();
        loginRequestFixture.setRole(RoleType.USER);
        loginRequestFixture.setEmail(email);
        loginRequestFixture.setPassword(password);
        return loginRequestFixture;
    }

    private LoginEntity makeEntityFixture(String email, String password) {
        LoginEntity loginEntityFixture = new User();
        loginEntityFixture.setEmail(email);
        loginEntityFixture.setPassword(
                passwordEncoder.encode(password)
        );
        return loginEntityFixture;
    }

    @BeforeEach
    void beforeEach() {
        given(passwordEncoder.encode(anyString()))
                .willReturn("mock encoded password");
        given(jwtProperties.getSecretKey())
                .willReturn("mock secretKey.mock secretKey.mock secretKey.");
        given(jwtProperties.getExpiredTimeMs())
                .willReturn(10000L);
    }

    @Test
    @DisplayName("[정상 작동][doLogin] 로그인 시, 정상 작동.")
    void givenExistedAccount_whenCallDoLogin_thenReturnToken() {
        // given
        String email = "mock login";
        String password = "mock password";

        LoginRequest loginRequestFixture = makeRequestFixture(email, password);
        LoginEntity loginEntityFixture = makeEntityFixture(email, password);

        willReturn(Optional.of(loginEntityFixture))
                .given(repositoryFactory).getFinderByEmail(any(RoleType.class), anyString());
        given(passwordEncoder.matches(anyString(), anyString()))
                .willReturn(Boolean.TRUE);
        // when
        LoginResponse loginResponse = authService.doLogin(loginRequestFixture);

        // then
        assertThat(loginResponse.getToken()).isNotNull();
        log.debug(loginResponse.getToken());

    }

    @Test
    @DisplayName("[비정상 작동][doLogin] 허구 계정으로 로그인 시, 오류 발생.")
    void givenNonExistedAccount_whenCallDoLogin_thenThrowError() {
        // given
        String email = "mock login";
        String emailNotExisted = "mock wrong password";
        String password = "mock password";

        LoginRequest loginRequestFixture = makeRequestFixture(emailNotExisted, password);

        willReturn(Optional.empty())
                .given(repositoryFactory).getFinderByEmail(any(RoleType.class), anyString());
        given(passwordEncoder.matches(anyString(), anyString()))
                .willReturn(Boolean.TRUE);

        // when & then
        AuthenticationException e = assertThrows(AuthenticationException.class,
                () -> authService.doLogin(loginRequestFixture)
        );
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.ACCOUNT_NOT_FOUNDED);
        log.debug(e.printErrorMessage());

    }

    @Test
    @DisplayName("[비정상 작동][doLogin] 틀린 비밀번호로 로그인 시, 오류 발생.")
    void givenWrongPassword_whenCallDoLogin_thenThrowError() {
        // given
        String email = "mock login";
        String password = "mock password";
        String passwordWrong = "mock wrong password";

        LoginRequest loginRequestFixture = makeRequestFixture(email, passwordWrong);
        LoginEntity loginEntityFixture = makeEntityFixture(email, password);

        willReturn(Optional.of(loginEntityFixture))
                .given(repositoryFactory).getFinderByEmail(any(RoleType.class), anyString());
        given(passwordEncoder.matches(anyString(), anyString()))
                .willReturn(Boolean.FALSE);

        // when & then
        AuthenticationException e = assertThrows(AuthenticationException.class,
                () -> authService.doLogin(loginRequestFixture)
        );
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.WRONG_PASSWORD);
        log.debug(e.printErrorMessage());

    }
}