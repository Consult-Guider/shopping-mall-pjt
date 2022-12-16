package com.project.shoppingmall.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration @ConfigurationProperties(prefix = "cloud.aws")
@NoArgsConstructor @Getter @Setter
public class S3Properties {
    private Region region;
    private S3 s3;
    private Credentials credentials;

    @NoArgsConstructor @Getter @Setter
    public static class Region {
        private String Static;
    }
    @NoArgsConstructor @Getter @Setter
    public static class S3 {
        private String bucket;
    }
    @NoArgsConstructor @Getter @Setter
    public static class Credentials {
        private String accessKey;
        private String secretKey;
    }

}