package com.project.shoppingmall.domain;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.time.LocalDateTime;

@ToString
// 위는 테스트 편의를 위한 어노테이션
@EntityListeners(AuditingEntityListener.class)
// 위는 JpaAuditing을 위한 어노테이션
@SQLDelete(sql = "UPDATE `ad_img` SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
// 위는 SOFT DELETE을 위한 어노테이션
@Table(name = "ad_img")
@Entity
@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @Builder(toBuilder = true)
// 위는 JPA Entity 사용을 위한 어노테이션
public class AdImg extends BaseEntity {
    private String itemName;
    private String companyName;
    private String path;
    private String link;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public static AdImg of(AdImg trg) {
        return trg.toBuilder().build();
    }
}
