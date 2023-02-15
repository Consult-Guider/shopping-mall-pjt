package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.HandledItem;
import com.project.shoppingmall.type.ProcessType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CustomHandledItemRepository {
    Page<HandledItem> findXxxByUser(ProcessType processType, Long uid, Pageable pageable);
    Page<HandledItem> findXxxBySeller(ProcessType processType, Long uid, Pageable pageable);
    Map<String, Long> countPaymentByProcessTypeWithUserId(Long uid);
    Map<String, Long> countPaymentByProcessTypeWithSellerId(Long uid);
}
