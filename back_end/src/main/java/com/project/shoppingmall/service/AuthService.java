package com.project.shoppingmall.service;

import com.project.shoppingmall.config.properties.JwtProperties;
import com.project.shoppingmall.domain.LoginEntity;
import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.model.request.LoginRequest;
import com.project.shoppingmall.model.response.LoginResponse;
import com.project.shoppingmall.repository.RepositoryFactory;
import com.project.shoppingmall.type.ErrorCode;
import com.project.shoppingmall.type.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.shoppingmall.utils.JwtTokenDealer.generateAccessToken;

@Service
@Transactional
public class AuthService {
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    RepositoryFactory repositoryFactory;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponse doLogin(LoginRequest request) {
        RoleType role = request.getRole();
        String email = request.getEmail();
        String password = request.getPassword();

        // 조회 후, 비밀번호 추출. 존재하지 않는 계정이면 에러 발생.
        String passwordLoaded = repositoryFactory.getFinderByEmail(role, email)
                .map(LoginEntity::getPassword)
                .orElseThrow(() ->
                        new AuthenticationException(
                                ErrorCode.ACCOUNT_NOT_FOUNDED,
                                String.format("로그인을 시도한 계정의 역할: %s\n로그인을 시도한 이메일: %s", role.getName(), email)
                        )
                );

        // 추춮한 비밀번호와 입력한 비밀번호가 일치하지 않으면 에러 발생.
        if(!passwordEncoder.matches(password, passwordLoaded)) {
            throw new AuthenticationException(ErrorCode.WRONG_PASSWORD);
        }

        // 일치한다면 토큰 발급.
        String token = generateAccessToken(
                email, role,
                jwtProperties.getSecretKey(), jwtProperties.getExpiredTimeMs()
        );

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
