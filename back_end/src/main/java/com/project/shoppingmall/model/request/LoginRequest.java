package com.project.shoppingmall.model.request;

import com.project.shoppingmall.type.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class LoginRequest {
    private RoleType role;
    private String email;
    private String password;
}
