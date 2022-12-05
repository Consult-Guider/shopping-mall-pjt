package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Seller;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.AdminDto;
import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.SellerDto;
import com.project.shoppingmall.repository.ItemRepository;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Slf4j
@MockitoSettings(strictness = Strictness.LENIENT)
@SetProfile
@DisplayName("Item Service Test")
@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Test
    @DisplayName("[정상 작동][loadItemById] ID를 이용한 Item Entity 호출")
    void givenExistedIid_whenLoadItemById_thenReturnEntity() {
        // given
        String iid = "mock id";
        given(itemRepository.findById(iid)).willReturn(Optional.of(new Item()));

        // when
        Item entity = itemService.loadItemById(iid);

        // then
        assertThat(entity).isNotNull();
    }

    @Test
    @DisplayName("[비정상 작동][loadItemById] 존재하지 않는 ID를 이용한 Item Entity 호출 시, 오류 발생.")
    void givenNonExistedIid_whenLoadItemById_thenThrowError() {
        // given
        String iid = "mock id";
        ErrorCode code = ErrorCode.ITEM_NOT_FOUNDED;

        given(itemRepository.findById(iid))
                .willThrow(new CrudException(code));

        // when & then
        CrudException e = assertThrows(CrudException.class, () -> itemService.loadItemById(iid));
        assertThat(e.getErrorCode()).isEqualTo(code);
    }

    @Test
    @DisplayName("[정상 작동][loadSellerById] ID를 이용한 Seller Entity 호출")
    void givenExistedUid_whenLoadSellerById_thenReturnEntity() {
        // given
        Long uid = 1L;
        given(sellerRepository.findById(uid)).willReturn(Optional.of(new Seller()));

        // when
        Seller entity = itemService.loadSellerById(uid);

        // then
        assertThat(entity).isNotNull();
    }

    @Test
    @DisplayName("[비정상 작동][loadSellerById] 존재하지 않는 ID를 이용한 Seller Entity 호출 시, 오류 발생.")
    void givenNonExistedIid_whenLoadSellerById_thenThrowError() {
        // given
        Long uid = 1L;
        ErrorCode code = ErrorCode.ACCOUNT_NOT_FOUNDED;

        given(sellerRepository.findById(uid))
                .willThrow(new CrudException(code));

        // when & then
        CrudException e = assertThrows(CrudException.class, () -> itemService.loadSellerById(uid));
        assertThat(e.getErrorCode()).isEqualTo(code);
    }

    @Test
    @DisplayName("[정상 작동][assertPossession] 판매자가 본인이 생성한 상품에 접근 가능한지 확인")
    void givenLoginDtoAndIid_whenAssertPossession_thenDoNothing() {
        // given
        LoginDto principal = SellerDto.builder().id(1L).build();
        String iid = "mock Id";

        given(itemRepository.findByIdAndSeller(anyString(), anyLong())).willReturn(Optional.of(new Item()));
        
        // when & then
        assertDoesNotThrow(() -> itemService.assertPossession(principal, iid));
    }

    @Test
    @DisplayName("[정상 작동][assertPossession] 운영자가 본인이 생성하지 않은 상품에 접근 가능한지 확인")
    void givenAdminAccount_whenAssertPossession_thenDoNothing() {
        // given
        LoginDto principal = AdminDto.builder().id(1L).build();
        String iid = "mock Id";

        ErrorCode code = ErrorCode.NO_OWNERSHIP;

        given(itemRepository.findByIdAndSeller(anyString(), anyLong()))
                .willThrow(new CrudException(code));

        // when & then
        assertDoesNotThrow(() -> itemService.assertPossession(principal, iid));
    }

    @Test
    @DisplayName("[비정상 작동][assertPossession] 판매자가 본인이 생성하지 않은 상품에 접근 불가능한지 확인")
    void givenLoginDtoAndIidNotBelonging_whenAssertPossession_thenThrowError() {
        // given
        LoginDto principal = SellerDto.builder().id(1L).build();
        String iid = "mock Id";

        ErrorCode code = ErrorCode.NO_OWNERSHIP;

        given(itemRepository.findByIdAndSeller(anyString(), anyLong()))
                .willThrow(new CrudException(code));

        // when & then
        CrudException e = assertThrows(CrudException.class, () -> itemService.assertPossession(principal, iid));
        assertThat(e.getErrorCode()).isEqualTo(code);
    }
}