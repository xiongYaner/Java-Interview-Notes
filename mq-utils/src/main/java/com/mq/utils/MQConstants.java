package com.mq.utils;

/**
 * MQ 常量类
 * 定义支持的消息队列类型和配置常量
 */
public class MQConstants {

    /**
     * 消息队列类型枚举
     */
    public enum MQType {
        RABBITMQ,
        KAFKA,
        ACTIVEMQ,
        ROCKETMQ
    }

    /**
     * 默认配置键
     */
    public static final String DEFAULT_MQ_TYPE = "mq.default.type";
    public static final String RABBITMQ_HOST = "mq.rabbitmq.host";
    public static final String RABBITMQ_PORT = "mq.rabbitmq.port";
    public static final String RABBITMQ_USERNAME = "mq.rabbitmq.username";
    public static final String RABBITMQ_PASSWORD = "mq.rabbitmq.password";
    public static final String RABBITMQ_VIRTUAL_HOST = "mq.rabbitmq.virtual-host";
    public static final String KAFKA_BOOTSTRAP_SERVERS = "mq.kafka.bootstrap-servers";
    public static final String KAFKA_GROUP_ID = "mq.kafka.group-id";
    public static final String ACTIVEMQ_BROKER_URL = "mq.activemq.broker-url";
    public static final String ACTIVEMQ_USERNAME = "mq.activemq.username";
    public static final String ACTIVEMQ_PASSWORD = "mq.activemq.password";
    public static final String ROCKETMQ_NAMESRV_ADDR = "mq.rocketmq.namesrv-addr";
    public static final String ROCKETMQ_PRODUCER_GROUP = "mq.rocketmq.producer-group";

    /**
     * 默认值
     */
    public static final String DEFAULT_RABBITMQ_HOST = "localhost";
    public static final int DEFAULT_RABBITMQ_PORT = 5672;
    public static final String DEFAULT_RABBITMQ_USERNAME = "guest";
    public static final String DEFAULT_RABBITMQ_PASSWORD = "guest";
    public static final String DEFAULT_RABBITMQ_VIRTUAL_HOST = "/";
    public static final String DEFAULT_KAFKA_BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String DEFAULT_KAFKA_GROUP_ID = "default-group";
    public static final String DEFAULT_ACTIVEMQ_BROKER_URL = "tcp://localhost:61616";
    public static final String DEFAULT_ROCKETMQ_NAMESRV_ADDR = "localhost:9876";
    public static final String DEFAULT_ROCKETMQ_PRODUCER_GROUP = "default-producer-group";
}
