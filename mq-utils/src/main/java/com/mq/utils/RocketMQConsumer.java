package com.mq.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RocketMQ 消费者实现（占位类，需要引入 RocketMQ 依赖）
 */
public class RocketMQConsumer implements MQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RocketMQConsumer.class);

    private final MQConfig.RocketMQConfig config;

    public RocketMQConsumer(MQConfig.RocketMQConfig config) {
        this.config = config;
        init();
    }

    /**
     * 初始化消费者
     */
    private void init() {
        logger.warn("RocketMQConsumer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }

    @Override
    public void subscribe(String topic, MessageListener listener) {
        logger.warn("RocketMQConsumer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }

    @Override
    public void subscribe(String topic, String tag, MessageListener listener) {
        logger.warn("RocketMQConsumer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }

    @Override
    public void subscribe(String topic, int partition, MessageListener listener) {
        logger.warn("RocketMQConsumer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }

    @Override
    public void unsubscribe(String topic) {
        logger.warn("RocketMQConsumer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }

    @Override
    public void start() {
        logger.warn("RocketMQConsumer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }

    @Override
    public void close() {
        logger.warn("RocketMQConsumer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }
}
