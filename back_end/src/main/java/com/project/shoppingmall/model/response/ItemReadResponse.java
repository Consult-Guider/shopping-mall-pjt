package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Seller;
import com.project.shoppingmall.domain.nested.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor @Getter @Builder
public class ItemReadResponse {
    private final String id;
    private final LocalDateTime createdAt;

    private final String name;
    private final Long price;
    private final SellerWithItemResponse seller;
    private final String imagePath;

    private final List<Image> imageList;
    private final List<Option> optionList;
    private final List<Description> descriptionList;
    private final List<Review> reviewList;
    private final List<Question> questionList;
    private final List<Tag> tagList;

    public static ItemReadResponse fromEntity(Item entity, Seller sellerEntity) {
        return ItemReadResponse.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())

                .name(entity.getName())
                .price(entity.getPrice())
                .seller(Optional.ofNullable(sellerEntity).map(SellerWithItemResponse::fromEntity).orElse(null))
                .imagePath(entity.getImagePath())
                .build();
    }

    public static ItemReadResponse fromEntity(Item entity) {
        return fromEntity(entity, null);
    }

    public static ItemReadResponse allFromEntity(Item entity, Seller sellerEntity) {
        return ItemReadResponse.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())

                .name(entity.getName())
                .price(entity.getPrice())
                .seller(Optional.ofNullable(sellerEntity).map(SellerWithItemResponse::fromEntity).orElse(null))
                .imagePath(entity.getImagePath())

                .imageList(entity.getImageList())
                .optionList(entity.getOptionList())
                .descriptionList(entity.getDescriptionList())
                .reviewList(entity.getReviewList())
                .questionList(entity.getQuestionList())
                .tagList(entity.getTagList())
                .build();
    }
}
