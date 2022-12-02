package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.User;
import com.project.shoppingmall.model.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor @Getter @Setter
public class UserReadResponse {
    private Long id;
    private LocalDateTime createdAt;

    private String email;

    private String name;
    private String phoneNum;
    private String address;

    public static UserReadResponse fromEntity(User entity) {
        UserReadResponse response = new UserReadResponse();
        response.setId(entity.getId());
        response.setCreatedAt(entity.getCreatedAt());

        response.setEmail(entity.getEmail());

        response.setName(entity.getName());
        response.setPhoneNum(entity.getPhoneNum());
        response.setAddress(entity.getAddress());
        return response;
    }

    public static UserReadResponse fromDto(UserDto dto) {
        UserReadResponse response = new UserReadResponse();
        response.setId(dto.getId());
        response.setCreatedAt(dto.getCreatedAt());

        response.setEmail(dto.getEmail());

        response.setName(dto.getName());
        response.setPhoneNum(dto.getPhoneNum());
        response.setAddress(dto.getAddress());
        return response;
    }
}
