package com.mq.utils;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQ 自动配置类
 * 用于 Spring Boot 自动配置 MQ 工具类
 */
@Configuration
@EnableConfigurationProperties(MQConfig.class)
@ConditionalOnClass(MQFactory.class)
public class MQAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MQFactory mqFactory() {
        return new MQFactory();
    }
}
