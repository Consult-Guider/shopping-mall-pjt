package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.nested.Option;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor @Getter @Setter
public class HandledItemCreateRequest {

    //상품 id
    //TODO: 상품ID 넘겨받는 부분 재작성 필요 - 외래키 필요없이 그냥 상품id 받아오기
    private Item itemId;

    // 구매/반품 구분
    private Long itemType;

    //상품 처리 단계 - DB에 올리는 순간이 첫단계이니(접수완료) 1로 지정
    private Long itemProcessType = 1L;

    //구매개수
    private Long count;

    //옵션
    private List<Option> optionList;

    //TODO: 혹시 태그 및 이미지관련 자료가 구매/반품 과정에 필요한지? 일단은 지움, 태그는 검색기능에 쓰이는 것으로 알고 있으니

//    public HandledItem toEntity() {
//        HandledItem entity = new HandledItem();
//        entity.set(itemId);
//        entity.setItemType(itemType);
//        entity.setItemProcessType(itemProcessType);
//        entity.setCount(count);
//
//        entity.setOptionList(optionList);
//        return entity;
//    }
}
