package com.project.shoppingmall.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration @ConfigurationProperties("jwt")
@NoArgsConstructor @Getter @Setter
public class JwtProperties {
    private String secretKey;
    private Long expiredTimeMs;
}
