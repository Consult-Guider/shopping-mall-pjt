package com.project.shoppingmall.type;

import com.project.shoppingmall.exception.AuthenticationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum RoleType {
    USER("ROLE_USER"),
    SELLER("ROLE_SELLER"),
    ADMIN("ROLE_ADMIN")
    ;

    private final String name;

    public static RoleType findByName(String name) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.getName().equals(name)) { return roleType; }
        }
        throw new AuthenticationException(
                ErrorCode.INVALID_ROLETYPE,
                String.format("요구한 RoleType :%s", name)
        );
    }
}
