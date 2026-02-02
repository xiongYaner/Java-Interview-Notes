package com.mq.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ActiveMQ 生产者实现（占位类，需要引入 ActiveMQ 依赖）
 */
public class ActiveMQProducer implements MQProducer {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQProducer.class);

    private final MQConfig.ActiveMQConfig config;

    public ActiveMQProducer(MQConfig.ActiveMQConfig config) {
        this.config = config;
        init();
    }

    /**
     * 初始化生产者
     */
    private void init() {
        logger.warn("ActiveMQProducer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }

    @Override
    public void sendMessage(String topic, String message) {
        logger.warn("ActiveMQProducer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }

    @Override
    public void sendMessage(String topic, String message, SendCallback callback) {
        logger.warn("ActiveMQProducer is a placeholder class. Please add ActiveMQ dependencies to your project.");
        callback.onFailure(new UnsupportedOperationException("ActiveMQProducer not implemented"));
    }

    @Override
    public void sendMessageWithDelay(String topic, String message, long delay) {
        logger.warn("ActiveMQProducer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }

    @Override
    public void sendMessageWithTag(String topic, String tag, String message) {
        logger.warn("ActiveMQProducer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }

    @Override
    public void sendMessageWithPartition(String topic, int partition, String message) {
        logger.warn("ActiveMQProducer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }

    @Override
    public void close() {
        logger.warn("ActiveMQProducer is a placeholder class. Please add ActiveMQ dependencies to your project.");
    }
}
