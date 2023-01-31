package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.HandledItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface HandledItemRepository extends
        ElasticsearchRepository<HandledItem, String>,
        KeywordRepository<HandledItem> {
}
