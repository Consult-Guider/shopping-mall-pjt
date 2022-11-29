package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long>, LoginRepository<Seller> {
}
