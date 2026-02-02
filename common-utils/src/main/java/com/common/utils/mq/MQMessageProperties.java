package com.common.utils.mq;

import java.util.Map;

/**
 * 消息属性类
 * 封装消息的属性信息
 */
public class MQMessageProperties {

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 主题
     */
    private String topic;

    /**
     * 标签
     */
    private String tag;

    /**
     * 分区
     */
    private Integer partition;

    /**
     * 偏移量
     */
    private Long offset;

    /**
     * 发送时间
     */
    private Long timestamp;

    /**
     * 消息属性
     */
    private Map<String, Object> headers;

    /**
     * 延迟时间
     */
    private Long delayTime;

    /**
     * 优先级
     */
    private Integer priority;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public Long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Long delayTime) {
        this.delayTime = delayTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "MQMessageProperties{" +
                "messageId='" + messageId + '\'' +
                ", topic='" + topic + '\'' +
                ", tag='" + tag + '\'' +
                ", partition=" + partition +
                ", offset=" + offset +
                ", timestamp=" + timestamp +
                ", headers=" + headers +
                ", delayTime=" + delayTime +
                ", priority=" + priority +
                '}';
    }
}
