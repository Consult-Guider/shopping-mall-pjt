package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@NoArgsConstructor @Getter @Setter
public class ReviewUpdateRequest {
    private Integer rating;
    private String content;

    public Review overwrite(Review trg) {
        Review entity = Review.of(trg);

        Optional.ofNullable(this.getRating())
                .ifPresent(entity::setRating);
        Optional.ofNullable(this.getContent())
                .filter(s -> !s.isBlank()).ifPresent(entity::setContent);
        return entity;
    }
}
