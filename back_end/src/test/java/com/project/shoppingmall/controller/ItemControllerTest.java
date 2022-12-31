package com.project.shoppingmall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shoppingmall.model.request.ItemCreateRequest;
import com.project.shoppingmall.model.request.ItemUpdateRequest;
import com.project.shoppingmall.model.response.ItemReadResponse;
import com.project.shoppingmall.service.ItemService;
import com.project.shoppingmall.type.RoleType;
import com.project.shoppingmall.utils.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.util.MultiValueMapAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.shoppingmall.utils.JsonPathUtil.makeBaseJsonPath;
import static com.project.shoppingmall.utils.JsonPathUtil.makePathJsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ItemController Controller Test")
@EnableProjectSecurityConfiguration
@SetProfile
@WithAnonymousUser
@WebMvcTest(ItemController.class)
class ItemControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private ItemService itemService;
    private final String prefix = UrlPrefixManager.addPrefix("/item");

    private String addIid(Long iid) {
        return String.format("%s/%s", prefix, iid);
    }

    private String addIidWithAll(Long iid) {
        return String.format("%s/%s/all", prefix, iid);
    }

    private <E> List<String> writeValueAsListOfString(List<E> listOfObject) throws JsonProcessingException {
        List<String> listOfString = new ArrayList<>();
        for (E obj : listOfObject) {
            String value = objectMapper.writeValueAsString(obj);
            listOfString.add(value);
        }
        return listOfString;
    }

    @Test
    @WithAuthenticationPrincipal(role = RoleType.SELLER)
    @DisplayName("[정상 작동][post][/api/v1/item] 상품 생성")
    void createItem() throws Exception {
        // given
        ItemCreateRequest createFixture = FixtureFactory.itemCreateRequestFixture();

        Map<String, List<String>> params = new HashMap<>();
        params.put("name", List.of(createFixture.getName()));
        params.put("price", List.of(createFixture.getPrice().toString()));
        params.put("optionList", writeValueAsListOfString(createFixture.getOptionList()));
        params.put("tagList", writeValueAsListOfString(createFixture.getTagList()));

        // when
        MockMultipartHttpServletRequestBuilder multipart = multipart(HttpMethod.POST, prefix)
                .file((MockMultipartFile) createFixture.getImage());

        createFixture.getDescriptionList().stream()
                .map(MockMultipartFile.class::cast)
                .forEach(multipart::file);

        RequestBuilder request = multipart
                .params(new MultiValueMapAdapter<>(params))
                .contentType(MediaType.MULTIPART_FORM_DATA);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[정상 작동][get][/api/v1/item/:id/all] 상품 단일 조회")
    void readItem() throws Exception {
        // given
        Long uid = 1L;
        ItemReadResponse response = FixtureFactory.itemReadResponseFixture();

        given(itemService.read(anyString())).willReturn(response);

        // when
        RequestBuilder request = get(addIid(uid));

        // then
        ResultActions actions = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

        actions.andExpect(jsonPath(makeBaseJsonPath("id")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("createdAt")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("name")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("price")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("seller.companyName")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("imagePath")).hasJsonPath());
    }

    @Test
    @DisplayName("[정상 작동][get][/api/v1/item/:id/all] 상품 단일 세부 조회")
    void readItemAll() throws Exception {
        // given
        Long uid = 1L;
        ItemReadResponse response = FixtureFactory.itemReadAllResponseFixture();

        given(itemService.readItemAll(anyString())).willReturn(response);

        // when
        RequestBuilder request = get(addIidWithAll(uid));

        // then
        ResultActions actions = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

        actions.andExpect(jsonPath(makeBaseJsonPath("id")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("createdAt")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("name")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("price")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("seller.companyName")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("imagePath")).hasJsonPath());

        actions.andExpect(jsonPath(makeBaseJsonPath("optionList[0].name")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("descriptionList[0].path")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("reviewList[0].content")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("questionList[0].content")).hasJsonPath());
        actions.andExpect(jsonPath(makeBaseJsonPath("tagList[0].name")).hasJsonPath());
    }

    @Test
    @WithAuthenticationPrincipal(role = RoleType.SELLER)
    @DisplayName("[정상 작동][put][/api/v1/item/:id] 상품 수정")
    void updateItem() throws Exception {
        // given
        Long uid = 1L;
        ItemUpdateRequest dto = new ItemUpdateRequest();

        // when
        RequestBuilder request = put(addIid(uid))
                .content(objectMapper.writeValueAsBytes(dto))
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAuthenticationPrincipal(role = RoleType.SELLER)
    @DisplayName("[정상 작동][delete][/api/v1/item/:id] 상품 삭제")
    void deleteItem() throws Exception {
        // given
        Long uid = 1L;

        // when
        RequestBuilder request = delete(addIid(uid));

        // then
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[정상 작동][get][/api/v1/item?keyword=:keyword] 상품 검색")
    void searchItem() throws Exception {
        // given
        String keyword = "mock keyword";
        ItemReadResponse response = FixtureFactory.itemReadResponseFixture();

        given(itemService.searchItem(anyString(), any())).willReturn(new PageImpl<>(List.of(response)));

        // when
        RequestBuilder request = get(prefix).param("keyword", keyword);

        // then
        ResultActions actions = mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

        actions.andExpect(jsonPath(makePathJsonPath("id")).hasJsonPath());
        actions.andExpect(jsonPath(makePathJsonPath("createdAt")).hasJsonPath());
        actions.andExpect(jsonPath(makePathJsonPath("name")).hasJsonPath());
        actions.andExpect(jsonPath(makePathJsonPath("price")).hasJsonPath());
        actions.andExpect(jsonPath(makePathJsonPath("seller.companyName")).hasJsonPath());
        actions.andExpect(jsonPath(makePathJsonPath("imagePath")).hasJsonPath());
    }
}