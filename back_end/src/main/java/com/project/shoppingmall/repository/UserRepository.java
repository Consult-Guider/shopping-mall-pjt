package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, LoginRepository<User> {
}
