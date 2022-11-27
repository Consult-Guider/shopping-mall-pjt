package com.project.shoppingmall.model.request;

import com.project.shoppingmall.type.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NoArgsConstructor @Getter @Setter
public class LoginRequest {
    @NotNull
    private RoleType role;
    @NotNull @Email
    private String email;
    @NotNull
    private String password;
}
