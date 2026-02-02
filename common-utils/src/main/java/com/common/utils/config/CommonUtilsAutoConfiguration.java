package com.common.utils.config;

import com.common.utils.mq.MQConfig;
import com.common.utils.mq.MQFactory;
import com.common.utils.redis.RedisConfig;
import com.common.utils.redis.RedisUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 自动配置类
 * 用于 Spring Boot 自动配置工具类
 */
@Configuration
@EnableConfigurationProperties({MQConfig.class, RedisConfig.class})
public class CommonUtilsAutoConfiguration {

    /**
     * Redis 配置
     */
    @Configuration
    @ConditionalOnClass({RedisTemplate.class, RedisConnectionFactory.class})
    public static class RedisConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<Object, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            template.setHashKeySerializer(new StringRedisSerializer());
            template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
            template.afterPropertiesSet();
            return template;
        }

        @Bean
        @ConditionalOnMissingBean
        public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
            return new StringRedisTemplate(redisConnectionFactory);
        }

        @Bean
        @ConditionalOnMissingBean
        public RedisUtil redisUtil() {
            return new RedisUtil();
        }
    }

    /**
     * MQ 配置
     */
    @Configuration
    @ConditionalOnClass(MQFactory.class)
    public static class MQConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public MQFactory mqFactory() {
            return new MQFactory();
        }
    }

    /**
     * 通用工具配置
     */
    @Configuration
    public static class UtilsConfiguration {
    }
}
