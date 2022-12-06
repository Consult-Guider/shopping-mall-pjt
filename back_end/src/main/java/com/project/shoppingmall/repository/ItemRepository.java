package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemRepository extends
        ElasticsearchRepository<Item, String>,
        KeywordRepository<Item> {
}
