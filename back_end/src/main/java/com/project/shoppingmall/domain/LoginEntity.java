package com.project.shoppingmall.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor @Getter @Setter
@MappedSuperclass
public abstract class LoginEntity extends BaseEntity {
    private String email;
    private String password;
}
