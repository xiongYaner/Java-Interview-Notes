package com.common.tools.mq;

/**
 * MQ服务接口
 */
public interface MqService {

    /**
     * 发送消息
     * @param topic 主题
     * @param message 消息内容
     * @param <T> 消息类型
     */
    <T> void send(String topic, T message);

    /**
     * 发送消息（带超时时间）
     * @param topic 主题
     * @param message 消息内容
     * @param timeout 超时时间（毫秒）
     * @param <T> 消息类型
     */
    <T> void send(String topic, T message, long timeout);

    /**
     * 发送延迟消息
     * @param topic 主题
     * @param message 消息内容
     * @param delay 延迟时间（毫秒）
     * @param <T> 消息类型
     */
    <T> void sendDelay(String topic, T message, long delay);

    /**
     * 接收消息
     * @param topic 主题
     * @param messageType 消息类型
     * @param messageListener 消息监听器
     * @param <T> 消息类型
     */
    <T> void receive(String topic, Class<T> messageType, MessageListener<T> messageListener);

    /**
     * 消息监听器接口
     * @param <T> 消息类型
     */
    interface MessageListener<T> {
        void onMessage(T message);
    }
}
