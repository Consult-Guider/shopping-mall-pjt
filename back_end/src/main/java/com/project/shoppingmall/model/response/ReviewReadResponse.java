package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Review;
import com.project.shoppingmall.domain.User;
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
        User user = entity.getUser();
        Item item = entity.getItem();

        ReviewReadResponse dto = new ReviewReadResponse();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());

        dto.setRating(entity.getRating());
        dto.setContent(entity.getContent());

        dto.setUserId(user.getId());
        dto.setUserName(user.getName());

        dto.setItemId(item.getId());
        dto.setItemName(item.getName());

        dto.setOption(entity.getOption());
        return dto;
    }
}
