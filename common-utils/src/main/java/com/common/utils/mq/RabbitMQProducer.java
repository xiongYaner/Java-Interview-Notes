package com.common.utils.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ 生产者实现
 */
public class RabbitMQProducer implements MQProducer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducer.class);

    private final MQConfig.RabbitMQConfig config;
    private Connection connection;
    private Channel channel;

    public RabbitMQProducer(MQConfig.RabbitMQConfig config) {
        this.config = config;
        init();
    }

    /**
     * 初始化连接
     */
    private void init() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(config.getHost());
            factory.setPort(config.getPort());
            factory.setUsername(config.getUsername());
            factory.setPassword(config.getPassword());
            factory.setVirtualHost(config.getVirtualHost());

            connection = factory.newConnection();
            channel = connection.createChannel();

            logger.info("RabbitMQ producer connected successfully");
        } catch (Exception e) {
            logger.error("Failed to connect to RabbitMQ", e);
        }
    }

    @Override
    public void sendMessage(String topic, String message) {
        try {
            channel.queueDeclare(topic, true, false, false, null);
            channel.basicPublish("", topic, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            logger.info("Sent message to RabbitMQ: {}", message);
        } catch (Exception e) {
            logger.error("Failed to send message to RabbitMQ", e);
        }
    }

    @Override
    public void sendMessage(String topic, String message, SendCallback callback) {
        try {
            channel.queueDeclare(topic, true, false, false, null);
            channel.basicPublish("", topic, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            logger.info("Sent message to RabbitMQ: {}", message);
            callback.onSuccess();
        } catch (Exception e) {
            logger.error("Failed to send message to RabbitMQ", e);
            callback.onFailure(e);
        }
    }

    @Override
    public void sendMessageWithDelay(String topic, String message, long delay) {
        try {
            String delayedExchange = topic + "_delayed_exchange";
            String delayedQueue = topic + "_delayed_queue";

            channel.exchangeDeclare(delayedExchange, "x-delayed-message", true, false,
                    java.util.Map.of("x-delayed-type", "direct"));
            channel.queueDeclare(delayedQueue, true, false, false,
                    java.util.Map.of("x-dead-letter-exchange", "", "x-dead-letter-routing-key", topic));
            channel.queueBind(delayedQueue, delayedExchange, topic);

            channel.basicPublish(delayedExchange, topic,
                    java.util.Map.of("x-delay", String.valueOf(delay)),
                    message.getBytes());

            logger.info("Sent delayed message to RabbitMQ ({}ms): {}", delay, message);
        } catch (Exception e) {
            logger.error("Failed to send delayed message to RabbitMQ", e);
        }
    }

    @Override
    public void sendMessageWithTag(String topic, String tag, String message) {
        try {
            String exchange = topic + "_exchange";
            channel.exchangeDeclare(exchange, "direct", true);
            channel.queueDecl(topic, true, false, false, null);
            channel.queueBind(topic, exchange, tag);
            channel.basicPublish(exchange, tag, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            logger.info("Sent message to RabbitMQ with tag {}: {}", tag, message);
        } catch (Exception e) {
            logger.error("Failed to send message to RabbitMQ with tag", e);
        }
    }

    @Override
    public void sendMessageWithPartition(String topic, int partition, String message) {
        try {
            String queueName = topic + "_partition_" + partition;
            channel.queueDeclare(queueName, true, false, false, null);
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            logger.info("Sent message to RabbitMQ partition {}: {}", partition, message);
        } catch (Exception e) {
            logger.error("Failed to send message to RabbitMQ partition", e);
        }
    }

    @Override
    public void close() {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
            logger.info("RabbitMQ producer closed");
        } catch (IOException | TimeoutException e) {
            logger.error("Failed to close RabbitMQ producer", e);
        }
    }
}
