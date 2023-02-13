package com.project.shoppingmall.controller;

import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.HandledItemDeleteRequest;
import com.project.shoppingmall.model.request.PaymentCancelCreateRequest;
import com.project.shoppingmall.model.request.PaymentDoneCreateRequest;
import com.project.shoppingmall.model.request.PaymentReadyCreateRequest;
import com.project.shoppingmall.model.response.HandledItemReadResponse;
import com.project.shoppingmall.model.response.HandledItemReadStatisticResponse;
import com.project.shoppingmall.model.response.Response;
import com.project.shoppingmall.service.HandledItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HandledItemController {
    private final HandledItemService handledItemService;

    @PostMapping("/payment/READY")
    public Response<Void> createPaymentAsReady(
            @RequestBody PaymentReadyCreateRequest request,
            @AuthenticationPrincipal LoginDto loginDto
    ) {
        handledItemService.createPaymentAsReady(request, loginDto);
        return Response.success();
    }

    @PostMapping("/payment/DONE")
    public Response<Void> createPaymentAsDone(
            @RequestBody PaymentDoneCreateRequest request,
            @AuthenticationPrincipal LoginDto loginDto
    ) {
        handledItemService.createPaymentAsDone(request, loginDto);
        return Response.success();
    }

    @PostMapping("/payment/DONE/{pid}")
    public Response<Void> createPaymentAsDone(
            @PathVariable String pid,
            @AuthenticationPrincipal LoginDto loginDto
    ) {
        handledItemService.createPaymentAsDoneWithId(pid, loginDto);
        return Response.success();
    }

    @PostMapping("/payment/CANCEL")
    public Response<Void> createPaymentAsCancel(
            @RequestBody PaymentCancelCreateRequest request,
            @AuthenticationPrincipal LoginDto loginDto
    ) {
        handledItemService.createPaymentAsCancel(request, loginDto);
        return Response.success();
    }

    @GetMapping("/payment/READY")
    public Response<Page<HandledItemReadResponse>> readPaymentAsReady(@PageableDefault Pageable pageable) {
        Page<HandledItemReadResponse> pages = handledItemService.readPaymentAsReady(pageable);
        return Response.success(pages);
    }

    @GetMapping("/payment/statistic")
    public Response<HandledItemReadStatisticResponse> readPaymentAsStatistic() {
        HandledItemReadStatisticResponse dto = handledItemService.readPaymentAsStatistic();
        return Response.success(dto);
    }

    @DeleteMapping("/payment/READY")
    public Response<Void> deletePaymentAsReady(@ModelAttribute HandledItemDeleteRequest request) {
        handledItemService.deletePaymentAsReady(request);
        return Response.success();
    }

    @GetMapping("/payment/DONE")
    public Response<Page<HandledItemReadResponse>> readPaymentAsDone(@PageableDefault Pageable pageable) {
        Page<HandledItemReadResponse> pages = handledItemService.readPaymentAsDone(pageable);
        return Response.success(pages);
    }

    @GetMapping("/payment/CANCEL")
    public Response<Page<HandledItemReadResponse>> readPaymentAsCancel(@PageableDefault Pageable pageable) {
        Page<HandledItemReadResponse> pages = handledItemService.readPaymentAsCancel(pageable);
        return Response.success(pages);
    }
}
