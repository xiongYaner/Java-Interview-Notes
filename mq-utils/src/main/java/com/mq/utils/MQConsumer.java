package com.mq.utils;

/**
 * 消息消费者接口
 * 定义消息消费的通用方法
 */
public interface MQConsumer {

    /**
     * 订阅主题并消费消息
     *
     * @param topic    主题/队列名称
     * @param listener 消息监听器
     */
    void subscribe(String topic, MessageListener listener);

    /**
     * 订阅主题并消费消息（带标签）
     *
     * @param topic    主题/队列名称
     * @param tag      标签
     * @param listener 消息监听器
     */
    void subscribe(String topic, String tag, MessageListener listener);

    /**
     * 订阅主题并消费消息（带分区）
     *
     * @param topic     主题/队列名称
     * @param partition 分区号
     * @param listener  消息监听器
     */
    void subscribe(String topic, int partition, MessageListener listener);

    /**
     * 取消订阅
     *
     * @param topic 主题/队列名称
     */
    void unsubscribe(String topic);

    /**
     * 启动消费者
     */
    void start();

    /**
     * 关闭消费者
     */
    void close();
}
