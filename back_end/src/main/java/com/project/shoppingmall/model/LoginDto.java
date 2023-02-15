package com.project.shoppingmall.model;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

public interface LoginDto extends UserDetails {
    Long getId();
    LocalDateTime getCreatedAt();
}
