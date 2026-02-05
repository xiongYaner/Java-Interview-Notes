package com.common.tools.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * 数学工具类
 */
public class MathUtil {

    /**
     * 加法运算
     * @param a 第一个数
     * @param b 第二个数
     * @return 和
     */
    public static BigDecimal add(double a, double b) {
        BigDecimal bigA = BigDecimal.valueOf(a);
        BigDecimal bigB = BigDecimal.valueOf(b);
        return bigA.add(bigB);
    }

    /**
     * 加法运算
     * @param a 第一个数
     * @param b 第二个数
     * @return 和
     */
    public static BigDecimal add(String a, String b) {
        BigDecimal bigA = new BigDecimal(a);
        BigDecimal bigB = new BigDecimal(b);
        return bigA.add(bigB);
    }

    /**
     * 减法运算
     * @param a 第一个数
     * @param b 第二个数
     * @return 差
     */
    public static BigDecimal subtract(double a, double b) {
        BigDecimal bigA = BigDecimal.valueOf(a);
        BigDecimal bigB = BigDecimal.valueOf(b);
        return bigA.subtract(bigB);
    }

    /**
     * 减法运算
     * @param a 第一个数
     * @param b 第二个数
     * @return 差
     */
    public static BigDecimal subtract(String a, String b) {
        BigDecimal bigA = new BigDecimal(a);
        BigDecimal bigB = new BigDecimal(b);
        return bigA.subtract(bigB);
    }

    /**
     * 乘法运算
     * @param a 第一个数
     * @param b 第二个数
     * @return 积
     */
    public static BigDecimal multiply(double a, double b) {
        BigDecimal bigA = BigDecimal.valueOf(a);
        BigDecimal bigB = BigDecimal.valueOf(b);
        return bigA.multiply(bigB);
    }

    /**
     * 乘法运算
     * @param a 第一个数
     * @param b 第二个数
     * @return 积
     */
    public static BigDecimal multiply(String a, String b) {
        BigDecimal bigA = new BigDecimal(a);
        BigDecimal bigB = new BigDecimal(b);
        return bigA.multiply(bigB);
    }

    /**
     * 除法运算
     * @param a 第一个数
     * @param b 第二个数
     * @param scale 保留小数位数
     * @return 商
     */
    public static BigDecimal divide(double a, double b, int scale) {
        BigDecimal bigA = BigDecimal.valueOf(a);
        BigDecimal bigB = BigDecimal.valueOf(b);
        return bigA.divide(bigB, scale, RoundingMode.HALF_UP);
    }

    /**
     * 除法运算
     * @param a 第一个数
     * @param b 第二个数
     * @param scale 保留小数位数
     * @return 商
     */
    public static BigDecimal divide(String a, String b, int scale) {
        BigDecimal bigA = new BigDecimal(a);
        BigDecimal bigB = new BigDecimal(b);
        return bigA.divide(bigB, scale, RoundingMode.HALF_UP);
    }

    /**
     * 四舍五入
     * @param value 原始值
     * @param scale 保留小数位数
     * @return 四舍五入后的值
     */
    public static BigDecimal round(double value, int scale) {
        BigDecimal bigValue = BigDecimal.valueOf(value);
        return bigValue.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 四舍五入
     * @param value 原始值
     * @param scale 保留小数位数
     * @return 四舍五入后的值
     */
    public static BigDecimal round(String value, int scale) {
        BigDecimal bigValue = new BigDecimal(value);
        return bigValue.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 获取随机数
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @return 随机数
     */
    public static int randomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("最小值不能大于最大值");
        }

        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 获取随机数
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @return 随机数
     */
    public static double randomDouble(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("最小值不能大于最大值");
        }

        Random random = new Random();
        return min + random.nextDouble() * (max - min);
    }

    /**
     * 判断是否为质数
     * @param number 数字
     * @return 是否为质数
     */
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 计算阶乘
     * @param n 数字
     * @return 阶乘
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("数字不能小于0");
        }

        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }

        return result;
    }

    /**
     * 计算平方根
     * @param number 数字
     * @return 平方根
     */
    public static double squareRoot(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("数字不能小于0");
        }

        return Math.sqrt(number);
    }

    /**
     * 计算平方
     * @param number 数字
     * @return 平方
     */
    public static double square(double number) {
        return Math.pow(number, 2);
    }

    /**
     * 计算立方
     * @param number 数字
     * @return 立方
     */
    public static double cube(double number) {
        return Math.pow(number, 3);
    }

    /**
     * 计算幂
     * @param base 底数
     * @param exponent 指数
     * @return 幂
     */
    public static double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    /**
     * 计算对数
     * @param number 数字
     * @return 对数
     */
    public static double log(double number) {
        if (number <= 0) {
            throw new IllegalArgumentException("数字必须大于0");
        }

        return Math.log(number);
    }

    /**
     * 计算自然对数
     * @param number 数字
     * @return 自然对数
     */
    public static double ln(double number) {
        if (number <= 0) {
            throw new IllegalArgumentException("数字必须大于0");
        }

        return Math.log(number);
    }

    /**
     * 计算以10为底的对数
     * @param number 数字
     * @return 对数
     */
    public static double log10(double number) {
        if (number <= 0) {
            throw new IllegalArgumentException("数字必须大于0");
        }

        return Math.log10(number);
    }
}
