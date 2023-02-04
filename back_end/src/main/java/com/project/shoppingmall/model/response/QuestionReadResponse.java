package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.Question;
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
        QuestionReadResponse dto = new QuestionReadResponse();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());

        dto.setContent(entity.getContent());

        dto.setUserId(entity.getUserId());
        dto.setUserName(entity.getUserName());

        dto.setSellerId(entity.getSellerId());
        dto.setSellerName(entity.getSellerName());

        dto.setItemId(entity.getItemId());
        dto.setItemName(entity.getItemName());
        return dto;
    }
}
