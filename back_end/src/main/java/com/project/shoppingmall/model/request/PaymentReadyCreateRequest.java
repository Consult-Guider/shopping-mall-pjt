package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.HandledItem;
import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.User;
import com.project.shoppingmall.domain.nested.Option;
import com.project.shoppingmall.type.ProcessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.function.Function;

@NoArgsConstructor @Getter @Setter
public class PaymentReadyCreateRequest {
    private String itemId;
    private Long count;
    private List<Option> optionList;

    public HandledItem toEntity(User user, Function<String, Item> loadItemById) {
        HandledItem entity = new HandledItem();
        entity.setProcessType(ProcessType.READY);
        entity.setItem(loadItemById.apply(itemId));
        entity.setUser(user);
        entity.setCount(count);
        entity.setOptionList(optionList);
        return entity;
    }
}
