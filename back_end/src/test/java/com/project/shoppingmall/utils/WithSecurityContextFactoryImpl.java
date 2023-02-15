package com.project.shoppingmall.utils;

import com.project.shoppingmall.type.RoleType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithSecurityContextFactoryImpl implements WithSecurityContextFactory<WithAuthenticationPrincipal> {

    @Override
    public SecurityContext createSecurityContext(WithAuthenticationPrincipal annotation) {
        RoleType role = annotation.role();

        UserDetails principal = switch (role) {
            case USER -> FixtureFactory.userDtoFixture;
            case SELLER -> FixtureFactory.sellerDtoFixture;
        };

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
