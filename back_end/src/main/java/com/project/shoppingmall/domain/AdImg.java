package com.project.shoppingmall.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.time.LocalDateTime;

@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ad_img")
@Entity
@NoArgsConstructor @Getter @Setter
@AllArgsConstructor @SuperBuilder(toBuilder = true)
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
