package com.project.shoppingmall.domain;

import com.project.shoppingmall.type.RoleType;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
// 위는 JpaAuditing을 위한 어노테이션
@SQLDelete(sql = "UPDATE `account` SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
// 위는 SOFT DELETE을 위한 어노테이션
@Table(name = "account")
@Entity
@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @Builder
// 위는 JPA Entity 사용을 위한 어노테이션
public class Seller implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    private String email;
    private String password;
    private String name;
    private String companyName;
    private String phoneNum;
    private String address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority(RoleType.SELLER.getName());
        return List.of(roleUser);
    }

    @Override
    public String getUsername() { return email; }

    @Override public boolean isAccountNonExpired() { return deletedAt==null; }
    @Override public boolean isAccountNonLocked() { return deletedAt==null; }
    @Override public boolean isCredentialsNonExpired() { return deletedAt==null; }
    @Override public boolean isEnabled() { return deletedAt==null; }
}
