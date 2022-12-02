package com.project.shoppingmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.request.SellerCreateRequest;
import com.project.shoppingmall.model.request.SellerUpdateRequest;
import com.project.shoppingmall.model.response.SellerReadResponse;
import com.project.shoppingmall.service.SellerService;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.utils.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Stream;

import static com.project.shoppingmall.utils.JsonPathUtil.makeBaseJsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("SellerController Controller Test")
@EnableProjectSecurityConfiguration
@SetProfile
@WithAnonymousUser
@WebMvcTest(SellerController.class)
class SellerControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private SellerService sellerService;
    private final String prefix = UrlPrefixManager.addPrefix("/seller");

    private String addUid(Long uid) {
        return String.format("%s/%s", prefix, uid);
    }

    private String addPrincipal() {
        return String.format("%s/%s", prefix, "principal");
    }

    @Test
    @DisplayName("[정상 작동][post][/api/v1/seller] 계정 생성")
    public void givenNotExistedAccount_whenCallCreateUser_thenSave() throws Exception {
        // given
        SellerCreateRequest createFixture = FixtureFactory.sellerCreateRequestFixture();

        // when
        RequestBuilder request = post(prefix)
                .content(objectMapper.writeValueAsBytes(createFixture))
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("[비정상 작동][post][/api/v1/seller] 기존에 존재하는 이메일로 계정 생성 시, 에러 발생")
    public void givenExistedAccount_whenCallCreateUser_thenThrowError() throws Exception {
        // given
        ErrorCode code = ErrorCode.ACCOUNT_ALREADY_EXISTED;

        SellerCreateRequest createFixture = FixtureFactory.sellerCreateRequestFixture();
        willThrow(new CrudException(code))
                .given(sellerService).create(any(SellerCreateRequest.class));

        // when
        RequestBuilder request = post(prefix)
                .content(objectMapper.writeValueAsBytes(createFixture))
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().is(code.getStatus().value()));

    }

    private static Stream<Arguments> provideInappropriateParametersSellerCreateRequest() {
        return FixtureFactory.provideInappropriateParametersSellerCreateRequest();
    }

    @ParameterizedTest
    @MethodSource("provideInappropriateParametersSellerCreateRequest")
    @DisplayName("[비정상 작동][post][/api/v1/seller] 부적절한 형식의 칼럼으로 계정 생성 시, 에러 발생")
    public void givenAbnormalParameters_whenCallCreateUser_thenThrowError(
            String email, String password, String name, String phoneNum, String companyName
    ) throws Exception {
        // given
        SellerCreateRequest createFixture = SellerCreateRequest.of(
                email, password, name, phoneNum, companyName
        );

        // when
        RequestBuilder request = post(prefix)
                .content(objectMapper.writeValueAsBytes(createFixture))
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    @WithMockAdmin
    @DisplayName("[정상 작동][get][/api/v1/seller/:id] 계정 단일 조회")
    public void givenUid_whenCallReadUser_thenReturnUser() throws Exception {
        // given
        Long uid = 1L;
        SellerReadResponse response = new SellerReadResponse();

        given(sellerService.read(anyLong())).willReturn(response);

        // when
        RequestBuilder request = get(addUid(uid));

        // then
        ResultActions actions = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

        actions.andExpect(jsonPath(makeBaseJsonPath("id")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("createdAt")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("email")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("name")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("phoneNum")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("companyName")).hasJsonPath());
    }

    @Test
    @WithMockAdmin
    @DisplayName("[정상 작동][put][/api/v1/seller/:id] 계정 수정")
    public void givenUidAndRequest_whenCallUpdateUser_thenUpdate() throws Exception {
        // given
        Long uid = 1L;
        SellerUpdateRequest dto = new SellerUpdateRequest();

        // when
        RequestBuilder request = put(addUid(uid))
                .content(objectMapper.writeValueAsBytes(dto))
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockAdmin
    @DisplayName("[비정상 작동][put][/api/v1/seller/:id] 존재하지 않는 계정 수정")
    public void givenUidNotExistingAndRequest_whenCallUpdateUser_thenUpdate() throws Exception {
        // given
        ErrorCode code = ErrorCode.ACCOUNT_NOT_FOUNDED;

        Long uid = 1L;
        SellerUpdateRequest dto = new SellerUpdateRequest();

        willThrow(new CrudException(code))
                .given(sellerService).update(anyLong(), any(SellerUpdateRequest.class));

        // when
        RequestBuilder request = put(addUid(uid))
                .content(objectMapper.writeValueAsBytes(dto))
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().is(code.getStatus().value()));
    }

    @Test
    @WithMockAdmin
    @DisplayName("[정상 작동][delete][/api/v1/seller/:id] 계정 삭제")
    public void givenUid_whenCallDeleteUser_thenDelete() throws Exception {
        // given
        Long uid = 1L;

        // when
        RequestBuilder request = delete(addUid(uid));

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockAdmin
    @DisplayName("[비정상 작동][delete][/api/v1/seller/:id] 존재하지 않는 계정 삭제")
    public void givenUidNotExisting_whenCallDeleteUser_thenDelete() throws Exception {
        // given
        ErrorCode code = ErrorCode.ACCOUNT_ALREADY_EXISTED;

        Long uid = 1L;

        willThrow(new CrudException(code))
                .given(sellerService).delete(uid);

        // when
        RequestBuilder request = delete(addUid(uid));

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().is(code.getStatus().value()));
    }

    @Test
    @WithAuthenticationPrincipal(role = "SELLER")
    @DisplayName("[정상 작동][get][/api/v1/seller/principal] 계정 단일 조회")
    public void givenPrincipal_whenCallReadUser_thenReturnUser() throws Exception {
        // given

        // when
        RequestBuilder request = get(addPrincipal());

        // then
        ResultActions actions = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

        actions.andExpect(jsonPath(makeBaseJsonPath("id")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("createdAt")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("email")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("name")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("phoneNum")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("companyName")).hasJsonPath());
    }

    @Test
    @WithAuthenticationPrincipal(role = "SELLER")
    @DisplayName("[정상 작동][put][/api/v1/seller/principal] 계정 수정")
    public void givenPrincipal_whenCallUpdateUser_thenUpdate() throws Exception {
        // given
        SellerUpdateRequest dto = new SellerUpdateRequest();

        // when
        RequestBuilder request = put(addPrincipal())
                .content(objectMapper.writeValueAsBytes(dto))
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAuthenticationPrincipal(role = "SELLER")
    @DisplayName("[정상 작동][delete][/api/v1/seller/principal] 계정 삭제")
    public void givenPrincipal_whenCallDeleteUser_thenDelete() throws Exception {
        // given

        // when
        RequestBuilder request = delete(addPrincipal());

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }
}