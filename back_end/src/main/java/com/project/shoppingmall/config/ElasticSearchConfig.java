package com.project.shoppingmall.config;

import com.project.shoppingmall.config.properties.ElasticSearchProperties;
import com.project.shoppingmall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetSocketAddress;

@EnableElasticsearchRepositories(basePackageClasses = {ItemRepository.class})
@EnableElasticsearchAuditing
@RequiredArgsConstructor
@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {
    private final ElasticSearchProperties esProperties;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(new InetSocketAddress(esProperties.getHost(), esProperties.getPort()))
                .build();
    }
}
