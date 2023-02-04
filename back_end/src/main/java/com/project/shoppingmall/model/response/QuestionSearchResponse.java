package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class QuestionSearchResponse {
    private String id;

    private String content;

    private String itemId;
    private String itemName;
    private String option;

    public static QuestionSearchResponse fromEntity(Question entity) {
        QuestionSearchResponse dto = new QuestionSearchResponse();
        dto.setId(entity.getId());

        dto.setContent(entity.getContent());

        dto.setItemId(entity.getItemId());
        dto.setItemName(entity.getItemName());
        return dto;
    }
}
