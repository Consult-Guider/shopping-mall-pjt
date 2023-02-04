package com.project.shoppingmall.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.time.LocalDateTime;

@ToString
@Document(indexName = "question")
@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @Builder(toBuilder = true)
public class Question {
    @Id @Field(type = FieldType.Auto)
    private String id;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis) @CreatedDate
    private LocalDateTime createdAt;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Text)
    private String parentId;

    @Field(type = FieldType.Long)
    private Long userId;
    @Field(type = FieldType.Text)
    private String userName;

    @Field(type = FieldType.Long)
    private Long sellerId;
    @Field(type = FieldType.Text)
    private String sellerName;

    @Field(type = FieldType.Text)
    private String itemId;
    @Field(type = FieldType.Text)
    private String itemName;

    public static Question of(Question trg) {
        return trg.toBuilder().build();
    }
}
