package com.project.shoppingmall.config;

import com.project.shoppingmall.config.filter.JwtFilter;
import com.project.shoppingmall.config.properties.JwtProperties;
import com.project.shoppingmall.exception.JwtAccessDeniedHandler;
import com.project.shoppingmall.exception.JwtAuthenticationEntryPoint;
import com.project.shoppingmall.service.UserDetailServiceFactory;
import com.project.shoppingmall.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.project.shoppingmall.utils.UrlPrefixManager.addPrefix;

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

                // api 접근 권한 제어.
                .and().authorizeRequests()

                .mvcMatchers(HttpMethod.POST, addPrefix("/user")).permitAll()
                .mvcMatchers(HttpMethod.GET, addPrefix("/user/principal")).authenticated()
                .mvcMatchers(HttpMethod.PUT, addPrefix("/user/principal")).authenticated()
                .mvcMatchers(HttpMethod.DELETE, addPrefix("/user/principal")).authenticated()
                .mvcMatchers(HttpMethod.GET, addPrefix("/user/*")).hasRole(RoleType.ADMIN.withoutPrefix())
                .mvcMatchers(HttpMethod.PUT, addPrefix("/user/*")).hasRole(RoleType.ADMIN.withoutPrefix())
                .mvcMatchers(HttpMethod.DELETE, addPrefix("/user/*")).hasRole(RoleType.ADMIN.withoutPrefix())

                .mvcMatchers(HttpMethod.POST, addPrefix("/admin")).permitAll()
                .mvcMatchers(HttpMethod.GET, addPrefix("/admin/principal")).authenticated()
                .mvcMatchers(HttpMethod.PUT, addPrefix("/admin/principal")).authenticated()
                .mvcMatchers(HttpMethod.DELETE, addPrefix("/admin/principal")).authenticated()
                .mvcMatchers(HttpMethod.GET, addPrefix("/admin/*")).hasRole(RoleType.ADMIN.withoutPrefix())
                .mvcMatchers(HttpMethod.PUT, addPrefix("/admin/*")).hasRole(RoleType.ADMIN.withoutPrefix())
                .mvcMatchers(HttpMethod.DELETE, addPrefix("/admin/*")).hasRole(RoleType.ADMIN.withoutPrefix())

                .mvcMatchers(HttpMethod.POST, addPrefix("/seller")).permitAll()
                .mvcMatchers(HttpMethod.GET, addPrefix("/seller/principal")).authenticated()
                .mvcMatchers(HttpMethod.PUT, addPrefix("/seller/principal")).authenticated()
                .mvcMatchers(HttpMethod.DELETE, addPrefix("/seller/principal")).authenticated()
                .mvcMatchers(HttpMethod.GET, addPrefix("/seller/*")).hasRole(RoleType.ADMIN.withoutPrefix())
                .mvcMatchers(HttpMethod.PUT, addPrefix("/seller/*")).hasRole(RoleType.ADMIN.withoutPrefix())
                .mvcMatchers(HttpMethod.DELETE, addPrefix("/seller/*")).hasRole(RoleType.ADMIN.withoutPrefix())

                .mvcMatchers(HttpMethod.POST, addPrefix("/item")).hasAnyRole(RoleType.SELLER.withoutPrefix(), RoleType.ADMIN.withoutPrefix())
                .mvcMatchers(HttpMethod.GET, addPrefix("/item")).permitAll()
                .mvcMatchers(HttpMethod.GET, addPrefix("/item/*")).permitAll()
                .mvcMatchers(HttpMethod.GET, addPrefix("/item/*/all")).permitAll()
                .mvcMatchers(HttpMethod.PUT, addPrefix("/item/*")).hasRole(RoleType.SELLER.withoutPrefix())
                .mvcMatchers(HttpMethod.DELETE, addPrefix("/item/*")).hasRole(RoleType.SELLER.withoutPrefix())

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
}
