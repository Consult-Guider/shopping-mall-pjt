package com.project.shoppingmall.domain;

import com.project.shoppingmall.domain.nested.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
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
@Document(indexName = "item")
// 위는 Elastic Search Document 사용을 위한 어노테이션
@NoArgsConstructor @Getter @Setter
// 위는 JPA Entity 사용을 위한 어노테이션
// TODO: 해당 엔티티는 JPA를 통해 delete가 이뤄지는 것이 아니기 때문에 Soft Delete를 직접 구현해서 사용해야 함.
@AllArgsConstructor @Builder(toBuilder = true)
public class Item {
    @Id @Field(type = FieldType.Auto)
    private String id;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis) @CreatedDate
    private LocalDateTime createdAt;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime deletedAt;

    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Long)
    private Long price;
    @Field(type = FieldType.Long) @CreatedBy
    private Long seller;
    @Field(type = FieldType.Text)
    private String imagePath;

    @Field(type = FieldType.Object) @Builder.Default
    private List<Option> optionList = new ArrayList<>();
    @Field(type = FieldType.Object) @Builder.Default
    private List<Description> descriptionList = new ArrayList<>();
    @Field(type = FieldType.Object) @Builder.Default
    private List<Review> reviewList = new ArrayList<>();
    @Field(type = FieldType.Object) @Builder.Default
    private List<Question> questionList = new ArrayList<>();
    @Field(type = FieldType.Object) @Builder.Default
    private List<Tag> tagList = new ArrayList<>();

    public void addOptionList(Option obj) { optionList.add(obj); }
    public void addDescriptionList(Description obj) { descriptionList.add(obj); }
    public void addReviewList(Review obj) { reviewList.add(obj); }
    public void addQuestionList(Question obj) { questionList.add(obj); }
    public void addTagList(Tag obj) { tagList.add(obj); }

    public static Item of(Item trg) {
        return trg.toBuilder()
                .optionList(trg.optionList.stream().map(Option::of).toList())
                .descriptionList(trg.descriptionList.stream().map(Description::of).toList())
                .reviewList(trg.reviewList.stream().map(Review::of).toList())
                .questionList(trg.questionList.stream().map(Question::of).toList())
                .tagList(trg.tagList.stream().map(Tag::of).toList())
                .build();
    }
}
