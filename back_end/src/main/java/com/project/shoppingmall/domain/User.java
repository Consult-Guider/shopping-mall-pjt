package com.project.shoppingmall.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@ToString
// 위는 테스트 편의를 위한 어노테이션
@EntityListeners(AuditingEntityListener.class)
// 위는 JpaAuditing을 위한 어노테이션
@SQLDelete(sql = "UPDATE `user_account` SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
// 위는 SOFT DELETE을 위한 어노테이션
@Table(name = "user_account")
@Entity
@AllArgsConstructor @SuperBuilder(toBuilder = true)
@NoArgsConstructor @Getter @Setter
// 위는 JPA Entity 사용을 위한 어노테이션
public class User extends LoginEntity {
    private String name;
    private String phoneNum;
    private String address;

    public static User of(User trg) {
        return trg.toBuilder().build();
    }
}
