package com.project.shoppingmall.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor @Getter @Setter
@MappedSuperclass @SuperBuilder(toBuilder = true)
public abstract class LoginEntity extends BaseEntity {
    private String email;
    private String password;
}
