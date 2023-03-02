package com.project.shoppingmall.domain;

import com.project.shoppingmall.domain.nested.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
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
@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @SuperBuilder(toBuilder = true)
public class Item {
    @Id @Field(type = FieldType.Auto)
    private String id;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis) @CreatedDate
    private LocalDateTime createdAt;

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
    private List<Tag> tagList = new ArrayList<>();

    public void addOptionList(Option obj) { optionList.add(obj); }
    public void addDescriptionList(Description obj) { descriptionList.add(obj); }
    public void addTagList(Tag obj) { tagList.add(obj); }

    public static Item of(Item trg) {
        return trg.toBuilder()
                .optionList(trg.optionList.stream().map(Option::of).toList())
                .descriptionList(trg.descriptionList.stream().map(Description::of).toList())
                .tagList(trg.tagList.stream().map(Tag::of).toList())
                .build();
    }
}
