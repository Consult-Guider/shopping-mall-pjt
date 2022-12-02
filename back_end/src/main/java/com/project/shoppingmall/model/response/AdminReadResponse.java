package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.Admin;
import com.project.shoppingmall.model.AdminDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor @Getter @Setter
public class AdminReadResponse {
    private Long id;
    private LocalDateTime createdAt;

    private String email;

    private String name;
    private String phoneNum;

    public static AdminReadResponse fromDto(AdminDto dto) {
        AdminReadResponse response = new AdminReadResponse();
        response.setId(dto.getId());
        response.setCreatedAt(dto.getCreatedAt());

        response.setEmail(dto.getEmail());

        response.setName(dto.getName());
        response.setPhoneNum(dto.getPhoneNum());
        return response;
    }

    public static AdminReadResponse fromEntity(Admin entity) {
        AdminReadResponse response = new AdminReadResponse();
        response.setId(entity.getId());
        response.setCreatedAt(entity.getCreatedAt());

        response.setEmail(entity.getEmail());

        response.setName(entity.getName());
        response.setPhoneNum(entity.getPhoneNum());
        return response;
    }
}
