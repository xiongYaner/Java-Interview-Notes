package com.mq.utils;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ 消费者实现
 */
public class RabbitMQConsumer implements MQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    private final MQConfig.RabbitMQConfig config;
    private Connection connection;
    private Channel channel;

    public RabbitMQConsumer(MQConfig.RabbitMQConfig config) {
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

            logger.info("RabbitMQ consumer connected successfully");
        } catch (Exception e) {
            logger.error("Failed to connect to RabbitMQ", e);
        }
    }

    @Override
    public void subscribe(String topic, MessageListener listener) {
        try {
            channel.queueDeclare(topic, true, false, false, null);
            channel.basicConsume(topic, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    logger.info("Received message from RabbitMQ: {}", message);

                    try {
                        MQMessageProperties mqProperties = new MQMessageProperties();
                        mqProperties.setMessageId(envelope.getDeliveryTag() + "");
                        mqProperties.setTopic(topic);
                        mqProperties.setOffset(envelope.getDeliveryTag());
                        mqProperties.setTimestamp(properties.getTimestamp().getTime());
                        mqProperties.setHeaders(properties.getHeaders());

                        listener.onMessage(message, mqProperties);
                        listener.onSuccess();
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (Exception e) {
                        logger.error("Failed to process message", e);
                        listener.onFailure(e);
                        channel.basicNack(envelope.getDeliveryTag(), false, true);
                    }
                }
            });
            logger.info("Subscribed to RabbitMQ topic: {}", topic);
        } catch (Exception e) {
            logger.error("Failed to subscribe to RabbitMQ topic", e);
        }
    }

    @Override
    public void subscribe(String topic, String tag, MessageListener listener) {
        try {
            String exchange = topic + "_exchange";
            String queueName = topic + "_queue_" + tag;

            channel.exchangeDeclare(exchange, "direct", true);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchange, tag);

            channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    logger.info("Received message from RabbitMQ with tag {}: {}", tag, message);

                    try {
                        MQMessageProperties mqProperties = new MQMessageProperties();
                        mqProperties.setMessageId(envelope.getDeliveryTag() + "");
                        mqProperties.setTopic(topic);
                        mqProperties.setTag(tag);
                        mqProperties.setOffset(envelope.getDeliveryTag());
                        mqProperties.setTimestamp(properties.getTimestamp().getTime());
                        mqProperties.setHeaders(properties.getHeaders());

                        listener.onMessage(message, mqProperties);
                        listener.onSuccess();
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (Exception e) {
                        logger.error("Failed to process message", e);
                        listener.onFailure(e);
                        channel.basicNack(envelope.getDeliveryTag(), false, true);
                    }
                }
            });
            logger.info("Subscribed to RabbitMQ topic {} with tag {}", topic, tag);
        } catch (Exception e) {
            logger.error("Failed to subscribe to RabbitMQ topic with tag", e);
        }
    }

    @Override
    public void subscribe(String topic, int partition, MessageListener listener) {
        try {
            String queueName = topic + "_partition_" + partition;
            channel.queueDeclare(queueName, true, false, false, null);

            channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    logger.info("Received message from RabbitMQ partition {}: {}", partition, message);

                    try {
                        MQMessageProperties mqProperties = new MQMessageProperties();
                        mqProperties.setMessageId(envelope.getDeliveryTag() + "");
                        mqProperties.setTopic(topic);
                        mqProperties.setPartition(partition);
                        mqProperties.setOffset(envelope.getDeliveryTag());
                        mqProperties.setTimestamp(properties.getTimestamp().getTime());
                        mqProperties.setHeaders(properties.getHeaders());

                        listener.onMessage(message, mqProperties);
                        listener.onSuccess();
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (Exception e) {
                        logger.error("Failed to process message", e);
                        listener.onFailure(e);
                        channel.basicNack(envelope.getDeliveryTag(), false, true);
                    }
                }
            });
            logger.info("Subscribed to RabbitMQ partition {} of topic {}", partition, topic);
        } catch (Exception e) {
            logger.error("Failed to subscribe to RabbitMQ partition", e);
        }
    }

    @Override
    public void unsubscribe(String topic) {
        logger.info("Unsubscribed from RabbitMQ topic: {}", topic);
    }

    @Override
    public void start() {
        logger.info("RabbitMQ consumer started");
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
            logger.info("RabbitMQ consumer closed");
        } catch (IOException | TimeoutException e) {
            logger.error("Failed to close RabbitMQ consumer", e);
        }
    }
}
