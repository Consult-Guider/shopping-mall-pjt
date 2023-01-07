package com.project.shoppingmall.domain.nested;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @Builder(toBuilder = true)
public class Option {
    @Field(type = FieldType.Text)
    private String name;

    public static Option of(Option trg) {
        return trg.toBuilder().build();
    }
}
