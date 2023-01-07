package com.project.shoppingmall.domain.nested;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @Builder(toBuilder = true)
public class Like {
    @Field(type = FieldType.Long)
    private Long userId;

    public static Like of(Like trg) {
        return trg.toBuilder().build();
    }
}
