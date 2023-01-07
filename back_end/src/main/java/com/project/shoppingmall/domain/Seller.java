package com.project.shoppingmall.domain;

import lombok.*;
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
@SQLDelete(sql = "UPDATE `seller` SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
// 위는 SOFT DELETE을 위한 어노테이션
@Table(name = "seller")
@Entity
@AllArgsConstructor @Builder(toBuilder = true)
@NoArgsConstructor @Getter @Setter
// 위는 JPA Entity 사용을 위한 어노테이션
public class Seller extends LoginEntity {
    private String name;
    private String companyName;
    private String phoneNum;
    private String address;

    public static Seller of(Seller trg) {
        return trg.toBuilder().build();
    }
}
