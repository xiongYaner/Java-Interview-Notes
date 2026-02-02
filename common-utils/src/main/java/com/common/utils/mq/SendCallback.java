package com.common.utils.mq;

/**
 * 发送回调接口
 * 定义消息发送成功和失败的回调方法
 */
public interface SendCallback {

    /**
     * 发送成功回调
     */
    void onSuccess();

    /**
     * 发送失败回调
     *
     * @param throwable 异常信息
     */
    void onFailure(Throwable throwable);
}
