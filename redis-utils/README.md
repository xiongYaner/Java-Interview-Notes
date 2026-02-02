# Redis Utils - Redis 工具库

一个功能强大的 Redis 工具库，提供了各种 Redis 操作方法，包括字符串、对象存储，过期时间管理，自增自减操作，分布式锁简单实现等功能。

## 技术栈

- **Spring Boot 3.2.0** - 核心框架
- **Spring Data Redis** - Redis 操作
- **Jackson** - JSON 解析

## 功能特性

### 1. 字符串操作
- 设置字符串值
- 获取字符串值
- 设置字符串值并指定过期时间
- 删除键
- 判断键是否存在

### 2. 对象操作
- 设置对象值（自动序列化为 JSON）
- 获取对象值（自动反序列化为 Java 对象）
- 设置对象值并指定过期时间

### 3. 过期时间管理
- 设置键的过期时间
- 获取键的剩余过期时间

### 4. 自增自减操作
- 自增操作
- 自增指定值
- 自减操作
- 自减指定值

### 5. 分布式锁
- 简单实现分布式锁
- 支持锁的获取和释放

### 6. 配置功能
- 支持通过 application.properties 或 application.yml 配置 Redis 连接参数
- 支持连接池配置

## 使用方法

### 1. 引入依赖

在 `pom.xml` 中添加以下依赖：

```xml
<dependency>
    <groupId>com.redis.utils</groupId>
    <artifactId>redis-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 配置参数

在 `application.properties` 或 `application.yml` 中配置 Redis 相关参数。

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

### 3. 使用示例

```java
import com.redis.utils.RedisUtil;
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

        // 设置对象
        User user = new User("test", "test@example.com");
        redisUtil.setObject("user:1", user);
        User storedUser = (User) redisUtil.getObject("user:1");
        System.out.println("Stored User: " + storedUser);
    }

    public static class User {
        private String name;
        private String email;

        public User() {}

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}
```

## 项目结构

```
redis-utils/
├── src/
│   ├── main/
│   │   ├── java/com/redis/utils/
│   │   │   ├── RedisConfig.java          # 配置类
│   │   │   ├── RedisUtil.java            # 工具类
│   │   │   └── RedisAutoConfiguration.java  # 自动配置类
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
