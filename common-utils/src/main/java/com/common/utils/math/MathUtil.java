package com.common.utils.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * 数学工具类
 * 提供各种数学计算方法
 */
public class MathUtil {

    private static final Logger logger = LoggerFactory.getLogger(MathUtil.class);
    private static final Random random = new Random();

    /**
     * 加法运算
     */
    public static BigDecimal add(double a, double b) {
        return add(BigDecimal.valueOf(a), BigDecimal.valueOf(b));
    }

    /**
     * 加法运算
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        try {
            return a.add(b);
        } catch (Exception e) {
            logger.error("Addition failed", e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * 减法运算
     */
    public static BigDecimal subtract(double a, double b) {
        return subtract(BigDecimal.valueOf(a), BigDecimal.valueOf(b));
    }

    /**
     * 减法运算
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        try {
            return a.subtract(b);
        } catch (Exception e) {
            logger.error("Subtraction failed", e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * 乘法运算
     */
    public static BigDecimal multiply(double a, double b) {
        return multiply(BigDecimal.valueOf(a), BigDecimal.valueOf(b));
    }

    /**
     * 乘法运算
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        try {
            return a.multiply(b);
        } catch (Exception e) {
            logger.error("Multiplication failed", e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * 除法运算
     */
    public static BigDecimal divide(double a, double b) {
        return divide(BigDecimal.valueOf(a), BigDecimal.valueOf(b), 2, RoundingMode.HALF_UP);
    }

    /**
     * 除法运算
     */
    public static BigDecimal divide(double a, double b, int scale, RoundingMode roundingMode) {
        return divide(BigDecimal.valueOf(a), BigDecimal.valueOf(b), scale, roundingMode);
    }

    /**
     * 除法运算
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale, RoundingMode roundingMode) {
        try {
            return a.divide(b, scale, roundingMode);
        } catch (Exception e) {
            logger.error("Division failed", e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * 四舍五入
     */
    public static double round(double value, int decimalPlaces) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * 计算百分比
     */
    public static double percentage(double value, double total) {
        if (total == 0) {
            return 0;
        }
        return round((value / total) * 100, 2);
    }

    /**
     * 计算平均值
     */
    public static double average(double[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }
        double sum = 0;
        for (double number : numbers) {
            sum += number;
        }
        return sum / numbers.length;
    }

    /**
     * 计算最大值
     */
    public static double max(double[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return Double.MIN_VALUE;
        }
        double max = numbers[0];
        for (double number : numbers) {
            if (number > max) {
                max = number;
            }
        }
        return max;
    }

    /**
     * 计算最小值
     */
    public static double min(double[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return Double.MAX_VALUE;
        }
        double min = numbers[0];
        for (double number : numbers) {
            if (number < min) {
                min = number;
            }
        }
        return min;
    }

    /**
     * 计算方差
     */
    public static double variance(double[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }
        double avg = average(numbers);
        double sum = 0;
        for (double number : numbers) {
            sum += Math.pow(number - avg, 2);
        }
        return sum / numbers.length;
    }

    /**
     * 计算标准差
     */
    public static double standardDeviation(double[] numbers) {
        return Math.sqrt(variance(numbers));
    }

    /**
     * 计算阶乘
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial of negative number is not defined");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * 计算组合数 C(n, k)
     */
    public static long combination(int n, int k) {
        if (k < 0 || k > n) {
            return 0;
        }
        if (k == 0 || k == n) {
            return 1;
        }
        k = Math.min(k, n - k);
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result *= (n - k + i);
            result /= i;
        }
        return result;
    }

    /**
     * 计算排列数 P(n, k)
     */
    public static long permutation(int n, int k) {
        if (k < 0 || k > n) {
            return 0;
        }
        long result = 1;
        for (int i = 0; i < k; i++) {
            result *= (n - i);
        }
        return result;
    }

    /**
     * 生成随机整数
     */
    public static int randomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Min must be less than or equal to max");
        }
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 生成随机双精度数
     */
    public static double randomDouble(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Min must be less than or equal to max");
        }
        return min + random.nextDouble() * (max - min);
    }

    /**
     * 判断是否是质数
     */
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算最大公约数（GCD）
     */
    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * 计算最小公倍数（LCM）
     */
    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return (a * b) / gcd(a, b);
    }

    /**
     * 计算斐波那契数
     */
    public static long fibonacci(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        long a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            long temp = b;
            b = a + b;
            a = temp;
        }
        return b;
    }
}
