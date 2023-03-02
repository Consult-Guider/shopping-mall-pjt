package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.AdImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdImgRepository extends JpaRepository<AdImg, Long>, CustomAdImgRepository {
}
