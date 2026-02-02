package com.mq.utils;

/**
 * 消息生产者接口
 * 定义消息发送的通用方法
 */
public interface MQProducer {

    /**
     * 发送消息
     *
     * @param topic   主题/队列名称
     * @param message 消息内容
     */
    void sendMessage(String topic, String message);

    /**
     * 发送消息（带回调）
     *
     * @param topic    主题/队列名称
     * @param message  消息内容
     * @param callback 发送回调
     */
    void sendMessage(String topic, String message, SendCallback callback);

    /**
     * 发送消息（带延迟）
     *
     * @param topic   主题/队列名称
     * @param message 消息内容
     * @param delay   延迟时间（毫秒）
     */
    void sendMessageWithDelay(String topic, String message, long delay);

    /**
     * 发送消息（带标签）
     *
     * @param topic   主题/队列名称
     * @param tag     标签
     * @param message 消息内容
     */
    void sendMessageWithTag(String topic, String tag, String message);

    /**
     * 发送消息（带分区）
     *
     * @param topic     主题/队列名称
     * @param partition 分区号
     * @param message   消息内容
     */
    void sendMessageWithPartition(String topic, int partition, String message);

    /**
     * 关闭生产者
     */
    void close();
}
