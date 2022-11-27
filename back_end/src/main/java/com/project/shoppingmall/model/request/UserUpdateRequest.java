package com.project.shoppingmall.model.request;

import com.project.shoppingmall.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Pattern;

@NoArgsConstructor @Getter @Setter
public class UserUpdateRequest {
    private String password;

    private String name;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}|\\d{2}-\\d{3}-\\d{4}$")
    private String phoneNum;
    private String address;

    public static UserUpdateRequest of(String password, String name, String phoneNum, String address) {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setPassword(password);
        request.setName(name);
        request.setPhoneNum(phoneNum);
        request.setAddress(address);
        return request;
    }

    public static User overwrite(
            User entity,
            UserUpdateRequest dto,
            PasswordEncoder passwordEncoder
    ) {
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        entity.setName(dto.getName());
        entity.setPhoneNum(dto.getPhoneNum());
        entity.setAddress(dto.getAddress());
        return entity;
    }
}