package com.common.tools.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 通用工具配置属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "common.tools")
public class CommonToolsProperties {

    /**
     * 是否启用工具包
     */
    private boolean enabled = true;

    /**
     * MQ配置
     */
    private MqProperties mq = new MqProperties();

    /**
     * Redis配置
     */
    private RedisProperties redis = new RedisProperties();

    /**
     * 文件上传配置
     */
    private FileUploadProperties fileUpload = new FileUploadProperties();

    /**
     * MQ配置属性
     */
    @Data
    public static class MqProperties {
        /**
         * 是否启用MQ
         */
        private boolean enabled = false;

        /**
         * MQ类型：rabbitmq, kafka, activemq
         */
        private String type = "rabbitmq";

        /**
         * 消息发送超时时间（毫秒）
         */
        private int sendTimeout = 3000;

        /**
         * 消息重试次数
         */
        private int retryCount = 3;
    }

    /**
     * Redis配置属性
     */
    @Data
    public static class RedisProperties {
        /**
         * 是否启用Redis
         */
        private boolean enabled = true;

        /**
         * 连接超时时间（毫秒）
         */
        private int timeout = 3000;

        /**
         * 数据库索引
         */
        private int database = 0;

        /**
         * 密码
         */
        private String password;

        /**
         * 主机地址
         */
        private String host = "localhost";

        /**
         * 端口
         */
        private int port = 6379;

        /**
         * 连接池配置
         */
        private PoolProperties pool = new PoolProperties();

        @Data
        public static class PoolProperties {
            /**
             * 最大连接数
             */
            private int maxActive = 8;

            /**
             * 最大空闲连接数
             */
            private int maxIdle = 8;

            /**
             * 最小空闲连接数
             */
            private int minIdle = 0;

            /**
             * 最大等待时间（毫秒）
             */
            private int maxWait = -1;
        }
    }

    /**
     * 文件上传配置属性
     */
    @Data
    public static class FileUploadProperties {
        /**
         * 是否启用文件上传功能
         */
        private boolean enabled = true;

        /**
         * 默认上传路径
         */
        private String uploadPath = "/tmp/upload";

        /**
         * 最大文件大小（MB）
         */
        private long maxFileSize = 10;

        /**
         * 允许的文件类型
         */
        private String allowedTypes = "jpg,jpeg,png,gif,pdf,doc,docx,xls,xlsx,zip,rar";

        /**
         * 是否启用文件压缩
         */
        private boolean compressEnabled = false;

        /**
         * 压缩质量（0-100）
         */
        private int compressQuality = 80;
    }
}
