package com.project.shoppingmall.utils;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Question;
import com.project.shoppingmall.domain.Review;
import com.project.shoppingmall.domain.User;
import com.project.shoppingmall.domain.nested.*;
import com.project.shoppingmall.model.AdminDto;
import com.project.shoppingmall.model.SellerDto;
import com.project.shoppingmall.model.UserDto;
import com.project.shoppingmall.model.response.ItemReadResponse;
import com.project.shoppingmall.model.request.*;
import com.project.shoppingmall.model.response.SellerWithItemResponse;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public class FixtureFactory {
    public static final UserDto userDtoFixture = UserDto.builder()
            .id(1L).createdAt(LocalDateTime.now()).deletedAt(null)
            .email("mock email").password("mock password").name("mock name")
            .phoneNum("mock phoneNum").address("mock address")
            .build();
    public static final SellerDto sellerDtoFixture = SellerDto.builder()
            .id(1L).createdAt(LocalDateTime.now()).deletedAt(null)
            .email("mock email").password("mock password").name("mock name")
            .phoneNum("mock phoneNum").address("mock address").companyName("mock companyName")
            .build();
    public static final AdminDto adminDtoFixture = AdminDto.builder()
            .id(1L).createdAt(LocalDateTime.now()).deletedAt(null)
            .email("mock email").password("mock password").name("mock name")
            .phoneNum("mock phoneNum")
            .build();

    public static final UserCreateRequest userCreateRequestFixture =
            makeUserCreateRequestFixture();

    private static UserCreateRequest makeUserCreateRequestFixture() {
        return UserCreateRequest.of(
                "mockEmail@gmail.com",
                "mock password",
                "mock name",
                "010-1234-0234"
        );
    }

    public static AdminCreateRequest adminCreateRequestFixture() {
        return AdminCreateRequest.of(
                "mockEmail@gmail.com",
                "mock password",
                "mock name",
                "010-1234-0234"
        );
    }

    public static SellerCreateRequest sellerCreateRequestFixture() {
        return SellerCreateRequest.of(
                "mockEmail@gmail.com",
                "mock password",
                "mock name",
                "010-1234-0234",
                "coupang"
        );
    }

    private static Stream<UserDto> provideInappropriateUserDto() {
        return Stream.of(
                // 부적절한 이메일 형식
                UserDto.builder()
                        .email("Inappropriate Email Format")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("010-1234-1234")
                        .address("경기도 김포시 풍무동")
                        .build(),
                // '-' 표시가 없는 전화번호
                UserDto.builder()
                        .email("mockEmail@gmail.com")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("01012341234")
                        .address("경기도 김포시 풍무동")
                        .build(),
                // 앞의 자릿수(4)가 맞지 않는 전화번호
                UserDto.builder()
                        .email("mockEmail@gmail.com")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("0101-1234-1234")
                        .address("경기도 김포시 풍무동")
                        .build(),
                // 중간 자릿수(5)가 맞지 않는 전화번호
                UserDto.builder()
                        .email("mockEmail@gmail.com")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("010-12345-1234")
                        .address("경기도 김포시 풍무동")
                        .build(),
                // 마지막 자릿수(5)가 맞지 않는 전화번호
                UserDto.builder()
                        .email("mockEmail@gmail.com")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("010-1234-12345")
                        .address("경기도 김포시 풍무동")
                        .build()
        );
    }

    public static Stream<Arguments> provideInappropriateParametersUserCreateRequest() {
        Stream<UserDto> stream = provideInappropriateUserDto();
        return stream.map(userDto -> Arguments.of(
                        userDto.getEmail(),
                        userDto.getPassword(),
                        userDto.getName(),
                        userDto.getPhoneNum()
                ));
    }

    public static Stream<Arguments> provideInappropriateParametersUserUpdateRequest() {
        Stream<UserDto> stream = provideInappropriateUserDto();
        return stream.map(userDto -> Arguments.of(
                userDto.getPassword(),
                userDto.getName(),
                userDto.getPhoneNum(),
                userDto.getAddress()
        ));
    }

    private static Stream<AdminDto> provideInappropriateAdminDto() {
        return Stream.of(
                // 부적절한 이메일 형식
                AdminDto.builder()
                        .email("Inappropriate Email Format")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("010-1234-1234")
                        .build(),
                // '-' 표시가 없는 전화번호
                AdminDto.builder()
                        .email("mockEmail@gmail.com")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("01012341234")
                        .build(),
                // 앞의 자릿수(4)가 맞지 않는 전화번호
                AdminDto.builder()
                        .email("mockEmail@gmail.com")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("0101-1234-1234")
                        .build(),
                // 중간 자릿수(5)가 맞지 않는 전화번호
                AdminDto.builder()
                        .email("mockEmail@gmail.com")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("010-12345-1234")
                        .build(),
                // 마지막 자릿수(5)가 맞지 않는 전화번호
                AdminDto.builder()
                        .email("mockEmail@gmail.com")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("010-1234-12345")
                        .build()
        );
    }

    public static Stream<Arguments> provideInappropriateParametersAdminCreateRequest() {
        Stream<AdminDto> stream = provideInappropriateAdminDto();
        return stream.map(adminDto -> Arguments.of(
                adminDto.getEmail(),
                adminDto.getPassword(),
                adminDto.getName(),
                adminDto.getPhoneNum()
        ));
    }

    private static Stream<SellerDto> provideInappropriateSellerDto() {
        return Stream.of(
                // 부적절한 이메일 형식
                SellerDto.builder()
                        .email("Inappropriate Email Format")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("010-1234-1234")
                        .companyName("mock companyName")
                        .build(),
                // '-' 표시가 없는 전화번호
                SellerDto.builder()
                        .email("mockEmail@gmail.com")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("01012341234")
                        .companyName("mock companyName")
                        .build(),
                // 앞의 자릿수(4)가 맞지 않는 전화번호
                SellerDto.builder()
                        .email("mockEmail@gmail.com")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("0101-1234-1234")
                        .companyName("mock companyName")
                        .build(),
                // 중간 자릿수(5)가 맞지 않는 전화번호
                SellerDto.builder()
                        .email("mockEmail@gmail.com")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("010-12345-1234")
                        .companyName("mock companyName")
                        .build(),
                // 마지막 자릿수(5)가 맞지 않는 전화번호
                SellerDto.builder()
                        .email("mockEmail@gmail.com")
                        .password("mock password")
                        .name("mock name")
                        .phoneNum("010-1234-12345")
                        .companyName("mock companyName")
                        .build()
        );
    }

    public static Stream<Arguments> provideInappropriateParametersSellerCreateRequest() {
        Stream<SellerDto> stream = provideInappropriateSellerDto();
        return stream.map(sellerDto -> Arguments.of(
                sellerDto.getEmail(),
                sellerDto.getPassword(),
                sellerDto.getName(),
                sellerDto.getPhoneNum(),
                sellerDto.getCompanyName()
        ));
    }

    public static ItemCreateRequest itemCreateRequestFixture() throws IOException {
        ItemCreateRequest request = new ItemCreateRequest();
        request.setName("mock Name");
        request.setPrice(10000L);
        request.setImage(ImageFixture());

        request.setOptionList(List.of(optionFixture()));
        request.setDescriptionList(List.of(descriptionImageFixture()));
        request.setTagList(List.of(tagFixture()));

        return request;
    }

    public static MultipartFile ImageFixture() throws IOException {
        URL url = new URL("https://cdn.pixabay.com/photo/2019/04/06/06/44/astronaut-4106766_960_720.jpg");
        return new MockMultipartFile("image", "astronaut-4106766_960_720.jpg", null, url.openStream());
    }

    public static Option optionFixture() {
        Option entity = new Option();
        entity.setName("mock Option Name");
        return entity;
    }

    public static Description descriptionFixture() {
        Description entity = new Description();
        entity.setPath("mock Description Path");
        return entity;
    }

    public static MultipartFile descriptionImageFixture() throws IOException {
        URL url = new URL("https://cdn.pixabay.com/photo/2018/07/09/16/59/clouds-3526558_960_720.jpg");
        return new MockMultipartFile("descriptionList", "clouds-3526558_960_720.jpg", null, url.openStream());
    }

    public static Tag tagFixture() {
        Tag entity = new Tag();
        entity.setName("mock Tag Name");
        return entity;
    }

    public static Review reviewFixture() {
        Review entity = new Review();
        entity.setRating(5);
        entity.setContent("mock Review Content");
        return entity;
    }

    public static Review reviewFixture(long userId, String itemId) {
        return reviewFixture(userId, itemId, null);
    }

    public static Review reviewFixture(long userId, String itemId, String keyword) {
        Review entity = reviewFixture();
        entity.setUser(User.builder().id(userId).build());
        entity.setItem(Item.builder().id(itemId).build());
        entity.setContent(keyword);
        return entity;
    }

    public static Review reviewFixture(String itemId, int rating) {
        Review entity = reviewFixture();
        entity.setItem(Item.builder().id(itemId).build());
        entity.setRating(rating);
        return entity;
    }

    public static Review reviewFixture(long itemSellerId) {
        Review entity = reviewFixture();
        entity.setItem(Item.builder().seller(itemSellerId).build());
        return entity;
    }

    public static Question questionFixture() {
        Question entity = new Question();
        entity.setContent("mock Question Content");
        return entity;
    }

    public static Question questionFixture(long userId, String itemId) {
        return questionFixture(userId, itemId, null);
    }

    public static Question questionFixture(long userId, String itemId, String keyword) {
        Question entity = questionFixture();
        entity.setUser(User.builder().id(userId).build());
        entity.setItem(Item.builder().id(itemId).build());
        entity.setContent(keyword);
        return entity;
    }

    public static SellerWithItemResponse sellerWithItemResponseFixture() {
        return SellerWithItemResponse.builder()
                .id(2L)
                .name("mock SellerWithItemResponse Name")
                .companyName("mock SellerWithItemResponse CompanyName")
                .build();
    }

    public static AdImgCreateRequest adImgCreateRequestFixture() throws IOException {
        AdImgCreateRequest dto = new AdImgCreateRequest();
        dto.setItemName("mock itemName");
        dto.setCompanyName("mock companyName");
        dto.setPath(descriptionImageFixture());
        dto.setLink("https://school.programmers.co.kr/learn/challenges");
        dto.setStartAt(LocalDateTime.of(2022, 1, 6, 0, 0, 0));
        dto.setEndAt(LocalDateTime.now());
        return dto;
    }

    public static AdImgUpdateRequest adImgUpdateRequestFixture() throws IOException {
        AdImgUpdateRequest dto = new AdImgUpdateRequest();
        dto.setItemName("mock itemName");
        dto.setCompanyName("mock companyName");
        dto.setPath(descriptionImageFixture());
        dto.setLink("https://school.programmers.co.kr/learn/challenges");
        dto.setStartAt(LocalDateTime.of(2022, 1, 6, 0, 0, 0));
        dto.setEndAt(LocalDateTime.now());
        return dto;
    }


    public static ItemReadResponse itemReadResponseFixture() {
        return ItemReadResponse.builder()
                .id("mock id")
                .createdAt(LocalDateTime.now())
                .name("mock name")
                .price(10000L)
                .seller(sellerWithItemResponseFixture())
                .imagePath("mock imagePath")
                .build();
    }

    public static ItemReadResponse itemReadAllResponseFixture() {
        return ItemReadResponse.builder()
                .id("mock id")
                .createdAt(LocalDateTime.now())
                .name("mock name")
                .price(10000L)
                .seller(sellerWithItemResponseFixture())
                .imagePath("mock imagePath")

                .optionList(List.of(optionFixture()))
                .descriptionList(List.of(descriptionFixture()))
                .reviewList(List.of(reviewFixture()))
                .questionList(List.of(questionFixture()))
                .tagList(List.of(tagFixture()))
                .build();
    }
}
