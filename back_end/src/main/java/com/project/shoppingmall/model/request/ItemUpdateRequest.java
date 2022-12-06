package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.nested.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor @Getter @Setter
public class ItemUpdateRequest {
    private String name;
    private Long price;
    private String imagePath;

    private List<Image> imageList;
    private List<Option> optionList;
    private List<Description> descriptionList;
    private List<Review> reviewList;
    private List<Question> questionList;
    private List<Tag> tagList;

    public Item overwrite(Item entity) {
        Optional.ofNullable(this.getName()).filter(s -> !s.isBlank()).ifPresent(entity::setName);
        Optional.ofNullable(this.getPrice()).ifPresent(entity::setPrice);
        Optional.ofNullable(this.getImagePath()).filter(s -> !s.isBlank()).ifPresent(entity::setImagePath);

        Optional.ofNullable(this.getImageList()).filter(s -> !s.isEmpty()).ifPresent(entity::setImageList);
        Optional.ofNullable(this.getOptionList()).filter(s -> !s.isEmpty()).ifPresent(entity::setOptionList);
        Optional.ofNullable(this.getDescriptionList()).filter(s -> !s.isEmpty()).ifPresent(entity::setDescriptionList);
        Optional.ofNullable(this.getReviewList()).filter(s -> !s.isEmpty()).ifPresent(entity::setReviewList);
        Optional.ofNullable(this.getQuestionList()).filter(s -> !s.isEmpty()).ifPresent(entity::setQuestionList);
        Optional.ofNullable(this.getTagList()).filter(s -> !s.isEmpty()).ifPresent(entity::setTagList);
        return entity;
    }
}
