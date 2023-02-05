package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Review;
import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.UserDto;
import com.project.shoppingmall.model.request.ReviewCreateRequest;
import com.project.shoppingmall.model.request.ReviewUpdateRequest;
import com.project.shoppingmall.model.response.ReviewReadResponse;
import com.project.shoppingmall.model.response.ReviewSearchResponse;
import com.project.shoppingmall.model.response.ReviewStatisticsResponse;
import com.project.shoppingmall.repository.ItemRepository;
import com.project.shoppingmall.repository.ReviewRepository;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
//    private final TransactionService transactionService;

//    private Transaction loadTransactionById(String iid) {
//        return transactionService.loadTransactionById(iid);
//    }

    private UserDto castToUserDto(LoginDto dto) {
        if(dto instanceof UserDto) {
            return (UserDto) dto;
        } else {
            throw new CrudException(ErrorCode.ONLY_USER_CAN_ACCESS);
        }
    }

    private Review loadReviewById(String rid) {
        return reviewRepository.findById(rid)
                .orElseThrow(() ->
                        new CrudException(
                                ErrorCode.REVIEW_NOT_FOUNDED,
                                String.format("조회에 사용된 ID: %s", rid)
                        )
                );
    }

    private Item loadItemById(String iid) {
        return itemRepository.findById(iid)
                .orElseThrow(() ->
                        new CrudException(
                                ErrorCode.ITEM_NOT_FOUNDED,
                                String.format("조회를 시도한 Id: %s", iid)
                        )
                );
    }

    private void isReviewYours(LoginDto loginDto, Review entity) {
        if(!loginDto.getId().equals(entity.getUserId())) {
            throw new CrudException(
                    ErrorCode.NO_OWNERSHIP,
                    String.format("로그인된 유저 아이디: %s\n리뷰의 작성자 아이디: %s", loginDto.getId(), entity.getUserId())
            );
        }
    }

    @Override
    public void create(String iid, ReviewCreateRequest request, LoginDto loginDto) {
//        Transaction transaction = loadTransactionByIdAndUid(iid, loginDto.getId());
        UserDto userDto = castToUserDto(loginDto);
        Item item = loadItemById(iid);

        Review entity = Review.builder()
                .rating(request.getRating())
                .content(request.getContent())

                .userId(userDto.getId())
                .userName(userDto.getName())

                .itemId(iid)
                .itemName(item.getName())
                .itemSellerId(item.getSeller())
//                .option(transaction.getOption)
                .build();

        reviewRepository.save(entity);
    }

    @Override
    public Page<ReviewReadResponse> readByIid(String iid, Pageable pageable) {
        return reviewRepository.findReviewByItemId(iid, pageable)
                .map(ReviewReadResponse::fromEntity);
    }

    @Override
    public Page<ReviewReadResponse> readByUid(LoginDto loginDto, Pageable pageable) {
        Page<Review> entities;
        Long uid = loginDto.getId();
        List<RoleType> roleTypes = loginDto.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(RoleType::findByName)
                .toList();

        if(roleTypes.contains(RoleType.USER)) {
            entities = reviewRepository.findReviewByUserId(uid, pageable);
        } else if(roleTypes.contains(RoleType.SELLER)) {
            entities = reviewRepository.findReviewBySellerId(uid, pageable);
        } else {
            throw new AuthenticationException(ErrorCode.INVALID_ROLETYPE);
        }
        return entities.map(ReviewReadResponse::fromEntity);
    }

    @Override
    public Page<ReviewSearchResponse> searchByKeyword(String keyword, Pageable pageable) {
        return reviewRepository.searchReviewByKeyword(keyword, pageable)
                .map(ReviewSearchResponse::fromEntity);
    }

    @Override
    public void update(String rid, ReviewUpdateRequest request, LoginDto loginDto) {
        Review entity = loadReviewById(rid);
        Review entityOverWritten = request.overwrite(entity);

        isReviewYours(loginDto, entity);
        reviewRepository.save(entityOverWritten);
    }

    @Override
    public void delete(String rid, LoginDto loginDto) {
        Review entity = loadReviewById(rid);

        isReviewYours(loginDto, entity);
        reviewRepository.delete(entity);
    }

    @Override
    public ReviewStatisticsResponse calculate(String iid) {
        ReviewStatisticsResponse dto = new ReviewStatisticsResponse();
        dto.setAverage(reviewRepository.getAverageByItemId(iid));
        return dto;
    }
}
