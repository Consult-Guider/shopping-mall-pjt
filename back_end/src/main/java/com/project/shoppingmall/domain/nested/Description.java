package com.project.shoppingmall.domain.nested;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @Builder(toBuilder = true)
public class Description {
    @Field(type = FieldType.Text)
    private String path;

    public static Description of(Description trg) {
        return trg.toBuilder().build();
    }
}
