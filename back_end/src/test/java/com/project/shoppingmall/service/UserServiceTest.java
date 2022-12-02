package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.User;
import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.repository.UserRepository;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.utils.SetProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Slf4j
@SetProfile
@DisplayName("User Service Test")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("[정상 작동][loadUserByUsername] 이메일을 이용한 UserDetail 호출")
    void givenExistedEmail_whenCallLoadUserByUsername_thenReturnUserDetail() {
        // given
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(mock(User.class)));
        
        // when
        UserDetails mockDto = userService.loadUserByUsername("mock email");

        // then
        assertThat(mockDto).isNotNull();
    }

    @Test
    @DisplayName("[비정상 작동][loadUserByUsername] 이메일을 이용한 UserDetail 호출 시, 조회 불가")
    void givenNotExistedEmail_whenCallLoadUserByUsername_thenThrowNotFounded() {
        // given
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());

        // when & then
        AuthenticationException e = assertThrows(AuthenticationException.class,
                () -> userService.loadUserByUsername("mock email")
                );
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.ACCOUNT_NOT_FOUNDED);
        log.debug(e.printErrorMessage());
    }

    @Test
    @DisplayName("[정상 작동][isThereEmailSimilarWith] 이메일을 이용한 검색 시, 조회 불가")
    void givenNotExistedEmail_whenCallIsThereEmailSimilarWith_thenThrowNothing() {
        // given
        String email = "mock email";

        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());
        
        // when & then
        assertDoesNotThrow(() -> userService.isThereEmailEqualTo(email));
    }

    @Test
    @DisplayName("[비정상 작동][isThereEmailSimilarWith] 이메일을 이용한 검색 시, 조회 가능")
    void givenExistedEmail_whenCallIsThereEmailSimilarWith_thenThrowError() {
        // given
        String email = "mock email";

        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(new User()));

        // when & then
        CrudException e =
                assertThrows(CrudException.class, () -> userService.isThereEmailEqualTo(email));
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.ACCOUNT_ALREADY_EXISTED);
    }

    @Test
    @DisplayName("[정상 작동][loadUserById] 아이디를 이용한 검색 시, 조회 가능")
    void givenExistedEmail_whenCallLoadUserById_thenThrowNothing() {
        // given
        Long id = 1L;

        given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(new User()));

        // when & then
        assertDoesNotThrow(() -> userService.loadUserById(id));
    }

    @Test
    @DisplayName("[비정상 작동][loadUserById] 아이디를 이용한 검색 시, 조회 불가")
    void givenNotExistedEmail_whenCallLoadUserById_thenThrowError() {
        // given
        Long id = 1L;

        given(userRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when & then
        CrudException e =
                assertThrows(CrudException.class, () -> userService.loadUserById(id));
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.ACCOUNT_NOT_FOUNDED);
    }
}