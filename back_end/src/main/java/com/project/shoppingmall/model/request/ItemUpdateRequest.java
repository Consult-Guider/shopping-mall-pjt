package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.nested.Description;
import com.project.shoppingmall.domain.nested.Option;
import com.project.shoppingmall.domain.nested.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor @Getter @Setter
public class ItemUpdateRequest {
    private String name;
    private Long price;
    private String imagePath;

    private List<Option> optionList;
    private List<Description> descriptionList;
    private List<Tag> tagList;

    public Item overwrite(Item trg) {
        Item entity = Item.of(trg);

        Optional.ofNullable(this.getName()).filter(s -> !s.isBlank()).ifPresent(entity::setName);
        Optional.ofNullable(this.getPrice()).ifPresent(entity::setPrice);
        Optional.ofNullable(this.getImagePath()).filter(s -> !s.isBlank()).ifPresent(entity::setImagePath);

        Optional.ofNullable(this.getOptionList()).filter(s -> !s.isEmpty()).ifPresent(entity::setOptionList);
        Optional.ofNullable(this.getDescriptionList()).filter(s -> !s.isEmpty()).ifPresent(entity::setDescriptionList);
        Optional.ofNullable(this.getTagList()).filter(s -> !s.isEmpty()).ifPresent(entity::setTagList);
        return entity;
    }
}
