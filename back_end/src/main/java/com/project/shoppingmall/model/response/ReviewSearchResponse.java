package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class ReviewSearchResponse {
    private String id;

    private Integer rating;
    private String content;

    private String itemId;
    private String itemName;
    private String option;

    public static ReviewSearchResponse fromEntity(Review entity) {
        Item item = entity.getItem();

        ReviewSearchResponse dto = new ReviewSearchResponse();
        dto.setId(entity.getId());

        dto.setRating(entity.getRating());
        dto.setContent(entity.getContent());

        dto.setItemId(item.getId());
        dto.setItemName(item.getName());

        dto.setOption(entity.getOption());
        return dto;
    }
}
