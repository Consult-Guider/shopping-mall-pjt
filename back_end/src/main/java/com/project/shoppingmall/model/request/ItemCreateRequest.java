package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.nested.Description;
import com.project.shoppingmall.domain.nested.Image;
import com.project.shoppingmall.domain.nested.Option;
import com.project.shoppingmall.domain.nested.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor @Getter @Setter
public class ItemCreateRequest {
    private String name;
    private Long price;
    private String imagePath;

    private List<Image> imageList;
    private List<Option> optionList;
    private List<Description> descriptionList;
    private List<Tag> tagList;

    public Item toEntity() {
        Item entity = new Item();
        entity.setName(name);
        entity.setPrice(price);
        entity.setImagePath(imagePath);

        entity.setImageList(imageList);
        entity.setOptionList(optionList);
        entity.setDescriptionList(descriptionList);
        entity.setTagList(tagList);
        return entity;
    }
}
