package com.common.tools.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * MQ自动配置类
 */
@Configuration
@ConditionalOnProperty(prefix = "common.tools.mq", name = "enabled", havingValue = "true")
@Import({
        RabbitMqConfiguration.class,
        KafkaMqConfiguration.class
})
public class MqAutoConfiguration {

    // MQ自动配置类，根据配置导入对应的MQ配置
}
