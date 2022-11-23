package com.project.shoppingmall.config;

import com.project.shoppingmall.config.filter.JwtFilter;
import com.project.shoppingmall.config.properties.JwtProperties;
import com.project.shoppingmall.exception.JwtAccessDeniedHandler;
import com.project.shoppingmall.exception.JwtAuthenticationEntryPoint;
import com.project.shoppingmall.service.UserDetailServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {
    private final UserDetailServiceFactory userDetailServiceFactory;
    private final JwtProperties jwtProperties;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // JWT를 사용하기 때문에 CSRF 설정은 고려하지 않음.
                .csrf().disable()
                // MSA 구조를 따르고 있기 때문에 로그인 페이지가 필요없음.
                .httpBasic().disable().formLogin().disable()
                // h2-console을 위한 설정. TODO: 배포 시, 지을 것.
                .headers().frameOptions().sameOrigin()

                // JWT를 사용할 것이기에 세션을 사용하지 않음.
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 우선 모든 api에 대해 허용한. TODO: 나중에 세부적으로 조정할 것.
                .and().authorizeRequests()
                .anyRequest().permitAll()

                // exception handling
                .and().exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // jwtFilter 적용.
                .and().addFilterBefore(
                        new JwtFilter(
                                userDetailServiceFactory,
                                jwtProperties.getSecretKey()
                        ), UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
