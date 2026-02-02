package com.mq.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MQ 配置类
 * 用于读取 application.properties 或 application.yml 中的 MQ 配置
 */
@Component
@ConfigurationProperties(prefix = "mq")
public class MQConfig {

    /**
     * 默认消息队列类型
     */
    private String defaultType = "rabbitmq";

    /**
     * RabbitMQ 配置
     */
    private RabbitMQConfig rabbitmq = new RabbitMQConfig();

    /**
     * Kafka 配置
     */
    private KafkaConfig kafka = new KafkaConfig();

    /**
     * ActiveMQ 配置
     */
    private ActiveMQConfig activemq = new ActiveMQConfig();

    /**
     * RocketMQ 配置
     */
    private RocketMQConfig rocketmq = new RocketMQConfig();

    public static class RabbitMQConfig {
        private String host = MQConstants.DEFAULT_RABBITMQ_HOST;
        private int port = MQConstants.DEFAULT_RABBITMQ_PORT;
        private String username = MQConstants.DEFAULT_RABBITMQ_USERNAME;
        private String password = MQConstants.DEFAULT_RABBITMQ_PASSWORD;
        private String virtualHost = MQConstants.DEFAULT_RABBITMQ_VIRTUAL_HOST;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getVirtualHost() {
            return virtualHost;
        }

        public void setVirtualHost(String virtualHost) {
            this.virtualHost = virtualHost;
        }
    }

    public static class KafkaConfig {
        private String bootstrapServers = MQConstants.DEFAULT_KAFKA_BOOTSTRAP_SERVERS;
        private String groupId = MQConstants.DEFAULT_KAFKA_GROUP_ID;

        public String getBootstrapServers() {
            return bootstrapServers;
        }

        public void setBootstrapServers(String bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
    }

    public static class ActiveMQConfig {
        private String brokerUrl = MQConstants.DEFAULT_ACTIVEMQ_BROKER_URL;
        private String username = MQConstants.DEFAULT_RABBITMQ_USERNAME;
        private String password = MQConstants.DEFAULT_RABBITMQ_PASSWORD;

        public String getBrokerUrl() {
            return brokerUrl;
        }

        public void setBrokerUrl(String brokerUrl) {
            this.brokerUrl = brokerUrl;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class RocketMQConfig {
        private String namesrvAddr = MQConstants.DEFAULT_ROCKETMQ_NAMESRV_ADDR;
        private String producerGroup = MQConstants.DEFAULT_ROCKETMQ_PRODUCER_GROUP;

        public String getNamesrvAddr() {
            return namesrvAddr;
        }

        public void setNamesrvAddr(String namesrvAddr) {
            this.namesrvAddr = namesrvAddr;
        }

        public String getProducerGroup() {
            return producerGroup;
        }

        public void setProducerGroup(String producerGroup) {
            this.producerGroup = producerGroup;
        }
    }

    public String getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }

    public RabbitMQConfig getRabbitmq() {
        return rabbitmq;
    }

    public void setRabbitmq(RabbitMQConfig rabbitmq) {
        this.rabbitmq = rabbitmq;
    }

    public KafkaConfig getKafka() {
        return kafka;
    }

    public void setKafka(KafkaConfig kafka) {
        this.kafka = kafka;
    }

    public ActiveMQConfig getActivemq() {
        return activemq;
    }

    public void setActivemq(ActiveMQConfig activemq) {
        this.activemq = activemq;
    }

    public RocketMQConfig getRocketmq() {
        return rocketmq;
    }

    public void setRocketmq(RocketMQConfig rocketmq) {
        this.rocketmq = rocketmq;
    }
}
