package com.common.utils.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Redis 配置类
 * 用于读取 application.properties 或 application.yml 中的 Redis 配置
 */
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    /**
     * 主机地址
     */
    private String host = "localhost";

    /**
     * 端口号
     */
    private int port = 6379;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库索引
     */
    private int database = 0;

    /**
     * 连接超时时间（毫秒）
     */
    private int timeout = 3000;

    /**
     * 连接池配置
     */
    private PoolConfig pool = new PoolConfig();

    public static class PoolConfig {
        /**
         * 最大连接数
         */
        private int maxActive = 8;

        /**
         * 最大等待时间（毫秒）
         */
        private int maxWait = -1;

        /**
         * 最大空闲连接
         */
        private int maxIdle = 8;

        /**
         * 最小空闲连接
         */
        private int minIdle = 0;

        public int getMaxActive() {
            return maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public int getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(int maxWait) {
            this.maxWait = maxWait;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public PoolConfig getPool() {
        return pool;
    }

    public void setPool(PoolConfig pool) {
        this.pool = pool;
    }
}
