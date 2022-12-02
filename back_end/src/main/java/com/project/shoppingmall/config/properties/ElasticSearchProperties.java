package com.project.shoppingmall.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "es.cfg")
@NoArgsConstructor @Getter @Setter
public class ElasticSearchProperties {
    private String host;
    private Integer port;

}
