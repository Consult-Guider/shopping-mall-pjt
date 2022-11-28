package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.Admin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor @Getter @Setter
public class AdminCreateRequest {
    @NotNull @Email
    private String email;
    @NotNull
    private String password;

    @NotNull
    private String name;
    @NotNull @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}|\\d{2}-\\d{3}-\\d{4}$")
    private String phoneNum;

    public static AdminCreateRequest of(
            String email, String password, String name, String phoneNum
    ) {
        AdminCreateRequest createFixture = new AdminCreateRequest();
        createFixture.setEmail(email);
        createFixture.setPassword(password);
        createFixture.setName(name);
        createFixture.setPhoneNum(phoneNum);
        return createFixture;
    }

    public Admin toEntity(PasswordEncoder passwordEncoder) {
        Admin entity = new Admin();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));

        entity.setName(name);
        entity.setPhoneNum(phoneNum);
        return entity;
    }
}
