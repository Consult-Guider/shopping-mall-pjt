package com.project.shoppingmall.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

//TODO: 어드민 제거 생각
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "manager")
@Entity
@AllArgsConstructor @SuperBuilder(toBuilder = true)
@NoArgsConstructor @Getter @Setter
public class Admin extends LoginEntity {
    private String name;
    private String phoneNum;

    public static Admin of(Admin trg) {
        return trg.toBuilder().build();
    }
}
