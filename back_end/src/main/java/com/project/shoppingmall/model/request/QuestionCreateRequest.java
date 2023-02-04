package com.project.shoppingmall.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class QuestionCreateRequest {
    private String content;
    private String parentId;
}
