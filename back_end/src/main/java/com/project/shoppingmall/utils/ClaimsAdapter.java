package com.project.shoppingmall.utils;

import com.project.shoppingmall.type.RoleType;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClaimsAdapter {
    private final Claims claims;

    public static final String ID = "id";
    public static final String IDENTIFIER = "email";
    public static final String ROLE = "role";

    public static ClaimsAdapter of(Claims claims) { return new ClaimsAdapter(claims); }
    public Claims getClaims() { return claims; }

    public Long getId() {
        return claims.get(ID, Long.class);
    }

    public void setId(Long id) {
        claims.put(ID, id);
    }

    public String getIdentifier() {
        return claims.get(IDENTIFIER, String.class);
    }

    public void setIdentifier(String email) {
        claims.put(IDENTIFIER, email);
    }

    public RoleType getRole() {
        return RoleType.findByName(claims.get(ROLE, String.class));
    }

    public void setRole(RoleType role) {
        claims.put(ROLE, role.getName());
    }
}
