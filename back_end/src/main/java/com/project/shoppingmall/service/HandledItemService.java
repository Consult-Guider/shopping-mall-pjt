package com.project.shoppingmall.service;

import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.PaymentDoneCreateRequest;
import com.project.shoppingmall.model.request.PaymentReadyCreateRequest;
import com.project.shoppingmall.model.response.HandledItemReadResponse;
import com.project.shoppingmall.model.response.HandledItemReadStatisticResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HandledItemService {
    void createPaymentAsReady(PaymentReadyCreateRequest request, LoginDto loginDto);
    void createPaymentAsDone(PaymentDoneCreateRequest request, LoginDto loginDto);

    Page<HandledItemReadResponse> readPaymentAsReady(Pageable pageable);
    HandledItemReadStatisticResponse readDeliveryAsStatistic();
    Page<HandledItemReadResponse> readDelivery(Pageable pageable);
    Page<HandledItemReadResponse> readDeliveryAsReady(Pageable pageable);
    Page<HandledItemReadResponse> readDeliveryAsOngoing(Pageable pageable);
    Page<HandledItemReadResponse> readDeliveryAsDone(Pageable pageable);
}
