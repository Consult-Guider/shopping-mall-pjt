package com.project.shoppingmall.utils;

import com.project.shoppingmall.model.AdminDto;
import com.project.shoppingmall.model.SellerDto;
import com.project.shoppingmall.model.UserDto;
import com.project.shoppingmall.model.request.UserCreateRequest;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class FixtureFactory {
    public static final UserDto userDtoFixture = UserDto.builder()
            .id(1L).createdAt(LocalDateTime.MIN).deletedAt(LocalDateTime.MAX)
            .email("mock email").password("mock password").name("mock name")
            .phoneNum("mock phoneNum").address("mock address")
            .build();
    public static final SellerDto sellerDtoFixture = SellerDto.builder()
            .id(1L).createdAt(LocalDateTime.MIN).deletedAt(LocalDateTime.MAX)
            .email("mock email").password("mock password").name("mock name")
            .phoneNum("mock phoneNum").address("mock address").companyName("mock companyName")
            .build();
    public static final AdminDto adminDtoFixture = AdminDto.builder()
            .id(1L).createdAt(LocalDateTime.MIN).deletedAt(LocalDateTime.MAX)
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

}