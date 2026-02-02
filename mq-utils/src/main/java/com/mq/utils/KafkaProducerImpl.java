package com.mq.utils;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Kafka 生产者实现
 */
public class KafkaProducerImpl implements MQProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerImpl.class);

    private final MQConfig.KafkaConfig config;
    private Producer<String, String> producer;

    public KafkaProducerImpl(MQConfig.KafkaConfig config) {
        this.config = config;
        init();
    }

    /**
     * 初始化生产者
     */
    private void init() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
        logger.info("Kafka producer initialized successfully");
    }

    @Override
    public void sendMessage(String topic, String message) {
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
            producer.send(record);
            logger.info("Sent message to Kafka: {}", message);
        } catch (Exception e) {
            logger.error("Failed to send message to Kafka", e);
        }
    }

    @Override
    public void sendMessage(String topic, String message, SendCallback callback) {
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
            producer.send(record, new org.apache.kafka.clients.producer.Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        logger.info("Sent message to Kafka: {}", message);
                        callback.onSuccess();
                    } else {
                        logger.error("Failed to send message to Kafka", exception);
                        callback.onFailure(exception);
                    }
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
            ProducerRecord<String, String> record = new ProducerRecord<>(delayedTopic, message);
            producer.send(record);
            logger.info("Sent delayed message to Kafka ({}ms): {}", delay, message);
        } catch (Exception e) {
            logger.error("Failed to send delayed message to Kafka", e);
        }
    }

    @Override
    public void sendMessageWithTag(String topic, String tag, String message) {
        try {
            String taggedTopic = topic + "_" + tag;
            ProducerRecord<String, String> record = new ProducerRecord<>(taggedTopic, message);
            producer.send(record);
            logger.info("Sent message to Kafka with tag {}: {}", tag, message);
        } catch (Exception e) {
            logger.error("Failed to send message to Kafka with tag", e);
        }
    }

    @Override
    public void sendMessageWithPartition(String topic, int partition, String message) {
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, null, message);
            producer.send(record);
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
