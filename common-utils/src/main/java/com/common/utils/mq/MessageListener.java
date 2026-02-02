package com.common.utils.mq;

/**
 * 消息监听器接口
 * 定义消息消费的回调方法
 */
@FunctionalInterface
public interface MessageListener {

    /**
     * 消息接收回调
     *
     * @param message 消息内容
     */
    void onMessage(String message);

    /**
     * 消息接收回调（带消息属性）
     *
     * @param message 消息内容
     * @param properties 消息属性
     */
    default void onMessage(String message, MQMessageProperties properties) {
        onMessage(message);
    }

    /**
     * 消息消费成功回调
     */
    default void onSuccess() {
    }

    /**
     * 消息消费失败回调
     *
     * @param throwable 异常信息
     */
    default void onFailure(Throwable throwable) {
    }
}
