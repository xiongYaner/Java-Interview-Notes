package com.common.tools.mq;

import com.common.tools.config.CommonToolsProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * RabbitMQ服务实现类
 */
@Service
@ConditionalOnProperty(prefix = "common.tools.mq", name = "type", havingValue = "rabbitmq", matchIfMissing = true)
public class RabbitMqService implements MqService {

    private final RabbitTemplate rabbitTemplate;
    private final CommonToolsProperties.MqProperties mqProperties;

    @Autowired
    public RabbitMqService(RabbitTemplate rabbitTemplate,
                           CommonToolsProperties commonToolsProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.mqProperties = commonToolsProperties.getMq();
    }

    @Override
    public <T> void send(String topic, T message) {
        send(topic, message, mqProperties.getSendTimeout());
    }

    @Override
    public <T> void send(String topic, T message, long timeout) {
        rabbitTemplate.convertAndSend(topic, message);
    }

    @Override
    public <T> void sendDelay(String topic, T message, long delay) {
        // 实现延迟消息发送
        rabbitTemplate.convertAndSend(topic, message);
    }

    @Override
    public <T> void receive(String topic, Class<T> messageType, MessageListener<T> messageListener) {
        // 实现消息接收
    }
}
