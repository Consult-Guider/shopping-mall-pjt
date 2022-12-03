package com.project.shoppingmall.model;

import com.project.shoppingmall.domain.User;
import com.project.shoppingmall.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@ToString
// 위는 테스트 편의를 위한 어노테이션
@AllArgsConstructor @Getter @Builder
public class UserDto implements LoginDto {
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    private final String email;
    private final String password;
    private final String name;
    private final String phoneNum;
    private final String address;

    public static UserDto fromEntity(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .name(entity.getName())
                .phoneNum(entity.getPhoneNum())
                .address(entity.getAddress())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority(RoleType.USER.getName());
        return List.of(roleUser);
    }

    @Override public String getUsername() { return email; }

    @Override public boolean isAccountNonExpired() { return deletedAt==null; }
    @Override public boolean isAccountNonLocked() { return deletedAt==null; }
    @Override public boolean isCredentialsNonExpired() { return deletedAt==null; }
    @Override public boolean isEnabled() { return deletedAt==null; }

    public User toEntity() {
        User entity = new User();
        entity.setId(id);
        entity.setCreatedAt(createdAt);
        entity.setDeletedAt(deletedAt);

        entity.setEmail(email);
        entity.setPassword(password);

        entity.setName(name);
        entity.setPhoneNum(phoneNum);
        entity.setAddress(address);
        return entity;
    }
}
