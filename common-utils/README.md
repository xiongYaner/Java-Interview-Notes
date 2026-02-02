# Common Utils - 通用工具库

一个功能强大的 Java 通用工具库，提供了 HTTP 请求、数学计算、日期操作、文件操作等常用功能，支持各种通用工具方法。

## 技术栈

- **Spring Boot 3.2.0** - 核心框架
- **Apache HttpClient 5** - HTTP 请求
- **Jackson** - JSON 解析

## 功能特性

### 1. HTTP 请求
- 支持 GET、POST、PUT、DELETE、HEAD 请求
- 支持自定义请求头
- 自动处理响应内容
- 异常处理和日志记录

### 2. 数学计算
- 高精度运算（BigDecimal）
- 基本运算：加法、减法、乘法、除法
- 高级运算：阶乘、组合数、排列数、斐波那契数
- 统计功能：平均值、最大值、最小值、方差、标准差
- 随机数生成

### 3. 日期和时间操作
- 格式化日期、时间、日期时间
- 解析日期、时间、日期时间字符串
- 计算日期和时间差值
- 在日期和时间上添加天数、月数、年数、小时、分钟、秒
- 获取月份的第一天和最后一天
- 判断闰年
- 获取星期几

### 4. 文件操作
- 读取和写入文件内容
- 复制、移动、删除文件
- 创建和删除目录
- 列出目录下的所有文件
- 获取文件大小和属性
- 格式化文件大小和日期

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

### 2. 代码示例

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

#### 日期和时间操作

```java
import com.common.utils.utils.DateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateExample {

    public static void main(String[] args) {
        // 格式化日期
        System.out.println("Formatted Date: " + DateUtil.format(LocalDate.now(), DateUtil.DATE_FORMAT_YYYY_MM_DD));

        // 解析日期
        LocalDate date = DateUtil.parseDate("2024-02-02", DateUtil.DATE_FORMAT_YYYY_MM_DD);
        System.out.println("Parsed Date: " + date);

        // 计算日期差值
        LocalDate startDate = DateUtil.parseDate("2024-01-01", DateUtil.DATE_FORMAT_YYYY_MM_DD);
        LocalDate endDate = DateUtil.parseDate("2024-02-02", DateUtil.DATE_FORMAT_YYYY_MM_DD);
        System.out.println("Days Between: " + DateUtil.daysBetween(startDate, endDate));

        // 获取月份第一天和最后一天
        System.out.println("First Day of Month: " + DateUtil.getFirstDayOfMonth());
        System.out.println("Last Day of Month: " + DateUtil.getLastDayOfMonth());
    }
}
```

#### 文件操作

```java
import com.common.utils.utils.FileUtil;

public class FileExample {

    public static void main(String[] args) {
        // 读取文件
        String content = FileUtil.readFile("test.txt");
        System.out.println("File Content: " + content);

        // 写入文件
        FileUtil.writeFile("test.txt", "Hello, World!");

        // 复制文件
        FileUtil.copyFile("test.txt", "test_copy.txt");

        // 获取文件大小
        System.out.println("File Size: " + FileUtil.formatFileSize(FileUtil.getFileSize("test.txt")));
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
