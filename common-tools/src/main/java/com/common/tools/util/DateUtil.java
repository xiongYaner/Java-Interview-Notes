package com.common.tools.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 日期工具类
 */
public class DateUtil {

    // 日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_SLASH = "yyyy/MM/dd";
    public static final String DATE_FORMAT_DOT = "yyyy.MM.dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";
    public static final String DATETIME_FORMAT_DOT = "yyyy.MM.dd HH:mm:ss";

    // 日期格式化器
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final DateTimeFormatter DATE_FORMATTER_SLASH = DateTimeFormatter.ofPattern(DATE_FORMAT_SLASH);
    public static final DateTimeFormatter DATE_FORMATTER_DOT = DateTimeFormatter.ofPattern(DATE_FORMAT_DOT);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    public static final DateTimeFormatter DATETIME_FORMATTER_SLASH = DateTimeFormatter.ofPattern(DATETIME_FORMAT_SLASH);
    public static final DateTimeFormatter DATETIME_FORMATTER_DOT = DateTimeFormatter.ofPattern(DATETIME_FORMAT_DOT);

    /**
     * 获取当前日期字符串
     * @return 当前日期字符串
     */
    public static String getCurrentDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /**
     * 获取当前时间字符串
     * @return 当前时间字符串
     */
    public static String getCurrentTime() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    /**
     * 获取当前日期时间字符串
     * @return 当前日期时间字符串
     */
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    /**
     * 格式化日期
     * @param date 日期
     * @return 格式化后的日期字符串
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * 格式化日期
     * @param date 日期
     * @param format 格式
     * @return 格式化后的日期字符串
     */
    public static String formatDate(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 格式化时间
     * @param time 时间
     * @return 格式化后的时间字符串
     */
    public static String formatTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }

    /**
     * 格式化时间
     * @param time 时间
     * @param format 格式
     * @return 格式化后的时间字符串
     */
    public static String formatTime(LocalTime time, String format) {
        return time.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 格式化日期时间
     * @param dateTime 日期时间
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_FORMATTER);
    }

    /**
     * 格式化日期时间
     * @param dateTime 日期时间
     * @param format 格式
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateTime(LocalDateTime dateTime, String format) {
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 解析日期字符串
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * 解析日期字符串
     * @param dateStr 日期字符串
     * @param format 格式
     * @return 日期
     */
    public static LocalDate parseDate(String dateStr, String format) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 解析时间字符串
     * @param timeStr 时间字符串
     * @return 时间
     */
    public static LocalTime parseTime(String timeStr) {
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    /**
     * 解析时间字符串
     * @param timeStr 时间字符串
     * @param format 格式
     * @return 时间
     */
    public static LocalTime parseTime(String timeStr, String format) {
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 解析日期时间字符串
     * @param dateTimeStr 日期时间字符串
     * @return 日期时间
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    /**
     * 解析日期时间字符串
     * @param dateTimeStr 日期时间字符串
     * @param format 格式
     * @return 日期时间
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String format) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 计算两个日期之间的天数差
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 天数差
     */
    public static long getDaysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 计算两个日期时间之间的小时差
     * @param startDateTime 开始日期时间
     * @param endDateTime 结束日期时间
     * @return 小时差
     */
    public static long getHoursBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ChronoUnit.HOURS.between(startDateTime, endDateTime);
    }

    /**
     * 计算两个日期时间之间的分钟差
     * @param startDateTime 开始日期时间
     * @param endDateTime 结束日期时间
     * @return 分钟差
     */
    public static long getMinutesBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ChronoUnit.MINUTES.between(startDateTime, endDateTime);
    }

    /**
     * 计算两个日期时间之间的秒差
     * @param startDateTime 开始日期时间
     * @param endDateTime 结束日期时间
     * @return 秒差
     */
    public static long getSecondsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ChronoUnit.SECONDS.between(startDateTime, endDateTime);
    }

    /**
     * 判断是否是闰年
     * @param year 年份
     * @return 是否是闰年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    /**
     * 判断是否是闰年
     * @param date 日期
     * @return 是否是闰年
     */
    public static boolean isLeapYear(LocalDate date) {
        return isLeapYear(date.getYear());
    }

    /**
     * 获取月份的天数
     * @param year 年份
     * @param month 月份
     * @return 天数
     */
    public static int getDaysInMonth(int year, int month) {
        return LocalDate.of(year, month, 1).lengthOfMonth();
    }

    /**
     * 获取月份的天数
     * @param date 日期
     * @return 天数
     */
    public static int getDaysInMonth(LocalDate date) {
        return date.lengthOfMonth();
    }

    /**
     * 获取年份的天数
     * @param year 年份
     * @return 天数
     */
    public static int getDaysInYear(int year) {
        return isLeapYear(year) ? 366 : 365;
    }

    /**
     * 获取年份的天数
     * @param date 日期
     * @return 天数
     */
    public static int getDaysInYear(LocalDate date) {
        return getDaysInYear(date.getYear());
    }
}
