package com.mq.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RocketMQ 生产者实现（占位类，需要引入 RocketMQ 依赖）
 */
public class RocketMQProducer implements MQProducer {

    private static final Logger logger = LoggerFactory.getLogger(RocketMQProducer.class);

    private final MQConfig.RocketMQConfig config;

    public RocketMQProducer(MQConfig.RocketMQConfig config) {
        this.config = config;
        init();
    }

    /**
     * 初始化生产者
     */
    private void init() {
        logger.warn("RocketMQProducer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }

    @Override
    public void sendMessage(String topic, String message) {
        logger.warn("RocketMQProducer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }

    @Override
    public void sendMessage(String topic, String message, SendCallback callback) {
        logger.warn("RocketMQProducer is a placeholder class. Please add RocketMQ dependencies to your project.");
        callback.onFailure(new UnsupportedOperationException("RocketMQProducer not implemented"));
    }

    @Override
    public void sendMessageWithDelay(String topic, String message, long delay) {
        logger.warn("RocketMQProducer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }

    @Override
    public void sendMessageWithTag(String topic, String tag, String message) {
        logger.warn("RocketMQProducer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }

    @Override
    public void sendMessageWithPartition(String topic, int partition, String message) {
        logger.warn("RocketMQProducer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }

    @Override
    public void close() {
        logger.warn("RocketMQProducer is a placeholder class. Please add RocketMQ dependencies to your project.");
    }
}
