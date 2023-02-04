package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@NoArgsConstructor @Getter @Setter
public class QuestionUpdateRequest {
    private String content;

    public Question overwrite(Question trg) {
        Question entity = Question.of(trg);

        Optional.ofNullable(this.getContent())
                .filter(s -> !s.isBlank()).ifPresent(entity::setContent);
        return entity;
    }
}
