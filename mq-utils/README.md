# MQ Utils - 消息队列工具库

一个功能强大的消息队列工具库，支持多种消息队列中间件（如 RabbitMQ、Kafka、ActiveMQ、RocketMQ 等），提供统一的生产者和消费者接口，支持消息发送回调、延迟消息、标签消息、分区消息等功能。

## 技术栈

- **Spring Boot 3.2.0** - 核心框架
- **Spring AMQP** - RabbitMQ 操作
- **Spring Kafka** - Kafka 操作
- **Jackson** - JSON 解析

## 功能特性

### 1. 支持的消息队列中间件
- RabbitMQ
- Kafka
- ActiveMQ
- RocketMQ

### 2. 生产者功能
- 发送消息
- 发送消息（带回调）
- 发送消息（带延迟）
- 发送消息（带标签）
- 发送消息（带分区）
- 自动连接管理

### 3. 消费者功能
- 订阅主题并消费消息
- 订阅主题并消费消息（带标签）
- 订阅主题并消费消息（带分区）
- 取消订阅
- 消息监听器
- 自动连接管理

### 4. 配置功能
- 支持通过 application.properties 或 application.yml 配置 MQ 连接参数
- 支持配置默认消息队列类型
- 支持配置多种消息队列参数

## 使用方法

### 1. 引入依赖

在 `pom.xml` 中添加以下依赖：

```xml
<dependency>
    <groupId>com.mq.utils</groupId>
    <artifactId>mq-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 配置参数

在 `application.properties` 或 `application.yml` 中配置 MQ 相关参数。

```properties
# 默认 MQ 类型（rabbitmq/kafka/activemq/rocketmq）
mq.default.type=rabbitmq

# RabbitMQ 配置
mq.rabbitmq.host=localhost
mq.rabbitmq.port=5672
mq.rabbitmq.username=guest
mq.rabbitmq.password=guest
mq.rabbitmq.virtual-host=/

# Kafka 配置
mq.kafka.bootstrap-servers=localhost:9092
mq.kafka.group-id=default-group

# ActiveMQ 配置
mq.activemq.broker-url=tcp://localhost:61616
mq.activemq.username=guest
mq.activemq.password=guest

# RocketMQ 配置
mq.rocketmq.namesrv-addr=localhost:9876
mq.rocketmq.producer-group=default-producer-group
```

### 3. 使用示例

#### MQ 生产者

```java
import com.mq.utils.MQFactory;
import com.mq.utils.MQProducer;
import com.mq.utils.SendCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private MQFactory mqFactory;

    public void sendMessage(String topic, String message) {
        MQProducer producer = mqFactory.createProducer();
        producer.sendMessage(topic, message);
        producer.close();
    }

    public void sendMessageWithCallback(String topic, String message) {
        MQProducer producer = mqFactory.createProducer();
        producer.sendMessage(topic, message, new SendCallback() {
            @Override
            public void onSuccess() {
                System.out.println("Message sent successfully");
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.err.println("Failed to send message: " + throwable.getMessage());
            }
        });
        producer.close();
    }
}
```

#### MQ 消费者

```java
import com.mq.utils.MQFactory;
import com.mq.utils.MQConsumer;
import com.mq.utils.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageConsumer {

    @Autowired
    private MQFactory mqFactory;

    @PostConstruct
    public void init() {
        MQConsumer consumer = mqFactory.createConsumer();
        consumer.subscribe("test-topic", new MessageListener() {
            @Override
            public void onMessage(String message) {
                System.out.println("Received message: " + message);
            }
        });
        consumer.start();
    }
}
```

## 项目结构

```
mq-utils/
├── src/
│   ├── main/
│   │   ├── java/com/mq/utils/
│   │   │   ├── config/          # 配置类
│   │   │   ├── http/            # HTTP 工具类
│   │   │   ├── math/            # 数学工具类
│   │   │   ├── mq/              # 消息队列工具类
│   │   │   ├── redis/           # Redis 工具类
│   │   │   └── utils/           # 通用工具类
│   │   └── resources/
│   │       └── META-INF/
│   │           └── spring.factories
│   └── test/                    # 测试代码
└── pom.xml                      # Maven 配置
```

## 构建项目

```bash
mvn clean install
```

构建成功后，项目将安装到本地 Maven 仓库，其他项目可以通过引入依赖使用。

## 许可证

本项目采用 MIT 许可证。
