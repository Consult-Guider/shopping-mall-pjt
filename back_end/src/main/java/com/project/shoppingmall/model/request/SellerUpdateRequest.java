package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.Seller;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Pattern;

@NoArgsConstructor @Getter @Setter
public class SellerUpdateRequest {
    private String password;

    private String name;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}|\\d{2}-\\d{3}-\\d{4}$")
    private String phoneNum;
    private String companyName;

    public static Seller overwrite(
            Seller entity,
            SellerUpdateRequest dto,
            PasswordEncoder passwordEncoder
    ) {
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        entity.setName(dto.getName());
        entity.setPhoneNum(dto.getPhoneNum());
        entity.setCompanyName(dto.getCompanyName());
        return entity;
    }
}
