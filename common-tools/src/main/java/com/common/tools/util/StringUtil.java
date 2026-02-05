package com.common.tools.util;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtil {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String NUMBERS = "0123456789";
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final Random RANDOM = new Random();

    /**
     * 生成随机字符串
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("长度必须大于0");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return sb.toString();
    }

    /**
     * 生成随机数字字符串
     * @param length 字符串长度
     * @return 随机数字字符串
     */
    public static String randomNumberString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("长度必须大于0");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
        }

        return sb.toString();
    }

    /**
     * 生成随机字母字符串
     * @param length 字符串长度
     * @return 随机字母字符串
     */
    public static String randomLetterString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("长度必须大于0");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
        }

        return sb.toString();
    }

    /**
     * 首字母大写
     * @param str 字符串
     * @return 首字母大写后的字符串
     */
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * 首字母小写
     * @param str 字符串
     * @return 首字母小写后的字符串
     */
    public static String uncapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 判断字符串是否为空
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断字符串是否为空或空白
     * @param str 字符串
     * @return 是否为空或空白
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 修剪字符串
     * @param str 字符串
     * @return 修剪后的字符串
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 判断字符串是否为数字
     * @param str 字符串
     * @return 是否为数字
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断字符串是否为字母
     * @param str 字符串
     * @return 是否为字母
     */
    public static boolean isAlpha(String str) {
        if (isEmpty(str)) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断字符串是否为字母或数字
     * @param str 字符串
     * @return 是否为字母或数字
     */
    public static boolean isAlphanumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断字符串是否包含特殊字符
     * @param str 字符串
     * @return 是否包含特殊字符
     */
    public static boolean containsSpecialCharacters(String str) {
        if (isEmpty(str)) {
            return false;
        }

        String regex = "[^a-zA-Z0-9\\s]";
        return Pattern.compile(regex).matcher(str).find();
    }

    /**
     * 替换字符串中的特殊字符
     * @param str 字符串
     * @param replacement 替换字符
     * @return 替换后的字符串
     */
    public static String replaceSpecialCharacters(String str, String replacement) {
        if (isEmpty(str)) {
            return str;
        }

        String regex = "[^a-zA-Z0-9\\s]";
        return str.replaceAll(regex, replacement);
    }

    /**
     * 截取字符串
     * @param str 字符串
     * @param length 长度
     * @return 截取后的字符串
     */
    public static String truncate(String str, int length) {
        if (isEmpty(str)) {
            return str;
        }

        if (str.length() <= length) {
            return str;
        }

        return str.substring(0, length);
    }

    /**
     * 截取字符串（添加省略号）
     * @param str 字符串
     * @param length 长度
     * @return 截取后的字符串
     */
    public static String truncateWithEllipsis(String str, int length) {
        if (isEmpty(str)) {
            return str;
        }

        if (str.length() <= length) {
            return str;
        }

        return str.substring(0, length - 3) + "...";
    }

    /**
     * 反转字符串
     * @param str 字符串
     * @return 反转后的字符串
     */
    public static String reverse(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return new StringBuilder(str).reverse().toString();
    }

    /**
     * 统计字符串中指定字符出现的次数
     * @param str 字符串
     * @param character 字符
     * @return 出现次数
     */
    public static int countChar(String str, char character) {
        if (isEmpty(str)) {
            return 0;
        }

        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == character) {
                count++;
            }
        }

        return count;
    }

    /**
     * 统计字符串中指定子串出现的次数
     * @param str 字符串
     * @param substring 子串
     * @return 出现次数
     */
    public static int countSubstring(String str, String substring) {
        if (isEmpty(str) || isEmpty(substring)) {
            return 0;
        }

        int count = 0;
        int index = 0;

        while ((index = str.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length();
        }

        return count;
    }
}
