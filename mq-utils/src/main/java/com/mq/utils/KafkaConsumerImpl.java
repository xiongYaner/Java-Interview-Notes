package com.mq.utils;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Kafka 消费者实现
 */
public class KafkaConsumerImpl implements MQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerImpl.class);

    private final MQConfig.KafkaConfig config;
    private org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer;
    private ExecutorService executorService;
    private volatile boolean running = false;

    public KafkaConsumerImpl(MQConfig.KafkaConfig config) {
        this.config = config;
        init();
    }

    /**
     * 初始化消费者
     */
    private void init() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroupId());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
        executorService = Executors.newSingleThreadExecutor();
        logger.info("Kafka consumer initialized successfully");
    }

    @Override
    public void subscribe(String topic, MessageListener listener) {
        consumer.subscribe(Arrays.asList(topic));
        logger.info("Subscribed to Kafka topic: {}", topic);

        executorService.submit(() -> {
            while (running) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    String message = record.value();
                    logger.info("Received message from Kafka: {}", message);

                    try {
                        MQMessageProperties mqProperties = new MQMessageProperties();
                        mqProperties.setMessageId(record.offset() + "");
                        mqProperties.setTopic(topic);
                        mqProperties.setPartition(record.partition());
                        mqProperties.setOffset(record.offset());
                        mqProperties.setTimestamp(record.timestamp());

                        listener.onMessage(message, mqProperties);
                        listener.onSuccess();
                        consumer.commitAsync();
                    } catch (Exception e) {
                        logger.error("Failed to process message", e);
                        listener.onFailure(e);
                    }
                });
            }
        });
    }

    @Override
    public void subscribe(String topic, String tag, MessageListener listener) {
        String taggedTopic = topic + "_" + tag;
        subscribe(taggedTopic, listener);
    }

    @Override
    public void subscribe(String topic, int partition, MessageListener listener) {
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        consumer.assign(Arrays.asList(topicPartition));
        logger.info("Subscribed to Kafka partition {} of topic {}", partition, topic);

        executorService.submit(() -> {
            while (running) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    String message = record.value();
                    logger.info("Received message from Kafka partition {}: {}", partition, message);

                    try {
                        MQMessageProperties mqProperties = new MQMessageProperties();
                        mqProperties.setMessageId(record.offset() + "");
                        mqProperties.setTopic(topic);
                        mqProperties.setPartition(partition);
                        mqProperties.setOffset(record.offset());
                        mqProperties.setTimestamp(record.timestamp());

                        listener.onMessage(message, mqProperties);
                        listener.onSuccess();
                        consumer.commitAsync();
                    } catch (Exception e) {
                        logger.error("Failed to process message", e);
                        listener.onFailure(e);
                    }
                });
            }
        });
    }

    @Override
    public void unsubscribe(String topic) {
        consumer.unsubscribe();
        logger.info("Unsubscribed from Kafka topic: {}", topic);
    }

    @Override
    public void start() {
        running = true;
        logger.info("Kafka consumer started");
    }

    @Override
    public void close() {
        running = false;
        if (consumer != null) {
            consumer.close();
        }
        if (executorService != null) {
            executorService.shutdown();
        }
        logger.info("Kafka consumer closed");
    }
}
