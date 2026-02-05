package com.common.tools.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka配置类
 */
@Configuration
@ConditionalOnProperty(prefix = "common.tools.mq", name = "type", havingValue = "kafka")
public class KafkaMqConfiguration {

    @Bean
    public NewTopic defaultTopic() {
        return TopicBuilder.name("default-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
