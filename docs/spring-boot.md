# Spring Boot

Spring Boot是Spring Framework的简化版本，它提供了一种快速开发Spring应用程序的方式。Spring Boot自动配置了Spring应用程序所需的大部分配置，使开发者可以专注于业务逻辑的开发。

## Spring Boot基础

### 创建Spring Boot项目

#### 使用Spring Initializr

1. 访问https://start.spring.io/
2. 配置项目信息，如项目名称、包名、依赖等
3. 点击"Generate"按钮下载项目
4. 解压项目并导入到IDE中

#### 使用IDE

1. 在IDE中创建新的Spring Boot项目
2. 配置项目信息，如项目名称、包名、依赖等
3. 点击"Next"按钮完成项目创建

### 项目结构

Spring Boot项目的基本结构如下：

```
my-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── myproject/
│   │   │               ├── MyProjectApplication.java
│   │   │               ├── controller/
│   │   │               ├── service/
│   │   │               ├── repository/
│   │   │               └── model/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application.yml
│   │       └── static/
│   └── test/
└── pom.xml
```

### 启动Spring Boot应用

```java
@SpringBootApplication
public class MyProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyProjectApplication.class, args);
    }
}
```

## 自动配置

Spring Boot自动配置了Spring应用程序所需的大部分配置。自动配置是基于条件注解实现的，只有当特定的条件满足时才会自动配置相应的组件。

### 条件注解

Spring Boot提供了多种条件注解：

| 注解 | 描述 |
| ---- | ---- |
| @ConditionalOnClass | 当类路径中存在指定类时 |
| @ConditionalOnMissingClass | 当类路径中不存在指定类时 |
| @ConditionalOnBean | 当容器中存在指定Bean时 |
| @ConditionalOnMissingBean | 当容器中不存在指定Bean时 |
| @ConditionalOnProperty | 当配置文件中存在指定属性时 |
| @ConditionalOnWebApplication | 当是Web应用程序时 |
| @ConditionalOnNotWebApplication | 当不是Web应用程序时 |

### 自定义自动配置

```java
@Configuration
@ConditionalOnClass(MyService.class)
@EnableConfigurationProperties(MyProperties.class)
public class MyAutoConfiguration {
    @Autowired
    private MyProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public MyService myService() {
        return new MyService(properties.getMessage());
    }
}

@ConfigurationProperties(prefix = "my")
public class MyProperties {
    private String message = "Hello World";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```

## 启动流程

Spring Boot的启动流程主要分为以下几个阶段：
1. 初始化SpringApplication
2. 准备环境
3. 准备上下文
4. 刷新上下文
5. 启动应用

### 自定义启动流程

```java
@SpringBootApplication
public class MyProjectApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MyProjectApplication.class);
        application.addListeners(new MyApplicationListener());
        application.run(args);
    }

    static class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            System.out.println("Spring Boot应用启动完成");
        }
    }
}
```

## 常用注解

Spring Boot提供了多种常用注解：

### 配置注解

| 注解 | 描述 |
| ---- | ---- |
| @SpringBootApplication | 组合注解，包含@Configuration、@EnableAutoConfiguration和@ComponentScan |
| @Configuration | 声明配置类 |
| @EnableAutoConfiguration | 启用自动配置 |
| @ComponentScan | 扫描组件 |

### 组件注解

| 注解 | 描述 |
| ---- | ---- |
| @Component | 声明组件 |
| @Controller | 声明控制器 |
| @Service | 声明服务 |
| @Repository | 声明数据访问层 |

### 注入注解

| 注解 | 描述 |
| ---- | ---- |
| @Autowired | 自动注入 |
| @Qualifier | 限定注入 |
| @Resource | 资源注入 |

### 配置属性注解

| 注解 | 描述 |
| ---- | ---- |
| @Value | 注入配置属性 |
| @ConfigurationProperties | 绑定配置属性到Bean |

## 数据访问

Spring Boot提供了多种数据访问方式，包括JDBC、JPA、MyBatis等。

### JDBC

```java
@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    public void save(User user) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail());
    }

    public void update(User user) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
```

### JPA

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // 省略getter和setter方法
}

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    List<User> findByEmailContaining(String email);
}

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> findByEmailContaining(String email) {
        return userRepository.findByEmailContaining(email);
    }
}
```

### MyBatis

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // 省略getter和setter方法
}

@Mapper
public interface UserMapper {
    List<User> findAll();
    User findById(Long id);
    void save(User user);
    void update(User user);
    void delete(Long id);
    List<User> findByName(String name);
    List<User> findByEmailContaining(String email);
}

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    public void save(User user) {
        userMapper.save(user);
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public void delete(Long id) {
        userMapper.delete(id);
    }

    public List<User> findByName(String name) {
        return userMapper.findByName(name);
    }

    public List<User> findByEmailContaining(String email) {
        return userMapper.findByEmailContaining(email);
    }
}
```

## 日志处理

Spring Boot提供了多种日志处理方式，包括Logback、Log4j2等。

### 配置日志

在application.properties中配置日志：

```properties
# 配置日志级别
logging.level.root=INFO
logging.level.com.example.myproject=DEBUG

# 配置日志输出位置
logging.file.name=myproject.log

# 配置日志格式
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
```

### 使用日志

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        logger.info("查询所有用户");
        return userRepository.findAll();
    }

    public User findById(Long id) {
        logger.debug("查询用户: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        logger.info("保存用户: {}", user);
        return userRepository.save(user);
    }

    public void delete(Long id) {
        logger.info("删除用户: {}", id);
        userRepository.deleteById(id);
    }
}
```

## 总结

Spring Boot是Spring Framework的简化版本，它提供了一种快速开发Spring应用程序的方式。Spring Boot自动配置了Spring应用程序所需的大部分配置，使开发者可以专注于业务逻辑的开发。本文介绍了Spring Boot的基本概念，包括Spring Boot基础、自动配置、启动流程、常用注解、数据访问和日志处理等内容，希望对大家有所帮助。