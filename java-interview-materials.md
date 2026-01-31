# Java面试材料（3年工具经验）

## 个人信息

- **姓名：** [你的姓名]
- **经验：** 3年Java开发经验
- **专业技能：** Java基础、Spring Boot、MySQL、Redis、Git、Docker、CI/CD

## 1. 自我介绍（2-3分钟）

### 模板
"面试官您好，我叫XXX，有3年Java开发经验。主要负责XXX项目的开发和维护，使用Spring Boot、MySQL、Redis等技术栈。在项目中负责过XXX功能的设计和开发，还负责过CI/CD流程的搭建和优化。我对Java基础和工具使用有较深的理解，希望能通过这次面试加入贵公司，为团队贡献自己的力量。"

### 重点
- 突出工具经验（如Git、Docker、CI/CD）
- 结合具体项目亮点
- 表达对技术的热情

## 2. Java基础知识

### 核心概念
- **面向对象**：封装、继承、多态的实现和应用场景
- **Java内存模型**：堆、栈、方法区的区别
- **异常处理**：try-catch-finally的执行顺序
- **泛型**：泛型擦除和通配符的使用

### 常见问题
- "请解释Java中的多态"
- "Java中堆内存和栈内存的区别"
- "try-catch-finally中如果catch块有return，finally会执行吗？"

## 3. 集合框架

### 常用集合
- List：ArrayList（数组实现）、LinkedList（链表实现）
- Set：HashSet（哈希表）、TreeSet（红黑树）
- Map：HashMap（数组+链表/红黑树）、LinkedHashMap（保持插入顺序）

### 常见问题
- "HashMap的底层实现原理"
- "ArrayList和LinkedList的区别和适用场景"
- "ConcurrentHashMap的线程安全机制"

## 4. 多线程编程

### 核心概念
- 线程创建方式：继承Thread、实现Runnable、Callable+Future
- 线程同步：synchronized、Lock（ReentrantLock）
- 线程通信：wait/notify、CountDownLatch、CyclicBarrier

### 常见问题
- "synchronized和Lock的区别"
- "线程死锁的原因和解决方法"
- "volatile关键字的作用"

## 5. JVM调优

### 内存结构
- 堆内存：Eden区、Survivor区、老年代、元空间
- 垃圾回收算法：标记-清除、复制、标记-整理、分代收集

### 常用工具
- jps：查看Java进程
- jstat：监控JVM内存和GC
- jmap：导出堆内存快照
- jstack：查看线程堆栈

### 常见问题
- "Java中堆内存的结构"
- "GC算法的分类和特点"
- "如何排查内存泄漏问题"

## 6. Spring Boot

### 核心特性
- 自动配置：@SpringBootApplication
- 启动流程：SpringApplication.run()
- 常用注解：@Controller、@Service、@Autowired、@Configuration

### 常见问题
- "Spring Boot的自动配置原理"
- "Spring Boot的启动流程"
- "@Autowired和@Resource的区别"

## 7. 数据库

### MySQL
- 索引：B+树索引、复合索引的设计
- 事务：ACID特性、事务隔离级别
- 优化：慢查询优化、分库分表

### Redis
- 数据类型：String、List、Hash、Set、ZSet
- 持久化：RDB、AOF
- 缓存问题：缓存穿透、缓存击穿、缓存雪崩

### 常见问题
- "MySQL索引的类型和设计原则"
- "Redis的持久化方式"
- "如何解决缓存穿透问题"

## 8. 工具经验（你的优势）

### Git
- 分支管理：Git Flow
- 代码冲突解决
- 常用命令：git add、git commit、git push、git merge

### Docker
- 容器编排：Docker Compose
- 镜像制作：Dockerfile
- 容器管理：docker run、docker exec、docker logs

### CI/CD
- 持续集成：Jenkins、GitLab CI
- 自动化部署：Docker容器化部署

### 常见问题
- "Git中的分支管理策略"
- "Dockerfile的常用指令"
- "如何搭建CI/CD流程"

## 9. 项目经验（STAR法则）

### 项目1：XXX系统
- **Situation（背景）：** 项目的业务需求和技术挑战
- **Task（任务）：** 你的具体职责和目标
- **Action（行动）：** 你采取的技术方案和工具使用
- **Result（结果）：** 项目的成果和价值

### 项目2：XXX工具
- **Situation（背景）：** 工具的开发目的
- **Task（任务）：** 工具的功能和架构设计
- **Action（行动）：** 你使用的技术和工具
- **Result（结果）：** 工具的使用效果和改进

## 10. 面试技巧

### 准备
- 研究公司背景和业务
- 复习常见面试题
- 准备项目经验的详细描述

### 回答问题
- 结构清晰，分点说明
- 结合项目经验举例
- 遇到不会的问题，坦诚回答并表现出学习意愿

### 结束
- 准备1-2个问题问面试官
- 表达对职位的兴趣

## 11. 进阶知识（可选）

- 微服务架构：Spring Cloud Alibaba
- 消息队列：RabbitMQ、Kafka
- 分布式锁：Redis实现、Zookeeper实现

---

**祝面试成功！** 🎉