package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Question;
import com.project.shoppingmall.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@NoArgsConstructor @Getter @Setter
public class SearchResponse {
    private String id;
    private LocalDateTime createdAt;

    private String name;
    private Long price;
    private String imagePath;

    public static SearchResponse fromEntity(Item item) {
        item = ofNullable(item).orElseGet(Item::new);
        SearchResponse response = new SearchResponse();
        response.setId(item.getId());
        response.setCreatedAt(item.getCreatedAt());
        response.setName(item.getName());
        response.setPrice(item.getPrice());
        response.setImagePath(item.getImagePath());
        return response;
    }

    public static SearchResponse fromEntity(Review review) {
        Item item = review.getItem();
        return fromEntity(item);
    }

    public static SearchResponse fromEntity(Question question) {
        Item item = question.getItem();
        return fromEntity(item);
    }
}
