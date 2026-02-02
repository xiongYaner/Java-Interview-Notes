package com.mq.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ActiveMQ 消费者实现（占位类，需要引入 ActiveMQ 依赖）
 */
public class ActiveMQConsumer implements MQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQConsumer.class);

    private final MQConfig.ActiveMQConfig config;

    public ActiveMQConsumer(MQConfig.ActiveMQConfig config) {
        this.config = config;
        init();
    }

    /**
     * 初始化消费者
     */
    private void init() {
        logger.warn("ActiveMQConsumer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }

    @Override
    public void subscribe(String topic, MessageListener listener) {
        logger.warn("ActiveMQConsumer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }

    @Override
    public void subscribe(String topic, String tag, MessageListener listener) {
        logger.warn("ActiveMQConsumer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }

    @Override
    public void subscribe(String topic, int partition, MessageListener listener) {
        logger.warn("ActiveMQConsumer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }

    @Override
    public void unsubscribe(String topic) {
        logger.warn("ActiveMQConsumer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }

    @Override
    public void start() {
        logger.warn("ActiveMQConsumer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }

    @Override
    public void close() {
        logger.warn("ActiveMQConsumer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }
}
