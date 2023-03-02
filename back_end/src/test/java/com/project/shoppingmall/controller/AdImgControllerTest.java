package com.project.shoppingmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shoppingmall.model.request.AdImgCreateRequest;
import com.project.shoppingmall.model.request.AdImgUpdateRequest;
import com.project.shoppingmall.model.response.AdImgReadResponse;
import com.project.shoppingmall.model.response.AdImgRecommendReadResponse;
import com.project.shoppingmall.service.AdImgService;
import com.project.shoppingmall.utils.EnableProjectSecurityConfiguration;
import com.project.shoppingmall.utils.FixtureFactory;
import com.project.shoppingmall.utils.SetProfile;
import com.project.shoppingmall.utils.UrlPrefixManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.util.MultiValueMapAdapter;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.shoppingmall.utils.JsonPathUtil.makeBaseJsonPath;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AdImgController Controller Test")
@EnableProjectSecurityConfiguration
@SetProfile
@WithAnonymousUser
@WebMvcTest(AdImgController.class)
class AdImgControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private AdImgService adImgService;
    private final String prefix = UrlPrefixManager.addPrefix("/adimg");

    private String addAid(Long aid) {
        return String.format("%s/%s", prefix, aid);
    }

    private Map<String, List<String>> makeParams(AdImgCreateRequest createFixture) {
        Map<String, List<String>> params = new HashMap<>();
        params.put("itemName", List.of(createFixture.getItemName()));
        params.put("companyName", List.of(createFixture.getCompanyName()));
        params.put("link", List.of(createFixture.getLink()));
        params.put("startAt", List.of(createFixture.getStartAt().format(DateTimeFormatter.ISO_DATE_TIME)));
        params.put("endAt", List.of(createFixture.getEndAt().format(DateTimeFormatter.ISO_DATE_TIME)));
        return params;
    }

    private Map<String, List<String>> makeParams(AdImgUpdateRequest updateFixture) {
        Map<String, List<String>> params = new HashMap<>();
        params.put("itemName", List.of(updateFixture.getItemName()));
        params.put("companyName", List.of(updateFixture.getCompanyName()));
        params.put("link", List.of(updateFixture.getLink()));
        params.put("startAt", List.of(updateFixture.getStartAt().format(DateTimeFormatter.ISO_DATE_TIME)));
        params.put("endAt", List.of(updateFixture.getEndAt().format(DateTimeFormatter.ISO_DATE_TIME)));
        return params;
    }

    @Test
    @DisplayName("[정상 작동][post][/api/v1/adimg] 광고 배너 생성")
    public void givenRequest_whenCallCreateAdImg_thenSave() throws Exception {
        // given
        AdImgCreateRequest createFixture = FixtureFactory.adImgCreateRequestFixture();
        Map<String, List<String>> params = makeParams(createFixture);

        // when
        MockMultipartHttpServletRequestBuilder multipart = multipart(HttpMethod.POST, prefix)
                .file((MockMultipartFile) createFixture.getPath());

        RequestBuilder request = multipart
                .params(new MultiValueMapAdapter<>(params))
                .contentType(MediaType.MULTIPART_FORM_DATA);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @ParameterizedTest
    @ValueSource(strings = {"mock wrong link", "http://school.programmers.co.kr/learn/challenges"})
    @DisplayName("[비정상 작동][post][/api/v1/adimg] 광고 배너 생성 시, 잘못된 URL")
    public void givenRequestWithWrongURL_whenCallCreateAdImg_thenThrow400(String link) throws Exception {
        // given
        AdImgCreateRequest createFixture = FixtureFactory.adImgCreateRequestFixture();
        Map<String, List<String>> params = makeParams(createFixture);
        params.put("link", List.of(link));

        // when
        MockMultipartHttpServletRequestBuilder multipart = multipart(HttpMethod.POST, prefix)
                .file((MockMultipartFile) createFixture.getPath());

        RequestBuilder request = multipart
                .params(new MultiValueMapAdapter<>(params))
                .contentType(MediaType.MULTIPART_FORM_DATA);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @ParameterizedTest
    @ValueSource(strings = {"2023/01/01", "2023-01-01 00:00:00"})
    @DisplayName("[비정상 작동][post][/api/v1/adimg] 광고 배너 생성 시, 잘못된 날짜 형식")
    public void givenRequestWithWrongDateTimeFormat_whenCallCreateAdImg_thenThrow400(String datetime) throws Exception {
        // given
        AdImgCreateRequest createFixture = FixtureFactory.adImgCreateRequestFixture();
        Map<String, List<String>> params = makeParams(createFixture);
        params.put("startAt", List.of(datetime));
        params.put("endAt", List.of(datetime));

        // when
        MockMultipartHttpServletRequestBuilder multipart = multipart(HttpMethod.POST, prefix)
                .file((MockMultipartFile) createFixture.getPath());

        RequestBuilder request = multipart
                .params(new MultiValueMapAdapter<>(params))
                .contentType(MediaType.MULTIPART_FORM_DATA);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("[정상 작동][get][/api/v1/adimg/:id] 광고 배너 단일 조회")
    public void givenAid_whenCallRead_thenReturnAdImgResponse() throws Exception {
        // given
        Long aid = 1L;
        AdImgReadResponse response = new AdImgReadResponse();

        given(adImgService.read(anyLong())).willReturn(response);

        // when
        RequestBuilder request = get(addAid(aid));

        // then
        ResultActions actions = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

        actions.andExpect(jsonPath(makeBaseJsonPath("id")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("itemName")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("companyName")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("path")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("link")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("startAt")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("endAt")).hasJsonPath());
    }

    @Test
    @DisplayName("[정상 작동][post][/api/v1/adimg/:id] 광고 배너 수정")
    public void givenAidAndRequest_whenCallUpdate_thenUpdate() throws Exception {
        // given
        Long aid = 1L;
        AdImgUpdateRequest updateFixture = FixtureFactory.adImgUpdateRequestFixture();
        Map<String, List<String>> params = makeParams(updateFixture);

        // when
        MockMultipartHttpServletRequestBuilder multipart = multipart(HttpMethod.POST, addAid(aid))
                .file((MockMultipartFile) updateFixture.getPath());

        RequestBuilder request = multipart
                .params(new MultiValueMapAdapter<>(params))
                .contentType(MediaType.MULTIPART_FORM_DATA);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"mock wrong link", "http://school.programmers.co.kr/learn/challenges"})
    @DisplayName("[비정상 작동][post][/api/v1/adimg/:id] 광고 배너 수정 시, 잘못된 link 형식.")
    public void givenAidAndRequestWithWrongLink_whenCallUpdate_thenThrow400(String link) throws Exception {
        // given
        Long aid = 1L;
        AdImgUpdateRequest updateFixture = FixtureFactory.adImgUpdateRequestFixture();
        Map<String, List<String>> params = makeParams(updateFixture);
        params.put("link", List.of(link));

        // when
        MockMultipartHttpServletRequestBuilder multipart = multipart(HttpMethod.POST, addAid(aid))
                .file((MockMultipartFile) updateFixture.getPath());

        RequestBuilder request = multipart
                .params(new MultiValueMapAdapter<>(params))
                .contentType(MediaType.MULTIPART_FORM_DATA);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"2023/01/01", "2023-01-01 00:00:00"})
    @DisplayName("[비정상 작동][post][/api/v1/adimg/:id] 광고 배너 수정 시, 잘못된 날짜 형식.")
    public void givenAidAndRequestWithWrongDateTimeFormat_whenCallUpdate_thenThrow400(String datetime) throws Exception {
        // given
        Long aid = 1L;
        AdImgUpdateRequest updateFixture = FixtureFactory.adImgUpdateRequestFixture();
        Map<String, List<String>> params = makeParams(updateFixture);
        params.put("startAt", List.of(datetime));
        params.put("endAt", List.of(datetime));

        // when
        MockMultipartHttpServletRequestBuilder multipart = multipart(HttpMethod.POST, addAid(aid))
                .file((MockMultipartFile) updateFixture.getPath());

        RequestBuilder request = multipart
                .params(new MultiValueMapAdapter<>(params))
                .contentType(MediaType.MULTIPART_FORM_DATA);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[정상 작동][delete][/api/v1/adimg/:id] 광고 배너 삭제")
    public void givenAid_whenCallDelete_thenDelete() throws Exception {
        // given
        Long aid = 1L;

        // when
        RequestBuilder request = delete(addAid(aid));

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[정상 작동][get][/api/v1/adimg] 광고 배너 일괄 조회")
    public void givenPage_whenCallRead_thenPageOfResponse() throws Exception {
        // given
        Pageable page = PageRequest.of(0, 10);

        // when
        RequestBuilder request = get(prefix)
                .param("page", String.valueOf(page.getPageNumber()))
                .param("size", String.valueOf(page.getPageSize()));

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[정상 작동][get][/api/v1/adimg/recommend] 임의의 광고 배너 호출")
    public void givenNothing_whenCallReadRecommend_thenReturnAdImgRecommendResponse() throws Exception {
        // given
        AdImgRecommendReadResponse response = new AdImgRecommendReadResponse();

        given(adImgService.readRecommend()).willReturn(response);

        // when
        RequestBuilder request = get(prefix + "/recommend");

        // then
        ResultActions actions = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

        actions.andExpect(jsonPath(makeBaseJsonPath("path")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("link")).hasJsonPath());
    }
}
