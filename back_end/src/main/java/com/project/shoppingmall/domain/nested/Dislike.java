package com.project.shoppingmall.domain.nested;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @Builder(toBuilder = true)
public class Dislike {
    @Field(type = FieldType.Long)
    private Long userId;

    public static Dislike of(Dislike trg) {
        return trg.toBuilder().build();
    }
}
