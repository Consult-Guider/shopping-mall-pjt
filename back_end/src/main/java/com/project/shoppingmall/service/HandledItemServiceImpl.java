package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.HandledItem;
import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Seller;
import com.project.shoppingmall.domain.nested.Description;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.HandledItemCreateRequest;
import com.project.shoppingmall.repository.HandledItemRepository;
import com.project.shoppingmall.repository.SellerRepository;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.type.ImageType;
import com.project.shoppingmall.type.RoleType;
import com.project.shoppingmall.utils.fileManager.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.project.shoppingmall.utils.fileManager.S3FileNameManager.makeFileName;

@Service
@Transactional
@RequiredArgsConstructor
public class HandledItemServiceImpl implements HandledItemService {
    private final HandledItemRepository HandleditemRepository;
    private final SellerRepository sellerRepository;
    private final FileManager<MultipartFile, String> fileManager; //TODO: 이거 정확한 역할 모르겠음

    //상품  id로 조회
    public Item loadItemById(String id) {
        return HandleditemRepository.findById(id).orElseThrow(() -> new CrudException(
                ErrorCode.ITEM_NOT_FOUNDED, String.format("조회를 시도한 Id: %s", id)
        ));
    }

    // 판매자 id로 조회
    public Seller loadSellerById(Long id) {
        return sellerRepository.findById(id).orElseThrow(() -> new CrudException(
                ErrorCode.ACCOUNT_NOT_FOUNDED, String.format("조회를 시도한 Id: %s", id)
        ));
    }

    // 로그인 정보
    public void assertPossession(LoginDto principal, String iid) {
        Long uid = principal.getId();

        for(GrantedAuthority role : principal.getAuthorities()) {
            if(role.getAuthority().equals(RoleType.ADMIN.getName())) { return ; }
        }

        HandleditemRepository.findById(iid)
                .map(HandledItem::getSeller).filter(seller -> seller.equals(uid))
                .orElseThrow(() -> new CrudException(
                    ErrorCode.NO_OWNERSHIP, String.format("조회를 시도한 UId: %s, Iid: %s", uid, iid)
        ));
    }

    //이미지 관련
    public String saveFile(MultipartFile src, String fileName) {
        try {
            return fileManager.saveFile(src, fileName);
        } catch (IOException e) {
            throw new CrudException(ErrorCode.ITEM_IMAGE_IOException);
        }
    }

    //TODO: 이부분만 풀리면 나머지는 풀릴 듯

    // 구매/반품 데이터 DB에 올리기
    public void create(HandledItemCreateRequest request) {
        // 구매/반품 상품 도메인 저장.
        HandledItem entity = HandleditemRepository.save(request.toEntity());
        String iid = entity.getId();

        // 상품 대표 이미지 or 설명 이미지 존재 여부 - id로 기존 Item 테이블에 접근하여 처리(시도 중)
        Boolean isThereMainImage = !request.getImage().isEmpty();
        Boolean isThereDescriptionImage = !request.getDescriptionList().isEmpty();

        // 상품 대표 이미지 저장.
        if (isThereMainImage) {
            String trgPath = makeFileName(request.getImage(), Item.class, iid, ImageType.MAIN.getName());
            entity.setImagePath(saveFile(request.getImage(), trgPath));
        }

        // 상품 설명 이미지 저장.
        if (isThereDescriptionImage) {
            List<MultipartFile> descriptionList = request.getDescriptionList();
            for(int i = 0; i < descriptionList.size(); i++) {
                MultipartFile descriptionFile = descriptionList.get(i);
                String imgPath = makeFileName(descriptionFile, Item.class, iid, ImageType.DESC.getName(i));
                Description description = Description.builder().path(saveFile(descriptionFile, imgPath)).build();
                entity.addDescriptionList(description);
            }
        }

        // 이미지 재저장.
        if (isThereMainImage || isThereDescriptionImage) {
            HandleditemRepository.save(entity);
        }
    }

    @Transactional(readOnly = true)
    public HandledItemReadResponse read(String id) {
        Item item = loadItemById(id);
        Seller seller = loadSellerById(item.getSeller());
        return HandledItemReadResponse.fromEntity(item, seller);
    }

    @Transactional(readOnly = true)
    public HandledItemReadResponse readItemAll(String id) {
        Item item = loadItemById(id);
        Seller seller = loadSellerById(item.getSeller());
        return HandledItemReadResponse.allFromEntity(item, seller);
    }

    @Override
    public void update(String uid, HandledItemUpdateRequest itemUpdateRequest, LoginDto principal) {
        assertPossession(principal, uid);
        HandledItem entityOverwritten = HandleditemUpdateRequest.overwrite(loadItemById(uid));
        HandleditemRepository.save(entityOverwritten);
    }

    @Override
    public void delete(String uid, LoginDto principal) {
        assertPossession(principal, uid);
        HandleditemRepository.delete(loadItemById(uid));
    }

    @Transactional(readOnly = true)
    public Page<HandledItemReadResponse> searchItem(String keyword, Pageable pageable) {
        return HandleditemRepository.findByKeyword(keyword, pageable).map(HandledItemReadResponse::fromEntity);
    }
}
