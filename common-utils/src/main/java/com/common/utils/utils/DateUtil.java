package com.common.utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 日期工具类
 * 提供各种日期和时间操作方法
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 常用日期格式
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_MM_DD_YYYY = "MM-dd-yyyy";
    public static final String DATE_FORMAT_DD_MM_YYYY = "dd-MM-yyyy";

    /**
     * 常用时间格式
     */
    public static final String TIME_FORMAT_HH_MM_SS = "HH:mm:ss";
    public static final String TIME_FORMAT_HHMMSS = "HHmmss";

    /**
     * 常用日期时间格式
     */
    public static final String DATETIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATETIME_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 获取当前日期字符串
     */
    public static String getCurrentDate() {
        return format(LocalDate.now(), DATE_FORMAT_YYYY_MM_DD);
    }

    /**
     * 获取当前时间字符串
     */
    public static String getCurrentTime() {
        return format(LocalTime.now(), TIME_FORMAT_HH_MM_SS);
    }

    /**
     * 获取当前日期时间字符串
     */
    public static String getCurrentDateTime() {
        return format(LocalDateTime.now(), DATETIME_FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 格式化日期
     */
    public static String format(LocalDate date, String pattern) {
        try {
            return date.format(DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            logger.error("Failed to format LocalDate", e);
            return null;
        }
    }

    /**
     * 格式化时间
     */
    public static String format(LocalTime time, String pattern) {
        try {
            return time.format(DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            logger.error("Failed to format LocalTime", e);
            return null;
        }
    }

    /**
     * 格式化日期时间
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        try {
            return dateTime.format(DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            logger.error("Failed to format LocalDateTime", e);
            return null;
        }
    }

    /**
     * 解析日期字符串
     */
    public static LocalDate parseDate(String dateStr, String pattern) {
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            logger.error("Failed to parse LocalDate", e);
            return null;
        }
    }

    /**
     * 解析时间字符串
     */
    public static LocalTime parseTime(String timeStr, String pattern) {
        try {
            return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            logger.error("Failed to parse LocalTime", e);
            return null;
        }
    }

    /**
     * 解析日期时间字符串
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
        try {
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            logger.error("Failed to parse LocalDateTime", e);
            return null;
        }
    }

    /**
     * Date 转 LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        try {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            logger.error("Failed to convert Date to LocalDate", e);
            return null;
        }
    }

    /**
     * Date 转 LocalTime
     */
    public static LocalTime toLocalTime(Date date) {
        try {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        } catch (Exception e) {
            logger.error("Failed to convert Date to LocalTime", e);
            return null;
        }
    }

    /**
     * Date 转 LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        try {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (Exception e) {
            logger.error("Failed to convert Date to LocalDateTime", e);
            return null;
        }
    }

    /**
     * LocalDate 转 Date
     */
    public static Date toDate(LocalDate localDate) {
        try {
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            return Date.from(zonedDateTime.toInstant());
        } catch (Exception e) {
            logger.error("Failed to convert LocalDate to Date", e);
            return null;
        }
    }

    /**
     * LocalTime 转 Date
     */
    public static Date toDate(LocalTime localTime) {
        try {
            LocalDate today = LocalDate.now();
            LocalDateTime localDateTime = LocalDateTime.of(today, localTime);
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            return Date.from(zonedDateTime.toInstant());
        } catch (Exception e) {
            logger.error("Failed to convert LocalTime to Date", e);
            return null;
        }
    }

    /**
     * LocalDateTime 转 Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        try {
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            return Date.from(zonedDateTime.toInstant());
        } catch (Exception e) {
            logger.error("Failed to convert LocalDateTime to Date", e);
            return null;
        }
    }

    /**
     * 计算两个日期之间的天数差
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        try {
            return ChronoUnit.DAYS.between(startDate, endDate);
        } catch (Exception e) {
            logger.error("Failed to calculate days between dates", e);
            return 0;
        }
    }

    /**
     * 计算两个日期时间之间的小时差
     */
    public static long hoursBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        try {
            return ChronoUnit.HOURS.between(startDateTime, endDateTime);
        } catch (Exception e) {
            logger.error("Failed to calculate hours between date times", e);
            return 0;
        }
    }

    /**
     * 计算两个日期时间之间的分钟差
     */
    public static long minutesBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        try {
            return ChronoUnit.MINUTES.between(startDateTime, endDateTime);
        } catch (Exception e) {
            logger.error("Failed to calculate minutes between date times", e);
            return 0;
        }
    }

    /**
     * 计算两个日期时间之间的秒差
     */
    public static long secondsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        try {
            return ChronoUnit.SECONDS.between(startDateTime, endDateTime);
        } catch (Exception e) {
            logger.error("Failed to calculate seconds between date times", e);
            return 0;
        }
    }

    /**
     * 在日期上添加天数
     */
    public static LocalDate addDays(LocalDate date, int days) {
        try {
            return date.plusDays(days);
        } catch (Exception e) {
            logger.error("Failed to add days to date", e);
            return null;
        }
    }

    /**
     * 在日期上添加月数
     */
    public static LocalDate addMonths(LocalDate date, int months) {
        try {
            return date.plusMonths(months);
        } catch (Exception e) {
            logger.error("Failed to add months to date", e);
            return null;
        }
    }

    /**
     * 在日期上添加年数
     */
    public static LocalDate addYears(LocalDate date, int years) {
        try {
            return date.plusYears(years);
        } catch (Exception e) {
            logger.error("Failed to add years to date", e);
            return null;
        }
    }

    /**
     * 在日期时间上添加小时
     */
    public static LocalDateTime addHours(LocalDateTime dateTime, int hours) {
        try {
            return dateTime.plusHours(hours);
        } catch (Exception e) {
            logger.error("Failed to add hours to date time", e);
            return null;
        }
    }

    /**
     * 在日期时间上添加分钟
     */
    public static LocalDateTime addMinutes(LocalDateTime dateTime, int minutes) {
        try {
            return dateTime.plusMinutes(minutes);
        } catch (Exception e) {
            logger.error("Failed to add minutes to date time", e);
            return null;
        }
    }

    /**
     * 在日期时间上添加秒
     */
    public static LocalDateTime addSeconds(LocalDateTime dateTime, int seconds) {
        try {
            return dateTime.plusSeconds(seconds);
        } catch (Exception e) {
            logger.error("Failed to add seconds to date time", e);
            return null;
        }
    }

    /**
     * 获取本月第一天
     */
    public static LocalDate getFirstDayOfMonth() {
        return getFirstDayOfMonth(LocalDate.now());
    }

    /**
     * 获取指定月份的第一天
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        try {
            return date.withDayOfMonth(1);
        } catch (Exception e) {
            logger.error("Failed to get first day of month", e);
            return null;
        }
    }

    /**
     * 获取本月最后一天
     */
    public static LocalDate getLastDayOfMonth() {
        return getLastDayOfMonth(LocalDate.now());
    }

    /**
     * 获取指定月份的最后一天
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        try {
            return date.withDayOfMonth(date.lengthOfMonth());
        } catch (Exception e) {
            logger.error("Failed to get last day of month", e);
            return null;
        }
    }

    /**
     * 判断是否是闰年
     */
    public static boolean isLeapYear(int year) {
        try {
            return Year.isLeap(year);
        } catch (Exception e) {
            logger.error("Failed to check leap year", e);
            return false;
        }
    }

    /**
     * 判断是否是闰年
     */
    public static boolean isLeapYear(LocalDate date) {
        try {
            return Year.isLeap(date.getYear());
        } catch (Exception e) {
            logger.error("Failed to check leap year", e);
            return false;
        }
    }

    /**
     * 获取星期几（1-7，周一-周日）
     */
    public static int getDayOfWeek(LocalDate date) {
        try {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            int value = dayOfWeek.getValue();
            return value == 7 ? 1 : value;
        } catch (Exception e) {
            logger.error("Failed to get day of week", e);
            return 0;
        }
    }

    /**
     * 获取星期几的中文名称
     */
    public static String getDayOfWeekChinese(LocalDate date) {
        try {
            int dayOfWeek = getDayOfWeek(date);
            String[] weekDays = {"", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};
            return weekDays[dayOfWeek];
        } catch (Exception e) {
            logger.error("Failed to get day of week Chinese name", e);
            return "";
        }
    }
}
