package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Optional.ofNullable;

@NoArgsConstructor @Getter @Setter
public class QuestionSearchResponse {
    private String id;

    private String content;

    private String itemId;
    private String itemName;
    private String option;

    public static QuestionSearchResponse fromEntity(Question entity) {
        Item item = ofNullable(entity.getItem()).orElseGet(Item::new);
        QuestionSearchResponse dto = new QuestionSearchResponse();
        dto.setId(entity.getId());

        dto.setContent(entity.getContent());

        dto.setItemId(item.getId());
        dto.setItemName(item.getName());
        return dto;
    }
}
