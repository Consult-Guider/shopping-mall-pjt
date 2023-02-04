package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.nested.Tag;
import lombok.*;

@AllArgsConstructor @Getter @Builder
public class TagReadResponse {
    private String name;

    public static TagReadResponse fromEntity(Tag entity) {
        return TagReadResponse.builder()
                .name(entity.getName())
                .build();
    }
}
