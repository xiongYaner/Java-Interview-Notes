package com.mq.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MQ 工厂类
 * 根据配置创建对应的生产者和消费者实例
 */
@Component
public class MQFactory {

    private static final Logger logger = LoggerFactory.getLogger(MQFactory.class);

    @Autowired
    private MQConfig mqConfig;

    /**
     * 根据类型创建生产者
     *
     * @param mqType 消息队列类型
     * @return 生产者实例
     */
    public MQProducer createProducer(MQConstants.MQType mqType) {
        logger.info("Creating MQ producer for type: {}", mqType);
        return switch (mqType) {
            case RABBITMQ -> new RabbitMQProducer(mqConfig.getRabbitmq());
            case KAFKA -> new KafkaProducerImpl(mqConfig.getKafka());
            case ACTIVEMQ -> new ActiveMQProducer(mqConfig.getActivemq());
            case ROCKETMQ -> new RocketMQProducer(mqConfig.getRocketmq());
        };
    }

    /**
     * 使用默认类型创建生产者
     *
     * @return 生产者实例
     */
    public MQProducer createProducer() {
        MQConstants.MQType defaultType = MQConstants.MQType.valueOf(mqConfig.getDefaultType().toUpperCase());
        return createProducer(defaultType);
    }

    /**
     * 根据类型创建消费者
     *
     * @param mqType 消息队列类型
     * @return 消费者实例
     */
    public MQConsumer createConsumer(MQConstants.MQType mqType) {
        logger.info("Creating MQ consumer for type: {}", mqType);
        return switch (mqType) {
            case RABBITMQ -> new RabbitMQConsumer(mqConfig.getRabbitmq());
            case KAFKA -> new KafkaConsumerImpl(mqConfig.getKafka());
            case ACTIVEMQ -> new ActiveMQConsumer(mqConfig.getActivemq());
            case ROCKETMQ -> new RocketMQConsumer(mqConfig.getRocketmq());
        };
    }

    /**
     * 使用默认类型创建消费者
     *
     * @return 消费者实例
     */
    public MQConsumer createConsumer() {
        MQConstants.MQType defaultType = MQConstants.MQType.valueOf(mqConfig.getDefaultType().toUpperCase());
        return createConsumer(defaultType);
    }
}
