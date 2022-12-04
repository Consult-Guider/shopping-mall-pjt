package com.project.shoppingmall.domain.nested;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @Builder
public class Review {
    @Field(type = FieldType.Integer)
    private Integer rating;
    @Field(type = FieldType.Text)
    private String userName;
    @Field(type = FieldType.Text)
    private String option;
    @Field(type = FieldType.Text)
    private String content;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis) @CreatedDate
    private LocalDateTime createdAt;
    @Field(type = FieldType.Object) @Builder.Default
    private List<Like> likes = new ArrayList<>();
    @Field(type = FieldType.Object) @Builder.Default
    private List<Dislike> dislikes = new ArrayList<>();
}
