package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.AdImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExceptOverdueAdImgRepository {
    Page<AdImg> findAllValid(Pageable pageable);
}
