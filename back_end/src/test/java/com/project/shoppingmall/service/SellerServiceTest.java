package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.Seller;
import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.repository.SellerRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Slf4j
@SetProfile
@DisplayName("Seller Service Test")
@ExtendWith(MockitoExtension.class)
class SellerServiceTest {
    @InjectMocks
    private SellerService sellerService;

    @Mock
    private SellerRepository sellerRepository;

    @Test
    @DisplayName("[정상 작동][loadUserByUsername] 이메일을 이용한 UserDetail 호출")
    void givenExistedEmail_whenCallLoadUserByUsername_thenReturnUserDetail() {
        // given
        given(sellerRepository.findByEmail(anyString()))
                .willReturn(Optional.of(mock(Seller.class)));
        
        // when
        UserDetails mockDto = sellerService.loadUserByUsername("mock email");

        // then
        assertThat(mockDto).isNotNull();
    }

    @Test
    @DisplayName("[비정상 작동][loadUserByUsername] 이메일을 이용한 UserDetail 호출 시, 조회 불가")
    void givenNotExistedEmail_whenCallLoadUserByUsername_thenThrowNotFounded() {
        // given
        given(sellerRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());

        // when & then
        AuthenticationException e = assertThrows(AuthenticationException.class,
                () -> sellerService.loadUserByUsername("mock email")
                );
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.ACCOUNT_NOT_FOUNDED);
        log.debug(e.printErrorMessage());
    }
}