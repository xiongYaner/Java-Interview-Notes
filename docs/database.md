# 数据库

数据库是Java应用程序中常用的存储和管理数据的方式。Java提供了多种数据库访问方式，包括JDBC、JPA、MyBatis等。

## MySQL基础

### 连接MySQL

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection connection = getConnection();
        if (connection != null) {
            System.out.println("MySQL连接成功");
        } else {
            System.out.println("MySQL连接失败");
        }
    }
}
```

### 基本SQL操作

```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLExample {
    public static void main(String[] args) {
        Connection connection = MySQLConnection.getConnection();
        if (connection == null) {
            return;
        }

        // 查询数据
        String querySQL = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getLong("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Email: " + resultSet.getString("email"));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 插入数据
        String insertSQL = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, "张三");
            preparedStatement.setString(2, "zhangsan@example.com");
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("插入了 " + affectedRows + " 条数据");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 更新数据
        String updateSQL = "UPDATE users SET email = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, "zhangsan@newexample.com");
            preparedStatement.setLong(2, 1);
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("更新了 " + affectedRows + " 条数据");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 删除数据
        String deleteSQL = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setLong(1, 1);
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("删除了 " + affectedRows + " 条数据");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

## 索引优化

### 索引的作用

索引是数据库中用于提高查询效率的数据结构。索引可以减少数据库查询时需要扫描的数据量，从而提高查询速度。

### 索引的类型

- 普通索引：最基本的索引，没有任何限制
- 唯一索引：确保索引列的值唯一
- 主键索引：主键列的索引，确保索引列的值唯一且非空
- 复合索引：多个列的组合索引
- 全文索引：用于全文搜索

### 创建索引

```sql
-- 创建普通索引
CREATE INDEX idx_name ON users(name);

-- 创建唯一索引
CREATE UNIQUE INDEX idx_email ON users(email);

-- 创建复合索引
CREATE INDEX idx_name_email ON users(name, email);

-- 创建全文索引
CREATE FULLTEXT INDEX idx_content ON articles(content);
```

### 索引的使用

```sql
-- 查询时使用索引
SELECT * FROM users WHERE name = '张三';

-- 查询时使用复合索引
SELECT * FROM users WHERE name = '张三' AND email = 'zhangsan@example.com';

-- 查询时使用全文索引
SELECT * FROM articles WHERE MATCH(content) AGAINST('Java');
```

## 事务处理

### 事务的基本概念

事务是数据库操作的基本单元，它具有原子性、一致性、隔离性和持久性等特性。

### 事务的ACID特性

- 原子性（Atomicity）：事务中的所有操作要么全部成功，要么全部失败
- 一致性（Consistency）：事务执行后，数据库的状态应该是一致的
- 隔离性（Isolation）：多个事务之间应该相互隔离，互不影响
- 持久性（Durability）：事务执行成功后，数据应该永久保存到数据库中

### 事务的使用

```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionExample {
    public static void main(String[] args) {
        Connection connection = MySQLConnection.getConnection();
        if (connection == null) {
            return;
        }

        try {
            connection.setAutoCommit(false);

            // 插入数据
            String insertSQL = "INSERT INTO users (name, email) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, "李四");
                preparedStatement.setString(2, "lisi@example.com");
                int affectedRows = preparedStatement.executeUpdate();
                System.out.println("插入了 " + affectedRows + " 条数据");
            }

            // 更新数据
            String updateSQL = "UPDATE users SET email = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
                preparedStatement.setString(1, "lisi@newexample.com");
                preparedStatement.setLong(2, 2);
                int affectedRows = preparedStatement.executeUpdate();
                System.out.println("更新了 " + affectedRows + " 条数据");
            }

            // 提交事务
            connection.commit();
            System.out.println("事务提交成功");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // 回滚事务
                connection.rollback();
                System.out.println("事务回滚成功");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## Redis基础

### 连接Redis

```java
import redis.clients.jedis.Jedis;

public class RedisConnection {
    private static final String HOST = "localhost";
    private static final int PORT = 6379;

    public static Jedis getConnection() {
        try {
            Jedis jedis = new Jedis(HOST, PORT);
            jedis.auth("password");
            return jedis;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Jedis jedis = getConnection();
        if (jedis != null) {
            System.out.println("Redis连接成功");
            jedis.close();
        } else {
            System.out.println("Redis连接失败");
        }
    }
}
```

### 基本操作

```java
import redis.clients.jedis.Jedis;

public class RedisExample {
    public static void main(String[] args) {
        Jedis jedis = RedisConnection.getConnection();
        if (jedis == null) {
            return;
        }

        // 字符串操作
        jedis.set("name", "张三");
        System.out.println("姓名: " + jedis.get("name"));

        // 哈希操作
        jedis.hset("user:1", "name", "李四");
        jedis.hset("user:1", "email", "lisi@example.com");
        System.out.println("用户信息: " + jedis.hgetAll("user:1"));

        // 列表操作
        jedis.lpush("users", "张三", "李四", "王五");
        System.out.println("用户列表: " + jedis.lrange("users", 0, -1));

        // 集合操作
        jedis.sadd("tags", "Java", "Python", "C++");
        System.out.println("标签集合: " + jedis.smembers("tags"));

        // 有序集合操作
        jedis.zadd("scores", 90, "张三");
        jedis.zadd("scores", 85, "李四");
        jedis.zadd("scores", 95, "王五");
        System.out.println("成绩排名: " + jedis.zrange("scores", 0, -1));

        jedis.close();
    }
}
```

## MongoDB基础

### 连接MongoDB

```java
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static final String HOST = "localhost";
    private static final int PORT = 27017;

    public static MongoDatabase getConnection() {
        try {
            MongoClient mongoClient = new MongoClient(HOST, PORT);
            return mongoClient.getDatabase("mydatabase");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        MongoDatabase database = getConnection();
        if (database != null) {
            System.out.println("MongoDB连接成功");
            System.out.println("数据库名称: " + database.getName());
        } else {
            System.out.println("MongoDB连接失败");
        }
    }
}
```

### 基本操作

```java
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBExample {
    public static void main(String[] args) {
        MongoDatabase database = MongoDBConnection.getConnection();
        if (database == null) {
            return;
        }

        MongoCollection<Document> users = database.getCollection("users");

        // 插入数据
        Document user = new Document("name", "张三")
                .append("email", "zhangsan@example.com")
                .append("age", 18);
        users.insertOne(user);
        System.out.println("插入了一条数据");

        // 查询数据
        users.find().forEach(doc -> System.out.println(doc));

        // 更新数据
        users.updateOne(new Document("name", "张三"),
                new Document("$set", new Document("email", "zhangsan@newexample.com")));
        System.out.println("更新了一条数据");

        // 删除数据
        users.deleteOne(new Document("name", "张三"));
        System.out.println("删除了一条数据");
    }
}
```

## 总结

数据库是Java应用程序中常用的存储和管理数据的方式。Java提供了多种数据库访问方式，包括JDBC、JPA、MyBatis等。本文介绍了MySQL基础、索引优化、事务处理、Redis基础和MongoDB基础等内容，希望对大家有所帮助。