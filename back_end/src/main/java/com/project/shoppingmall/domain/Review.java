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
@Document(indexName = "review")
@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @Builder(toBuilder = true)
public class Review {
    @Id @Field(type = FieldType.Auto)
    private String id;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis) @CreatedDate
    private LocalDateTime createdAt;

    @Field(type = FieldType.Integer)
    private Integer rating;
    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Long)
    private Long userId;
    @Field(type = FieldType.Text)
    private String userName;

    @Field(type = FieldType.Text)
    private String itemId;
    @Field(type = FieldType.Text)
    private String itemName;
    @Field(type = FieldType.Long)
    private Long itemSellerId;
    @Field(type = FieldType.Text)
    private String option;

    public static Review of(Review trg) {
        return trg.toBuilder().build();
    }
}
