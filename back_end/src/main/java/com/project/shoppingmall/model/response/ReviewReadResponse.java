package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor @Getter @Setter
public class ReviewReadResponse {
    private String id;
    private LocalDateTime createdAt;

    private Integer rating;
    private String content;

    private Long userId;
    private String userName;

    private String itemId;
    private String itemName;
    private String option;

    public static ReviewReadResponse fromEntity(Review entity) {
        ReviewReadResponse dto = new ReviewReadResponse();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());

        dto.setRating(entity.getRating());
        dto.setContent(entity.getContent());

        dto.setUserId(entity.getUserId());
        dto.setUserName(entity.getUserName());

        dto.setItemId(entity.getItemId());
        dto.setItemName(entity.getItemName());
        dto.setOption(entity.getOption());
        return dto;
    }
}
