# Common Utils - 通用工具库

一个功能强大的 Java 通用工具库，提供了消息队列（MQ）、Redis 操作、HTTP 请求、数学计算等常用功能，支持多种中间件和配置方式。

## 技术栈

- **Spring Boot 3.2.0** - 核心框架
- **Spring Data Redis** - Redis 操作
- **Apache HttpClient 5** - HTTP 请求
- **RabbitMQ** - 消息队列
- **Kafka** - 消息队列
- **Jackson** - JSON 解析

## 功能特性

### 1. 消息队列（MQ）
- 支持多种消息队列中间件：RabbitMQ、Kafka、ActiveMQ、RocketMQ
- 统一的生产者和消费者接口
- 支持消息发送回调、延迟消息、标签消息、分区消息
- 自动配置和连接管理

### 2. Redis 操作
- 字符串、对象存储
- 过期时间管理
- 自增自减操作
- 分布式锁简单实现
- 连接池配置

### 3. HTTP 请求
- 支持 GET、POST、PUT、DELETE、HEAD 请求
- 支持自定义请求头
- 自动处理响应内容
- 异常处理和日志记录

### 4. 数学计算
- 高精度运算（BigDecimal）
- 基本运算：加法、减法、乘法、除法
- 高级运算：阶乘、组合数、排列数、斐波那契数
- 统计功能：平均值、最大值、最小值、方差、标准差
- 随机数生成

### 5. 通用工具
- 字符串处理：判空、脱敏、格式化
- 验证：邮箱、手机号、身份证号、URL 格式验证
- 转换：驼峰转下划线、下划线转驼峰
- 文件大小格式化
- 集合操作

## 使用方法

### 1. 引入依赖

在 `pom.xml` 中添加以下依赖：

```xml
<dependency>
    <groupId>com.common.utils</groupId>
    <artifactId>common-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 配置参数

在 `application.properties` 或 `application.yml` 中配置相关参数。

#### Redis 配置

```properties
# Redis 配置
redis.host=localhost
redis.port=6379
redis.password=
redis.database=0
redis.timeout=3000

# Redis 连接池配置
redis.pool.max-active=8
redis.pool.max-wait=-1
redis.pool.max-idle=8
redis.pool.min-idle=0
```

#### MQ 配置

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
import com.common.utils.mq.MQFactory;
import com.common.utils.mq.MQProducer;
import com.common.utils.mq.SendCallback;
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
import com.common.utils.mq.MQFactory;
import com.common.utils.mq.MQConsumer;
import com.common.utils.mq.MessageListener;
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

#### Redis 操作

```java
import com.common.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisExample {

    @Autowired
    private RedisUtil redisUtil;

    public void redisOperations() {
        // 设置字符串
        redisUtil.set("key", "value");
        System.out.println("Get: " + redisUtil.get("key"));

        // 设置过期时间
        redisUtil.set("key", "value", 60, java.util.concurrent.TimeUnit.SECONDS);

        // 自增
        System.out.println("Increment: " + redisUtil.increment("counter"));

        // 分布式锁
        String lockKey = "lock-key";
        String requestId = "request-id";
        boolean locked = redisUtil.tryLock(lockKey, requestId, 3000);
        if (locked) {
            try {
                // 业务逻辑
            } finally {
                redisUtil.unlock(lockKey, requestId);
            }
        }
    }
}
```

#### HTTP 请求

```java
import com.common.utils.http.HttpUtil;
import java.util.HashMap;
import java.util.Map;

public class HttpExample {

    public static void main(String[] args) {
        // GET 请求
        String response = HttpUtil.get("https://example.com/api");
        System.out.println("GET Response: " + response);

        // POST 请求
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String body = "{\"key\": \"value\"}";
        response = HttpUtil.post("https://example.com/api", body, headers);
        System.out.println("POST Response: " + response);

        // PUT 请求
        response = HttpUtil.put("https://example.com/api", body, headers);
        System.out.println("PUT Response: " + response);

        // DELETE 请求
        response = HttpUtil.delete("https://example.com/api", headers);
        System.out.println("DELETE Response: " + response);
    }
}
```

#### 数学计算

```java
import com.common.utils.math.MathUtil;

public class MathExample {

    public static void main(String[] args) {
        // 加法
        System.out.println("Add: " + MathUtil.add(1.23, 4.56));

        // 乘法
        System.out.println("Multiply: " + MathUtil.multiply(2.5, 3.5));

        // 除法
        System.out.println("Divide: " + MathUtil.divide(10, 3));

        // 阶乘
        System.out.println("Factorial: " + MathUtil.factorial(5));

        // 斐波那契数
        System.out.println("Fibonacci: " + MathUtil.fibonacci(10));
    }
}
```

#### 通用工具

```java
import com.common.utils.utils.CommonUtils;
import java.util.List;

public class CommonUtilsExample {

    public static void main(String[] args) {
        // 邮箱验证
        System.out.println("Email Valid: " + CommonUtils.isValidEmail("test@example.com"));

        // 手机号验证
        System.out.println("Phone Valid: " + CommonUtils.isValidPhone("13812345678"));

        // 生成随机字符串
        System.out.println("Random String: " + CommonUtils.randomString(10));

        // 格式化文件大小
        System.out.println("File Size: " + CommonUtils.formatFileSize(1024 * 1024 * 5));
    }
}
```

## 项目结构

```
common-utils/
├── src/
│   ├── main/
│   │   ├── java/com/common/utils/
│   │   │   ├── config/          # 自动配置类
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
