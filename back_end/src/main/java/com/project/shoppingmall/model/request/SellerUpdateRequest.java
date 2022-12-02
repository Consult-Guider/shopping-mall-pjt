package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.Seller;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Pattern;
import java.util.Optional;

@NoArgsConstructor @Getter @Setter
public class SellerUpdateRequest {
    private String password;

    private String name;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}|\\d{2}-\\d{3}-\\d{4}$")
    private String phoneNum;
    private String companyName;
    private String address;

    public Seller overwrite(
            Seller entity,
            PasswordEncoder passwordEncoder
    ) {
        Optional.ofNullable(this.getPassword())
                .filter(s -> !s.isBlank()).map(passwordEncoder::encode).ifPresent(entity::setPassword);

        Optional.ofNullable(this.getName())
                .filter(s -> !s.isBlank()).ifPresent(entity::setName);
        Optional.ofNullable(this.getPhoneNum())
                .filter(s -> !s.isBlank()).ifPresent(entity::setPhoneNum);
        Optional.ofNullable(this.getCompanyName())
                .filter(s -> !s.isBlank()).ifPresent(entity::setCompanyName);
        Optional.ofNullable(this.getAddress())
                .filter(s -> !s.isBlank()).ifPresent(entity::setAddress);
        return entity;
    }
}
