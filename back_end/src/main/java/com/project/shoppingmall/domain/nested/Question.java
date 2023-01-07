package com.project.shoppingmall.domain.nested;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @Builder(toBuilder = true)
public class Question {
    @Id @Field(type = FieldType.Auto)
    private String id;
    @Field(type = FieldType.Text)
    private String itemName;
    @Field(type = FieldType.Text)
    private String option;
    @Field(type = FieldType.Text)
    private String userName;
    @Field(type = FieldType.Text)
    private String content;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis) @CreatedDate
    private LocalDateTime createdAt;

    public static Question of(Question trg) {
        return trg.toBuilder().build();
    }
}
