package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.Seller;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor @Getter @Setter
public class SellerCreateRequest {
    @NotNull @Email
    private String email;
    @NotNull
    private String password;

    @NotNull
    private String name;
    @NotNull @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}|\\d{2}-\\d{3}-\\d{4}$")
    private String phoneNum;
    @NotNull
    private String companyName;

    public static SellerCreateRequest of(String email, String password, String name, String phoneNum, String companyName) {
        SellerCreateRequest createFixture = new SellerCreateRequest();
        createFixture.setEmail(email);
        createFixture.setPassword(password);
        createFixture.setName(name);
        createFixture.setPhoneNum(phoneNum);
        createFixture.setCompanyName(companyName);
        return createFixture;
    }

    public Seller toEntity(PasswordEncoder passwordEncoder) {
        Seller entity = new Seller();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));

        entity.setName(name);
        entity.setPhoneNum(phoneNum);
        entity.setCompanyName(companyName);
        return entity;
    }
}
