package com.project.shoppingmall.domain;

import com.project.shoppingmall.domain.nested.Option;
import com.project.shoppingmall.type.ProcessType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Document(indexName = "handled_item")
// 위는 Elastic Search Document 사용을 위한 어노테이션
@NoArgsConstructor @Getter @Setter
// 위는 JPA Entity 사용을 위한 어노테이션
@AllArgsConstructor @SuperBuilder(toBuilder = true)
public class HandledItem {

    // 구매, 반품 대상의 고유 id
    @Id
    @Field(type = FieldType.Auto)
    private String id;

    // 해당 상품의 물품id(Item 도메인에서)
    @Field(type = FieldType.Object)
    private Item item;

    // 해당 상품의 사용자Id(User 도메인에서)
    @Field(type = FieldType.Object)
    private User user;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis) @CreatedDate
    private LocalDateTime createdAt;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime endAt;

    // 처리단계 (4단계)
    // <1 : 접수완료 / 접수완료> <2 : 상품준비중 / 상품반송준비중> <3 : 상품배달중/상품확인중> <4 : 배달완료/반품완료>
    @Field(type = FieldType.Keyword)
    private ProcessType ProcessType;

    //개수
    @Field(type = FieldType.Long)
    private Long count;

    //옵션
    @Field(type = FieldType.Object) @Builder.Default
    private List<Option> optionList = new ArrayList<>();
}
