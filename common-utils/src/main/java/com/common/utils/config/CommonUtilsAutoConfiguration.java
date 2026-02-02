package com.common.utils.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.common.utils.http.HttpUtil;
import com.common.utils.math.MathUtil;
import com.common.utils.utils.CommonUtils;
import com.common.utils.utils.DateUtil;
import com.common.utils.utils.FileUtil;

/**
 * 自动配置类
 * 用于 Spring Boot 自动配置工具类
 */
@Configuration
public class CommonUtilsAutoConfiguration {

    /**
     * HTTP 工具配置
     */
    @Configuration
    @ConditionalOnClass(HttpUtil.class)
    public static class HttpConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public HttpUtil httpUtil() {
            return new HttpUtil();
        }
    }

    /**
     * 数学工具配置
     */
    @Configuration
    @ConditionalOnClass(MathUtil.class)
    public static class MathConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public MathUtil mathUtil() {
            return new MathUtil();
        }
    }

    /**
     * 通用工具配置
     */
    @Configuration
    @ConditionalOnClass(CommonUtils.class)
    public static class CommonConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public CommonUtils commonUtils() {
            return new CommonUtils();
        }
    }

    /**
     * 日期工具配置
     */
    @Configuration
    @ConditionalOnClass(DateUtil.class)
    public static class DateConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public DateUtil dateUtil() {
            return new DateUtil();
        }
    }

    /**
     * 文件工具配置
     */
    @Configuration
    @ConditionalOnClass(FileUtil.class)
    public static class FileConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public FileUtil fileUtil() {
            return new FileUtil();
        }
    }
}
