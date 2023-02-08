package com.project.shoppingmall.controller;

import com.project.shoppingmall.model.LoginDto;
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

    @GetMapping("/payment/READY")
    public Response<Page<HandledItemReadResponse>> readPaymentAsReady(@PageableDefault Pageable pageable) {
        Page<HandledItemReadResponse> pages = handledItemService.readPaymentAsReady(pageable);
        return Response.success(pages);
    }

    @GetMapping("/delivery/statistic")
    public Response<HandledItemReadStatisticResponse> readDeliveryAsStatistic() {
        HandledItemReadStatisticResponse dto = handledItemService.readDeliveryAsStatistic();
        return Response.success(dto);
    }

    @GetMapping("/delivery")
    public Response<Page<HandledItemReadResponse>> readDelivery(@PageableDefault Pageable pageable) {
        Page<HandledItemReadResponse> pages = handledItemService.readDelivery(pageable);
        return Response.success(pages);
    }

    @GetMapping("/delivery/READY")
    public Response<Page<HandledItemReadResponse>> readDeliveryAsReady(@PageableDefault Pageable pageable) {
        Page<HandledItemReadResponse> pages = handledItemService.readDeliveryAsReady(pageable);
        return Response.success(pages);
    }

    @GetMapping("/delivery/ONGOING")
    public Response<Page<HandledItemReadResponse>> readDeliveryAsOngoing(@PageableDefault Pageable pageable) {
        Page<HandledItemReadResponse> pages = handledItemService.readDeliveryAsOngoing(pageable);
        return Response.success(pages);
    }

    @GetMapping("/delivery/DONE")
    public Response<Page<HandledItemReadResponse>> readDeliveryAsDone(@PageableDefault Pageable pageable) {
        Page<HandledItemReadResponse> pages = handledItemService.readDeliveryAsDone(pageable);
        return Response.success(pages);
    }
}
