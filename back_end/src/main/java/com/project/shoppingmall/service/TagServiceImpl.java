package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.nested.Tag;
import com.project.shoppingmall.model.response.ItemReadResponse;
import com.project.shoppingmall.repository.ItemRepository;
import com.project.shoppingmall.utils.RandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final ItemRepository itemRepository;

    @Override
    public Page<Tag> getRandomTags(Pageable pageable) {
        List<Tag> tags = itemRepository.findDistinctTag();
        List<Tag> tagsSelected = RandomUtils.sample(tags, pageable.getPageSize());
        return new PageImpl<>(tagsSelected, pageable, tagsSelected.size());
    }

    @Override
    public Page<ItemReadResponse> getItems(String tagName, Pageable pageable) {
        return itemRepository.findByTagName(tagName, pageable)
                .map(ItemReadResponse::fromEntity);
    }
}
