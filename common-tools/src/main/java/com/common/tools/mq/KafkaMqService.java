package com.common.tools.mq;

import com.common.tools.config.CommonToolsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka服务实现类
 */
@Service
@ConditionalOnProperty(prefix = "common.tools.mq", name = "type", havingValue = "kafka")
public class KafkaMqService implements MqService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final CommonToolsProperties.MqProperties mqProperties;

    @Autowired
    public KafkaMqService(KafkaTemplate<String, Object> kafkaTemplate,
                          CommonToolsProperties commonToolsProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.mqProperties = commonToolsProperties.getMq();
    }

    @Override
    public <T> void send(String topic, T message) {
        send(topic, message, mqProperties.getSendTimeout());
    }

    @Override
    public <T> void send(String topic, T message, long timeout) {
        kafkaTemplate.send(topic, message);
    }

    @Override
    public <T> void sendDelay(String topic, T message, long delay) {
        // 实现延迟消息发送
        kafkaTemplate.send(topic, message);
    }

    @Override
    public <T> void receive(String topic, Class<T> messageType, MessageListener<T> messageListener) {
        // 实现消息接收
    }
}
