package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Seller;
import com.project.shoppingmall.domain.nested.Description;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.ItemCreateRequest;
import com.project.shoppingmall.model.request.ItemUpdateRequest;
import com.project.shoppingmall.model.response.ItemReadResponse;
import com.project.shoppingmall.repository.ItemRepository;
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
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final SellerRepository sellerRepository;
    private final FileManager<MultipartFile, String> fileManager;

    public Item loadItemById(String id) {
        return itemRepository.findById(id).orElseThrow(() -> new CrudException(
                ErrorCode.ITEM_NOT_FOUNDED, String.format("조회를 시도한 Id: %s", id)
        ));
    }

    public Seller loadSellerById(Long id) {
        return sellerRepository.findById(id).orElseThrow(() -> new CrudException(
                ErrorCode.ACCOUNT_NOT_FOUNDED, String.format("조회를 시도한 Id: %s", id)
        ));
    }

    public void assertPossession(LoginDto principal, String iid) {
        Long uid = principal.getId();

        for(GrantedAuthority role : principal.getAuthorities()) {
            if(role.getAuthority().equals(RoleType.ADMIN.getName())) { return ; }
        }

        itemRepository.findById(iid)
                .map(Item::getSeller).filter(seller -> seller.equals(uid))
                .orElseThrow(() -> new CrudException(
                    ErrorCode.NO_OWNERSHIP, String.format("조회를 시도한 UId: %s, Iid: %s", uid, iid)
        ));
    }

    public String saveFile(MultipartFile src, String fileName) {
        try {
            return fileManager.saveFile(src, fileName);
        } catch (IOException e) {
            throw new CrudException(ErrorCode.ITEM_IMAGE_IOException);
        }
    }

    public void create(ItemCreateRequest request) {
        // 상품 도메인 저장.
        Item entity = itemRepository.save(request.toEntity());
        String iid = entity.getId();

        // 상품 대표 이미지 or 설명 이미지 존재 여부
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
            itemRepository.save(entity);
        }
    }

    @Transactional(readOnly = true)
    public ItemReadResponse read(String id) {
        Item item = loadItemById(id);
        Seller seller = loadSellerById(item.getSeller());
        return ItemReadResponse.fromEntity(item, seller);
    }

    @Transactional(readOnly = true)
    public ItemReadResponse readItemAll(String id) {
        Item item = loadItemById(id);
        Seller seller = loadSellerById(item.getSeller());
        return ItemReadResponse.allFromEntity(item, seller);
    }

    @Override
    public void update(String uid, ItemUpdateRequest itemUpdateRequest, LoginDto principal) {
        assertPossession(principal, uid);
        Item entityOverwritten = itemUpdateRequest.overwrite(loadItemById(uid));
        itemRepository.save(entityOverwritten);
    }

    @Override
    public void delete(String uid, LoginDto principal) {
        assertPossession(principal, uid);
        itemRepository.delete(loadItemById(uid));
    }

    @Transactional(readOnly = true)
    public Page<ItemReadResponse> searchItem(String keyword, Pageable pageable) {
        return itemRepository.findByKeyword(keyword, pageable).map(ItemReadResponse::fromEntity);
    }
}
