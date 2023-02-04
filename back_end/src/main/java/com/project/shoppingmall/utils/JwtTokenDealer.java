package com.project.shoppingmall.utils;

import com.project.shoppingmall.domain.LoginEntity;
import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.type.RoleType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenDealer {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    public static String extractTokenFrom(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (!StringUtils.hasText(header)) {
            throw new AuthenticationException(ErrorCode.NO_AUTHENTICATION_IN_HEADER);
        }
        if (!header.startsWith(BEARER_PREFIX)) {
            throw  new AuthenticationException(ErrorCode.IS_NOT_BEARER_TOKEN);
        }
        return header.substring(BEARER_PREFIX.length());
    }

    public static Claims getClaimsWithValidation(String token, String secretKey) {
        try {
            return getClaims(token, secretKey);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new AuthenticationException(ErrorCode.MALFORMED_JWT);
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException(ErrorCode.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            throw new AuthenticationException(ErrorCode.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException(ErrorCode.ILLEGALARGUMENT_JWT);
        } finally {
            log.trace("token: {}", token);
        }
    }

    private static Claims getClaims(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Authentication getAuthentication(
            String identifier,
            UserDetailsService userDetailsService
    ) {
        UserDetails principal = userDetailsService.loadUserByUsername(identifier);

        return new UsernamePasswordAuthenticationToken(
                principal,
                "",
                principal.getAuthorities()
        );
    }

    public static String generateAccessToken(
            LoginEntity entity,
            RoleType role,
            String secretKey,
            Long expiredTimeMs
    ) {
        ClaimsAdapter claimsAdapter = ClaimsAdapter.of(Jwts.claims());
        claimsAdapter.setId(entity.getId());
        claimsAdapter.setIdentifier(entity.getEmail());
        claimsAdapter.setRole(role);

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(claimsAdapter.getClaims())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiredTimeMs))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }
}
