package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Seller;
import com.project.shoppingmall.domain.nested.Description;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.ItemCreateRequest;
import com.project.shoppingmall.model.request.ItemUpdateRequest;
import com.project.shoppingmall.model.response.ItemReadResponse;
import com.project.shoppingmall.model.response.SearchResponse;
import com.project.shoppingmall.repository.ItemRepository;
import com.project.shoppingmall.repository.SellerRepository;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.type.ImageType;
import com.project.shoppingmall.utils.fileManager.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.project.shoppingmall.utils.fileManager.S3FileNameManager.makeFileName;
import static java.util.Optional.ofNullable;
import static java.util.stream.IntStream.range;

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
        AtomicBoolean flagForSaving = new AtomicBoolean(false);

        // 상품 대표 이미지 저장.
        ofNullable(request.getImage())
                .filter(multipartFile -> !multipartFile.isEmpty())
                .ifPresent(mainImage -> {
                    String trgPath = makeFileName(mainImage, Item.class, iid, ImageType.MAIN.getName());
                    entity.setImagePath(saveFile(mainImage, trgPath));

                    flagForSaving.set(true);
                });

        // 상품 설명 이미지 저장.
        List<MultipartFile> descriptionList = ofNullable(request.getDescriptionList()).orElse(List.of());
        range(0, descriptionList.size()).boxed()
                .collect(Collectors.toMap(Function.identity(), descriptionList::get))
                .forEach((i, descriptionFile) -> {
                    String imgPath = makeFileName(
                            descriptionFile, Item.class, iid,
                            ImageType.DESC.getName(i)
                    );
                    entity.addDescriptionList(
                            Description.builder()
                                    .path(saveFile(descriptionFile, imgPath))
                                    .build()
                    );
                });
        flagForSaving.compareAndSet(false, !descriptionList.isEmpty());

        // 이미지 재저장.
        if (flagForSaving.get()) {
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
    @Override
    public Page<SearchResponse> searchByKeyword(String keyword, Pageable pageable) {
        return itemRepository.findByKeyword(keyword, pageable).map(SearchResponse::fromEntity);
    }
}
