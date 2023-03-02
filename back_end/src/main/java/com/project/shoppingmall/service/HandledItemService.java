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
    Page<HandledItemReadResponse> readPaymentAsReady(LoginDto loginDto, Pageable pageable);
    Page<HandledItemReadResponse> readPaymentAsDone(LoginDto loginDto, Pageable pageable);
    Page<HandledItemReadResponse> readPaymentAsCancel(LoginDto loginDto, Pageable pageable);

    HandledItemReadStatisticResponse readPaymentAsStatistic(LoginDto loginDto);

    void deletePaymentAsReady(HandledItemDeleteRequest request);
}
