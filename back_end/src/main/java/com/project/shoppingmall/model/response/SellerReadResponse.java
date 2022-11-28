package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.Seller;
import com.project.shoppingmall.model.SellerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor @Getter @Setter
public class SellerReadResponse {
    private Long id;
    private LocalDateTime createdAt;

    private String email;

    private String name;
    private String phoneNum;
    private String companyName;

    public static SellerReadResponse fromDto(SellerDto entity) {
        SellerReadResponse response = new SellerReadResponse();
        response.setId(entity.getId());
        response.setCreatedAt(entity.getCreatedAt());

        response.setEmail(entity.getEmail());

        response.setName(entity.getName());
        response.setPhoneNum(entity.getPhoneNum());
        return response;
    }

    public static SellerReadResponse fromEntity(Seller entity) {
        SellerReadResponse response = new SellerReadResponse();
        response.setId(entity.getId());
        response.setCreatedAt(entity.getCreatedAt());

        response.setEmail(entity.getEmail());

        response.setName(entity.getName());
        response.setPhoneNum(entity.getPhoneNum());
        response.setCompanyName(entity.getCompanyName());
        return response;
    }
}
