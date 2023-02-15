package com.project.shoppingmall.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "seller")
@Entity
@AllArgsConstructor @SuperBuilder(toBuilder = true)
@NoArgsConstructor @Getter @Setter
public class Seller extends LoginEntity {
    private String name;
    private String companyName;
    private String phoneNum;
    private String address;

    public static Seller of(Seller trg) {
        return trg.toBuilder().build();
    }
}
