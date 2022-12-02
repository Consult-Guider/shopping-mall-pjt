package com.project.shoppingmall.config;

import com.project.shoppingmall.config.properties.ElasticSearchProperties;
import com.project.shoppingmall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackageClasses = {ItemRepository.class})
@EnableElasticsearchAuditing
@RequiredArgsConstructor
@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
    private final ElasticSearchProperties esProperties;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(
                        String.format("%s:%s", esProperties.getHost(), esProperties.getPort())
                )
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
