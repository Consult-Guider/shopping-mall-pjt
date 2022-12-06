package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.Item;
import com.project.shoppingmall.domain.Seller;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.LoginDto;
import com.project.shoppingmall.model.request.ItemCreateRequest;
import com.project.shoppingmall.model.request.ItemUpdateRequest;
import com.project.shoppingmall.model.response.ItemReadResponse;
import com.project.shoppingmall.repository.ItemRepository;
import com.project.shoppingmall.repository.SellerRepository;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final SellerRepository sellerRepository;

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

    public void create(ItemCreateRequest request) {
        itemRepository.save(request.toEntity());
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
