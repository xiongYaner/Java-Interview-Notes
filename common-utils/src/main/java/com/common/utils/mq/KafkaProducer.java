package com.common.utils.mq;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Kafka 生产者实现
 */
public class KafkaProducer implements MQProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private final MQConfig.KafkaConfig config;
    private Producer<String, String> producer;

    public KafkaProducer(MQConfig.KafkaConfig config) {
        this.config = config;
        init();
    }

    /**
     * 初始化生产者
     */
    private void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", config.getBootstrapServers());
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
        logger.info("Kafka producer initialized successfully");
    }

    @Override
    public void sendMessage(String topic, String message) {
        try {
            producer.send(new ProducerRecord<>(topic, message));
            logger.info("Sent message to Kafka: {}", message);
        } catch (Exception e) {
            logger.error("Failed to send message to Kafka", e);
        }
    }

    @Override
    public void sendMessage(String topic, String message, SendCallback callback) {
        try {
            producer.send(new ProducerRecord<>(topic, message), (metadata, exception) -> {
                if (exception == null) {
                    logger.info("Sent message to Kafka: {}", message);
                    callback.onSuccess();
                } else {
                    logger.error("Failed to send message to Kafka", exception);
                    callback.onFailure(exception);
                }
            });
        } catch (Exception e) {
            logger.error("Failed to send message to Kafka", e);
            callback.onFailure(e);
        }
    }

    @Override
    public void sendMessageWithDelay(String topic, String message, long delay) {
        try {
            String delayedTopic = topic + "_delayed_" + delay;
            producer.send(new ProducerRecord<>(delayedTopic, message));
            logger.info("Sent delayed message to Kafka ({}ms): {}", delay, message);
        } catch (Exception e) {
            logger.error("Failed to send delayed message to Kafka", e);
        }
    }

    @Override
    public void sendMessageWithTag(String topic, String tag, String message) {
        try {
            String taggedTopic = topic + "_" + tag;
            producer.send(new ProducerRecord<>(taggedTopic, message));
            logger.info("Sent message to Kafka with tag {}: {}", tag, message);
        } catch (Exception e) {
            logger.error("Failed to send message to Kafka with tag", e);
        }
    }

    @Override
    public void sendMessageWithPartition(String topic, int partition, String message) {
        try {
            producer.send(new ProducerRecord<>(topic, partition, null, message));
            logger.info("Sent message to Kafka partition {}: {}", partition, message);
        } catch (Exception e) {
            logger.error("Failed to send message to Kafka partition", e);
        }
    }

    @Override
    public void close() {
        if (producer != null) {
            producer.close();
            logger.info("Kafka producer closed");
        }
    }
}
