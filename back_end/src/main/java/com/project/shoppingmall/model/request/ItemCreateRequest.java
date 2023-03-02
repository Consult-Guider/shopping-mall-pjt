package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.nested.Option;
import com.project.shoppingmall.domain.nested.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor @Getter @Setter
public class ItemCreateRequest {
    private String name;
    private Long price;
    private MultipartFile image;

    private List<Option> optionList;
    private List<MultipartFile> descriptionList;
    private List<Tag> tagList;

    public Item toEntity() {
        Item entity = new Item();
        entity.setName(name);
        entity.setPrice(price);

        entity.setOptionList(optionList);
        entity.setTagList(tagList);
        return entity;
    }
}
