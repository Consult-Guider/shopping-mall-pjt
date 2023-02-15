package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.HandledItem;
import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.SellerDto;
import com.project.shoppingmall.model.UserDto;
import com.project.shoppingmall.model.request.HandledItemDeleteRequest;
import com.project.shoppingmall.model.request.PaymentCancelCreateRequest;
import com.project.shoppingmall.model.request.PaymentDoneCreateRequest;
import com.project.shoppingmall.model.request.PaymentReadyCreateRequest;
import com.project.shoppingmall.model.response.HandledItemReadResponse;
import com.project.shoppingmall.model.response.HandledItemReadStatisticResponse;
import com.project.shoppingmall.repository.HandledItemRepository;
import com.project.shoppingmall.repository.ItemRepository;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.type.ProcessType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

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

    private HandledItem loadHandledItemById(String pid) {
        return handledItemRepository.findById(pid).orElseThrow(() ->
                new CrudException(ErrorCode.HANDLED_ITEM_NOT_FOUNDED, String.format("조회를 시도한 Id: %s", pid))
        );
    }

    @Override
    public void createPaymentAsReady(PaymentReadyCreateRequest request, LoginDto loginDto) {
        UserDto userDto = (UserDto) loginDto;
        HandledItem entity = request.toEntity(userDto.toEntity(), this::loadItemById);
        handledItemRepository.save(entity);
    }

    public <U> U doGivenRole(
            LoginDto loginDto,
            Supplier<U> runWhenUser, Supplier<U> runWhenSeller
            ) {
        if(loginDto instanceof UserDto) {
            return runWhenUser.get();
        } else if(loginDto instanceof SellerDto) {
            return runWhenSeller.get();
        } else {
            throw new AuthenticationException(ErrorCode.INVALID_ROLETYPE);
        }
    }

    @Override
    public void createPaymentAsDone(PaymentDoneCreateRequest request, LoginDto loginDto) {
        List<String> itemIds = request.getItemIds();
        List<HandledItem> entitiesLoaded = (List<HandledItem>) handledItemRepository.findAllById(itemIds);

        // 해당 HandledItem 엔티티의 사용자와 요청자가 같은지 확인.
        List<HandledItem> entitiesChanged = entitiesLoaded.stream().map(entity -> {
            if (entity.getUser().getId().equals(loginDto.getId())) {
                entity.setProcessType(ProcessType.DONE);
                return entity;
            } else {
                throw new AuthenticationException(ErrorCode.FORBIDDEN);
            }
        }).toList();

        handledItemRepository.saveAll(entitiesChanged);
    }

    @Override
    public void createPaymentAsCancel(PaymentCancelCreateRequest request, LoginDto loginDto) {
        List<String> itemIds = request.getItemIds();
        List<HandledItem> entitiesLoaded = (List<HandledItem>) handledItemRepository.findAllById(itemIds);

        // 해당 HandledItem 엔티티의 사용자와 요청자가 같은지 확인.
        List<HandledItem> entitiesChanged = entitiesLoaded.stream().map(entity -> {
            if (entity.getUser().getId().equals(loginDto.getId())) {
                entity.setProcessType(ProcessType.CANCEL);
                return entity;
            } else {
                throw new AuthenticationException(ErrorCode.FORBIDDEN);
            }
        }).toList();

        handledItemRepository.saveAll(entitiesChanged);
    }

    @Override
    public Page<HandledItemReadResponse> readPaymentAsReady(LoginDto loginDto, Pageable pageable) {
        return doGivenRole(loginDto,
                () -> handledItemRepository.findXxxByUser(ProcessType.READY, loginDto.getId(), pageable),
                () -> handledItemRepository.findXxxBySeller(ProcessType.READY, loginDto.getId(), pageable)
        ).map(HandledItemReadResponse::fromEntity);
    }

    @Override
    public void deletePaymentAsReady(HandledItemDeleteRequest request) {
        handledItemRepository.deleteAllById(request.getItemIds());
    }

    @Override
    public Page<HandledItemReadResponse> readPaymentAsDone(LoginDto loginDto, Pageable pageable) {
        return doGivenRole(loginDto,
                () -> handledItemRepository.findXxxByUser(ProcessType.DONE, loginDto.getId(), pageable),
                () -> handledItemRepository.findXxxBySeller(ProcessType.DONE, loginDto.getId(), pageable)
        ).map(HandledItemReadResponse::fromEntity);
    }

    @Override
    public Page<HandledItemReadResponse> readPaymentAsCancel(LoginDto loginDto, Pageable pageable) {
        return doGivenRole(loginDto,
                () -> handledItemRepository.findXxxByUser(ProcessType.CANCEL, loginDto.getId(), pageable),
                () -> handledItemRepository.findXxxBySeller(ProcessType.CANCEL, loginDto.getId(), pageable)
        ).map(HandledItemReadResponse::fromEntity);
    }

    private long getLong(Long value) {
        return Optional.ofNullable(value).orElse(0L);
    }

    @Override
    public HandledItemReadStatisticResponse readPaymentAsStatistic(LoginDto loginDto) {
        HandledItemReadStatisticResponse response = new HandledItemReadStatisticResponse();
        Map<String, Long> mapCount = doGivenRole(loginDto,
                () -> handledItemRepository.countPaymentByProcessTypeWithUserId(loginDto.getId()),
                () -> handledItemRepository.countPaymentByProcessTypeWithSellerId(loginDto.getId())
        );
        response.setCountDeliveryByReady(getLong(mapCount.get(ProcessType.READY.name())));
        response.setCountDeliveryByDONE(getLong(mapCount.get(ProcessType.DONE.name())));
        response.setCountDeliveryByCancel(getLong(mapCount.get(ProcessType.CANCEL.name())));
        return response;
    }

    @Override
    public void createPaymentAsDoneWithId(String pid, LoginDto loginDto) {
        HandledItem entitySaved = loadHandledItemById(pid);
        entitySaved.setProcessType(ProcessType.DONE);
        handledItemRepository.save(entitySaved);
    }
}
