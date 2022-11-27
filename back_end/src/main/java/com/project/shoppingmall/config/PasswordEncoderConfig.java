package com.project.shoppingmall.config;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class PasswordEncoderConfig {

    @Bean @Profile({"prod", "dev"})
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean @Profile({"local"})
    public PasswordEncoder passwordEncoderLocal() {
        return new identityPasswordEncoder();
    }

    @NoArgsConstructor
    private static class identityPasswordEncoder implements PasswordEncoder {

        @Override
        public String encode(CharSequence rawPassword) {
            return rawPassword.toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return rawPassword.toString().equals(encodedPassword);
        }
    }
}
