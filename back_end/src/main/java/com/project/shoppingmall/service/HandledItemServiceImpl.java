package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.HandledItem;
import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.UserDto;
import com.project.shoppingmall.model.request.PaymentDoneCreateRequest;
import com.project.shoppingmall.model.request.PaymentReadyCreateRequest;
import com.project.shoppingmall.model.response.HandledItemReadResponse;
import com.project.shoppingmall.model.response.HandledItemReadStatisticResponse;
import com.project.shoppingmall.repository.HandledItemRepository;
import com.project.shoppingmall.repository.ItemRepository;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.type.HandledType;
import com.project.shoppingmall.type.ProcessType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class HandledItemServiceImpl implements HandledItemService {
    private final HandledItemRepository handledItemRepository;
    private final ItemRepository itemRepository;

    private Item loadItemById(String iid) {
        return itemRepository.findById(iid).orElseThrow(() ->
                new CrudException(ErrorCode.ITEM_NOT_FOUNDED, String.format("조회를 시도한 Id: %s", iid))
        );
    }

    @Override
    public void createPaymentAsReady(PaymentReadyCreateRequest request, LoginDto loginDto) {
        UserDto userDto = (UserDto) loginDto;
        HandledItem entity = request.toEntity(userDto.toEntity(), this::loadItemById);
        handledItemRepository.save(entity);
    }

    @Override
    public void createPaymentAsDone(PaymentDoneCreateRequest request, LoginDto loginDto) {
        UserDto userDto = (UserDto) loginDto;
        HandledItem entity = request.toEntity(userDto.toEntity(), this::loadItemById);
        handledItemRepository.save(entity);
    }

    @Override
    public Page<HandledItemReadResponse> readPaymentAsReady(Pageable pageable) {
        return handledItemRepository.findXxxAs(HandledType.PAYMENT, ProcessType.READY, pageable)
                .map(HandledItemReadResponse::fromEntity);
    }

    @Override
    public HandledItemReadStatisticResponse readDeliveryAsStatistic() {
        HandledItemReadStatisticResponse response = new HandledItemReadStatisticResponse();
        Map<String, Long> mapCount = handledItemRepository.countDeliveryByProcessType();
        response.setCountDeliveryByReady(mapCount.get(ProcessType.READY.getName()));
        response.setCountDeliveryByOnGoing(mapCount.get(ProcessType.ONGOING.getName()));
        response.setCountDeliveryByDONE(mapCount.get(ProcessType.DONE.getName()));
        return response;
    }

    @Override
    public Page<HandledItemReadResponse> readDelivery(Pageable pageable) {
        return handledItemRepository.findXxxAs(HandledType.DELIVERY, null, pageable)
                .map(HandledItemReadResponse::fromEntity);
    }

    @Override
    public Page<HandledItemReadResponse> readDeliveryAsReady(Pageable pageable) {
        return handledItemRepository.findXxxAs(HandledType.DELIVERY, ProcessType.READY, pageable)
                .map(HandledItemReadResponse::fromEntity);
    }

    @Override
    public Page<HandledItemReadResponse> readDeliveryAsOngoing(Pageable pageable) {
        return handledItemRepository.findXxxAs(HandledType.DELIVERY, ProcessType.ONGOING, pageable)
                .map(HandledItemReadResponse::fromEntity);
    }

    @Override
    public Page<HandledItemReadResponse> readDeliveryAsDone(Pageable pageable) {
        return handledItemRepository.findXxxAs(HandledType.DELIVERY, ProcessType.DONE, pageable)
                .map(HandledItemReadResponse::fromEntity);
    }
}
