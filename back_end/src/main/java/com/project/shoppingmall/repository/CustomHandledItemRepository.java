package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.HandledItem;
import com.project.shoppingmall.type.HandledType;
import com.project.shoppingmall.type.ProcessType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CustomHandledItemRepository {
    Page<HandledItem> findXxxAs(HandledType handledType, ProcessType processType, Pageable pageable);
    Map<String, Long> countDeliveryByProcessType();
}
