package com.project.shoppingmall.domain;

import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.SellerDto;
import com.project.shoppingmall.model.UserDto;
import com.project.shoppingmall.model.request.QuestionCreateRequest;
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

    @Field(type = FieldType.Object)
    private User user;

    @Field(type = FieldType.Object)
    private Seller seller;

    @Field(type = FieldType.Object)
    private Item item;

    public static Question of(Question trg) {
        return trg.toBuilder().build();
    }

    public static Question fromDto(
            String parentId, Item item,
            QuestionCreateRequest request, LoginDto loginDto,
            Runnable runnableWhenLoginDtoCanNotCast
            ) {

        Question entity = Question.builder()
                .content(request.getContent())
                .parentId(parentId)
                .item(item)
                .build();

        if(loginDto instanceof UserDto dto) {
            entity.setUser(dto.toEntity());
        } else if(loginDto instanceof SellerDto dto) {
            entity.setSeller(dto.toEntity());
        } else {
            runnableWhenLoginDtoCanNotCast.run();
        }
        return entity;
    }
}
