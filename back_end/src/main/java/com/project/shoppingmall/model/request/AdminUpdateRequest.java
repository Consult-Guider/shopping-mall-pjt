package com.project.shoppingmall.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@NoArgsConstructor @Getter @Setter
public class AdminUpdateRequest {
    private String password;

    private String name;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}|\\d{2}-\\d{3}-\\d{4}$")
    private String phoneNum;
}
