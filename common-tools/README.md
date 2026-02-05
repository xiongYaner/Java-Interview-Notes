# 通用工具包

## 项目简介

一个通用的Java工具包，包含以下功能：

- **MQ支持**：支持多种消息队列（RabbitMQ、Kafka）
- **Redis支持**：提供Redis操作的工具类
- **HTTP工具**：提供HTTP请求的工具类
- **文件上传**：提供文件上传和管理的工具类
- **数学工具**：提供常用的数学计算工具类
- **字符串工具**：提供字符串处理的工具类
- **日期工具**：提供日期和时间处理的工具类

## 技术栈

- Spring Boot 3.5.0
- Spring Data Redis
- Spring Kafka
- Apache HttpClient 5
- Apache Commons IO
- Google Guava
- Lombok
- Jackson

## 功能特性

### MQ支持

- 支持RabbitMQ和Kafka
- 配置简单，支持切换
- 提供发送、接收、延迟消息等功能

### Redis支持

- 提供常用的Redis操作
- 支持字符串、对象的存储和获取
- 支持过期时间设置

### HTTP工具

- 提供GET、POST、PUT、DELETE等请求方法
- 支持请求头和请求体设置
- 简单易用的API

### 文件上传

- 支持文件上传
- 支持文件类型和大小验证
- 支持文件信息获取和删除

### 数学工具

- 提供常用的数学计算功能
- 支持精确计算（BigDecimal）
- 提供随机数生成、质数判断等功能

### 字符串工具

- 提供字符串处理的常用功能
- 支持随机字符串生成
- 支持字符串验证和替换

### 日期工具

- 提供日期和时间处理的常用功能
- 支持日期格式化和解析
- 支持日期计算和比较

## 安装和使用

### 安装

在Maven项目中，添加以下依赖：

```xml
<dependency>
    <groupId>com.common</groupId>
    <artifactId>common-tools</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 配置

在Spring Boot项目中，添加以下配置到`application.properties`：

```properties
# 工具包是否启用
common.tools.enabled=true

# MQ配置
common.tools.mq.enabled=false
common.tools.mq.type=rabbitmq
common.tools.mq.send-timeout=3000
common.tools.mq.retry-count=3

# Redis配置
common.tools.redis.enabled=true
common.tools.redis.host=localhost
common.tools.redis.port=6379
common.tools.redis.database=0
common.tools.redis.password=
common.tools.redis.timeout=3000
common.tools.redis.pool.max-active=8
common.tools.redis.pool.max-idle=8
common.tools.redis.pool.min-idle=0
common.tools.redis.pool.max-wait=-1

# 文件上传配置
common.tools.file-upload.enabled=true
common.tools.file-upload.upload-path=/tmp/upload
common.tools.file-upload.max-file-size=10
common.tools.file-upload.allowed-types=jpg,jpeg,png,gif,pdf,doc,docx,xls,xlsx,zip,rar
common.tools.file-upload.compress-enabled=false
common.tools.file-upload.compress-quality=80
```

### 使用示例

#### 字符串工具

```java
// 生成随机字符串
String randomStr = StringUtil.randomString(10);

// 首字母大写
String capitalized = StringUtil.capitalize("hello world");

// 判断字符串是否为空
boolean isEmpty = StringUtil.isEmpty("");
```

#### 数学工具

```java
// 加法
BigDecimal sum = MathUtil.add(1.23, 4.56);

// 除法（保留2位小数）
BigDecimal quotient = MathUtil.divide(5.79, 4.56, 2);

// 生成随机整数
int randomInt = MathUtil.randomInt(1, 10);
```

#### 日期工具

```java
// 获取当前日期
String currentDate = DateUtil.getCurrentDate();

// 格式化日期
String formattedDate = DateUtil.formatDate(LocalDate.now());

// 解析日期字符串
LocalDate date = DateUtil.parseDate("2024-02-02");
```

#### HTTP工具

```java
// 发送GET请求
String response = HttpUtil.get("https://www.baidu.com");

// 发送POST请求
String response = HttpUtil.post("https://api.example.com", "{\"key\":\"value\"}");
```

#### 文件上传工具

```java
@Autowired
private FileUploadUtil fileUploadUtil;

// 上传文件
String filePath = fileUploadUtil.uploadFile(file);

// 获取文件信息
FileUploadUtil.FileInfo fileInfo = fileUploadUtil.getFileInfo(filePath);

// 删除文件
fileUploadUtil.deleteFile(filePath);
```

#### Redis工具

```java
@Autowired
private RedisService redisService;

// 设置字符串
redisService.setString("key", "value");

// 获取字符串
String value = redisService.getString("key");

// 设置对象
redisService.setObject("user", user);

// 获取对象
User user = redisService.getObject("user", User.class);
```

#### MQ工具

```java
@Autowired
private MqService mqService;

// 发送消息
mqService.send("topic", "message");

// 发送延迟消息
mqService.sendDelay("topic", "message", 5000);

// 接收消息
mqService.receive("topic", String.class, message -> {
    System.out.println("收到消息: " + message);
});
```

## 构建和部署

### 构建项目

使用Maven构建项目：

```bash
mvn clean install
```

### 运行项目

直接运行启动类：

```bash
mvn spring-boot:run
```

## 许可证

MIT License

## 作者

Common Tools Team
