package com.project.shoppingmall.config.filter;

import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.service.UserDetailServiceFactory;
import com.project.shoppingmall.utils.ClaimsAdapter;
import com.project.shoppingmall.utils.JwtTokenDealer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailServiceFactory userDetailServiceFactory;
    private final String secretKey;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            // Request로 부터 jwt 토큰 추출.
            String header = JwtTokenDealer.extractTokenFrom(request);

            // jwt 토큰 검증.
            ClaimsAdapter claimsAdapter = ClaimsAdapter.of(
                    JwtTokenDealer.getClaimsWithValidation(header, secretKey)
            );

            // Authentication 객체 생성.
            Authentication auth = JwtTokenDealer.getAuthentication(
                    claimsAdapter.getIdentifier(),
                    userDetailServiceFactory.getService(claimsAdapter.getRole())
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (AuthenticationException e) {
            log.error(e.printErrorMessage());

        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
