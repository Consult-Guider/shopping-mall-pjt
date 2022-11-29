package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long>, LoginRepository<Admin> {
}
