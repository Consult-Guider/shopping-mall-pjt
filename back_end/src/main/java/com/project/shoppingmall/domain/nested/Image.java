package com.project.shoppingmall.domain.nested;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @Builder
public class Image {
    @Field(type = FieldType.Text)
    private String path;
}
