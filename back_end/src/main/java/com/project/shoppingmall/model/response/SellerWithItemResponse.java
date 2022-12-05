package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor @Getter @Builder
public class SellerWithItemResponse {
    private final Long id;
    private final String name;
    private final String companyName;

    public static SellerWithItemResponse fromEntity(Seller seller) {
        return SellerWithItemResponse.builder()
                .id(seller.getId())
                .name(seller.getName())
                .companyName(seller.getCompanyName())
                .build();
    }
}
