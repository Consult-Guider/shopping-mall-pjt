package com.project.shoppingmall.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_account")
@Entity
@AllArgsConstructor @SuperBuilder(toBuilder = true)
@NoArgsConstructor @Getter @Setter
public class User extends LoginEntity {
    private String name;
    private String phoneNum;
    private String address;

    public static User of(User trg) {
        return trg.toBuilder().build();
    }
}
