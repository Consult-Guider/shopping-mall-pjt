package com.project.shoppingmall.model;

import com.project.shoppingmall.domain.Admin;
import com.project.shoppingmall.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@ToString
// 위는 테스트 편의를 위한 어노테이션
@AllArgsConstructor @Getter @Builder
public class AdminDto implements UserDetails {
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    private final String email;
    private final String password;
    private final String name;
    private final String phoneNum;

    public static AdminDto fromEntity(Admin entity) {
        return AdminDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .name(entity.getName())
                .phoneNum(entity.getPhoneNum())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority(RoleType.ADMIN.getName());
        return List.of(roleUser);
    }

    @Override public String getUsername() { return email; }

    @Override public boolean isAccountNonExpired() { return deletedAt==null; }
    @Override public boolean isAccountNonLocked() { return deletedAt==null; }
    @Override public boolean isCredentialsNonExpired() { return deletedAt==null; }
    @Override public boolean isEnabled() { return deletedAt==null; }

    public Admin toEntity() {
        Admin entity = new Admin();
        entity.setId(id);
        entity.setCreatedAt(createdAt);
        entity.setDeletedAt(deletedAt);

        entity.setEmail(email);
        entity.setPassword(password);

        entity.setName(name);
        entity.setPhoneNum(phoneNum);
        return entity;
    }
}
