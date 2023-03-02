package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.AdImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomAdImgRepository {
    Page<AdImg> findAllValid(Pageable pageable);

    Optional<AdImg> findOneRandomly();
}
