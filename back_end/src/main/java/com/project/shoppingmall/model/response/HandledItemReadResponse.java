package com.project.shoppingmall.model.response;

import com.project.shoppingmall.domain.HandledItem;
import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.User;
import com.project.shoppingmall.domain.nested.Option;
import com.project.shoppingmall.type.HandledType;
import com.project.shoppingmall.type.ProcessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor @Getter @Setter
public class HandledItemReadResponse {
    private String id;
    private LocalDateTime createdAt;

    private Long userId;
    private String userName;

    private String itemId;
    private String itemName;
    private Long itemPrice;
    private Long itemSellerId;
    private String itemImagePath;

    private HandledType handledType;
    private ProcessType processType;

    private Long count;
    private List<Option> optionList;


    public static HandledItemReadResponse fromEntity(HandledItem entity) {
        User user = entity.getUser();
        Item item = entity.getItem();

        HandledItemReadResponse dto = new HandledItemReadResponse();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());

        dto.setUserId(user.getId());
        dto.setUserName(user.getName());

        dto.setItemId(item.getId());
        dto.setItemPrice(item.getPrice());
        dto.setItemSellerId(item.getSeller());
        dto.setItemImagePath(item.getImagePath());

        dto.setHandledType(entity.getHandledType());
        dto.setProcessType(entity.getProcessType());

        dto.setCount(entity.getCount());
        dto.setOptionList(entity.getOptionList());
        return dto;
    }
}
