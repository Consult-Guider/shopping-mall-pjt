package com.project.shoppingmall.service;

import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.HandledItemDeleteRequest;
import com.project.shoppingmall.model.request.PaymentCancelCreateRequest;
import com.project.shoppingmall.model.request.PaymentDoneCreateRequest;
import com.project.shoppingmall.model.request.PaymentReadyCreateRequest;
import com.project.shoppingmall.model.response.HandledItemReadResponse;
import com.project.shoppingmall.model.response.HandledItemReadStatisticResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HandledItemService {
    void createPaymentAsReady(PaymentReadyCreateRequest request, LoginDto loginDto);
    void createPaymentAsDone(PaymentDoneCreateRequest request, LoginDto loginDto);
    void createPaymentAsDoneWithId(String pid, LoginDto loginDto);

    void createPaymentAsCancel(PaymentCancelCreateRequest request, LoginDto loginDto);
    Page<HandledItemReadResponse> readPaymentAsReady(Pageable pageable);
    Page<HandledItemReadResponse> readPaymentAsDone(Pageable pageable);
    Page<HandledItemReadResponse> readPaymentAsCancel(Pageable pageable);

    HandledItemReadStatisticResponse readPaymentAsStatistic();

    void deletePaymentAsReady(HandledItemDeleteRequest request);
}
