package com.common.tools;

import com.common.tools.util.DateUtil;
import com.common.tools.util.HttpUtil;
import com.common.tools.util.MathUtil;
import com.common.tools.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 通用工具包测试类
 */
@SpringBootTest
class CommonToolsApplicationTests {

    /**
     * 测试字符串工具类
     */
    @Test
    void testStringUtil() {
        System.out.println("测试字符串工具类:");
        System.out.println("随机字符串: " + StringUtil.randomString(10));
        System.out.println("随机数字字符串: " + StringUtil.randomNumberString(6));
        System.out.println("首字母大写: " + StringUtil.capitalize("hello world"));
        System.out.println("首字母小写: " + StringUtil.uncapitalize("Hello World"));
        System.out.println("是否为空: " + StringUtil.isEmpty(""));
        System.out.println("是否为空白: " + StringUtil.isBlank("  "));
        System.out.println("是否为数字: " + StringUtil.isNumeric("123"));
        System.out.println("是否为字母: " + StringUtil.isAlpha("abc"));
        System.out.println("是否为字母或数字: " + StringUtil.isAlphanumeric("abc123"));
        System.out.println("包含特殊字符: " + StringUtil.containsSpecialCharacters("abc@123"));
        System.out.println("替换特殊字符: " + StringUtil.replaceSpecialCharacters("abc@123", "_"));
        System.out.println("截取字符串: " + StringUtil.truncate("Hello World", 5));
        System.out.println("截取字符串(省略号): " + StringUtil.truncateWithEllipsis("Hello World", 5));
        System.out.println("反转字符串: " + StringUtil.reverse("Hello World"));
        System.out.println("统计字符数: " + StringUtil.countChar("Hello World", 'l'));
        System.out.println("统计子串数: " + StringUtil.countSubstring("Hello World", "l"));
        System.out.println();
    }

    /**
     * 测试数学工具类
     */
    @Test
    void testMathUtil() {
        System.out.println("测试数学工具类:");
        System.out.println("加法: " + MathUtil.add(1.23, 4.56));
        System.out.println("减法: " + MathUtil.subtract(5.79, 4.56));
        System.out.println("乘法: " + MathUtil.multiply(1.23, 4.56));
        System.out.println("除法: " + MathUtil.divide(5.79, 4.56, 2));
        System.out.println("四舍五入: " + MathUtil.round(1.2345, 2));
        System.out.println("随机整数: " + MathUtil.randomInt(1, 10));
        System.out.println("随机小数: " + MathUtil.randomDouble(1.0, 10.0));
        System.out.println("是否为质数: " + MathUtil.isPrime(7));
        System.out.println("阶乘: " + MathUtil.factorial(5));
        System.out.println("平方根: " + MathUtil.squareRoot(16));
        System.out.println("平方: " + MathUtil.square(4));
        System.out.println("立方: " + MathUtil.cube(2));
        System.out.println("幂: " + MathUtil.power(2, 3));
        System.out.println("对数: " + MathUtil.log(10));
        System.out.println("自然对数: " + MathUtil.ln(10));
        System.out.println("以10为底的对数: " + MathUtil.log10(100));
        System.out.println();
    }

    /**
     * 测试日期工具类
     */
    @Test
    void testDateUtil() {
        System.out.println("测试日期工具类:");
        System.out.println("当前日期: " + DateUtil.getCurrentDate());
        System.out.println("当前时间: " + DateUtil.getCurrentTime());
        System.out.println("当前日期时间: " + DateUtil.getCurrentDateTime());
        System.out.println("格式化日期: " + DateUtil.formatDate(LocalDate.now()));
        System.out.println("格式化时间: " + DateUtil.formatTime(LocalTime.now()));
        System.out.println("格式化日期时间: " + DateUtil.formatDateTime(LocalDateTime.now()));
        System.out.println("解析日期: " + DateUtil.parseDate("2024-02-02"));
        System.out.println("解析时间: " + DateUtil.parseTime("12:34:56"));
        System.out.println("解析日期时间: " + DateUtil.parseDateTime("2024-02-02 12:34:56"));
        System.out.println("计算天数差: " + DateUtil.getDaysBetween(LocalDate.of(2024, 2, 2), LocalDate.of(2024, 2, 10)));
        System.out.println("是否是闰年: " + DateUtil.isLeapYear(2024));
        System.out.println("获取月份天数: " + DateUtil.getDaysInMonth(2024, 2));
        System.out.println("获取年份天数: " + DateUtil.getDaysInYear(2024));
        System.out.println();
    }

    /**
     * 测试HTTP工具类
     */
    @Test
    void testHttpUtil() {
        System.out.println("测试HTTP工具类:");
        try {
            String result = HttpUtil.get("https://www.baidu.com");
            System.out.println("HTTP GET请求成功，响应长度: " + result.length());
        } catch (Exception e) {
            System.out.println("HTTP GET请求失败: " + e.getMessage());
        }
        System.out.println();
    }
}
