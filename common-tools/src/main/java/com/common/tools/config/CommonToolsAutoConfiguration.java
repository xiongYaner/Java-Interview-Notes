package com.common.tools.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 通用工具自动配置类
 */
@Configuration
@EnableConfigurationProperties(CommonToolsProperties.class)
@Import({
        MqAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class CommonToolsAutoConfiguration {

    // 自动配置类，负责将所有配置类组合在一起
}
