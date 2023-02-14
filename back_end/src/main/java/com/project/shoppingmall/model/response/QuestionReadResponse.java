package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Question;
import com.project.shoppingmall.domain.Seller;
import com.project.shoppingmall.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor @Getter @Setter
public class QuestionReadResponse {
    private String id;
    private LocalDateTime createdAt;

    private Integer rating;
    private String content;

    private Long userId;
    private String userName;

    private Long sellerId;
    private String sellerName;

    private String itemId;
    private String itemName;
    private String option;

    public static QuestionReadResponse fromEntity(Question entity) {
        User user = entity.getUser();
        Seller seller = entity.getSeller();
        Item item = entity.getItem();

        QuestionReadResponse dto = new QuestionReadResponse();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());

        dto.setContent(entity.getContent());

        dto.setUserId(user.getId());
        dto.setUserName(user.getName());

        dto.setSellerId(seller.getId());
        dto.setSellerName(seller.getName());

        dto.setItemId(item.getId());
        dto.setItemName(item.getName());
        return dto;
    }
}
