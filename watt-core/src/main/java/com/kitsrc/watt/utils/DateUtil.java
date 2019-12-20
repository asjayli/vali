package com.kitsrc.watt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2019/5/14  10:17
 * Description:
 */

public class DateUtil {

    public static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String DEFAULT_DAY = "dd";
    public static String DEFAULT_MONTH = "MM";
    public static String DEFAULT_SHORT_YEAR = "yy";
    public static String DEFAULT_LONG_YEAR = "yyyy";
    public static String DEFAULT_PATTERN_YM = "yyyyMM";
    public static String DEFAULT_PATTERN_YMD = "yyyyMMdd";
    public static String DEFAULT_PATTERN_Y_M = "yyyy-MM";
    public static String DEFAULT_PATTERN_Y_M_D = "yyyy-MM-dd";
    public static String DEFAULT_PATTERN_Y_M_D_H_M_S_S = "yyyy-MM-dd HH:mm:ss.s";

    /**
     * 默认日期格式：yyyyMM        (1970-01）
     */
    public static final String DEFAULT_PATTERN_TO_MONTH = "yyyy-MM";
    /**
     * 默认日期格式：yyyy-MM-dd    (1970-01-01)
     */
    public static final String DEFAULT_PATTERN_TO_DAY = "yyyy-MM-dd";
    /**
     * 默认时间格式：yyyy-MM-dd HH:mm:ss   (1970-01-01 00:00:00)
     */
    public static final String DEFAULT_PATTERN_TO_SECOND = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认时间戳格式，到毫秒 yyyy-MM-dd HH:mm:ss SSS  (1970-01-01 00:00:00 000)
     */
    public static final String DEFAULT_PATTERN_TO_MILLIS = "yyyy-MM-dd HH:mm:ss SSS";
    /**
     * 1天折算成毫秒数
     */
    public static final long MILLIS_A_DAY = 24 * 3600 * 1000;
    /**
     * 1小时折算成毫秒数
     */
    public static final long MILLIS_A_HOUR = 3600 * 1000;
    /**
     * 1分钟折算成毫秒数
     */
    public static final long MILLIS_A_MINUTE = 60 * 1000;

    private static SimpleDateFormat getDateParser(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    public static Date currentDate() {
        return new Date();
    }

    /**
     * 将LocalDateTime类型的时间转换为Date类型
     *
     * @param localDateTime 给定日期 java.time.LocalDateTime
     * @return
     */
    public static Date convertToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    /**
     * 将LocalDate 转换成 Date
     *
     * @param localDate 给定日期 java.time.LocalDate
     * @return
     */
    public static Date convertToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 将 Date 转换成LocalDate
     * atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
     *
     * @param date 给定日期 java.util.Date
     * @return java.time.LocalDate
     */
    public static LocalDate convertToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 将long型时间戳转换成LocalDateTime
     * atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
     *
     * @param timestamp 给定的long型10位时间戳
     * @return
     */
    public static LocalDate convertToLocalDate(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 将 Date 转换成LocalDateTime
     * atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
     *
     * @param date 给定日期 java.util.Date
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime convertToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 将long型时间戳转换成LocalDateTime
     * atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
     *
     * @param timestamp 给定的long型13位时间戳
     * @return
     */
    public static LocalDateTime convertToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 为日期增加天数
     *
     * @param date 给定日期 java.util.Date
     * @param day  要加的天数
     * @return
     */
    public static Date addDate(Date date, int day) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);

        return convertToDate(localDateTime.plusDays(day));
    }

    /**
     * 为日期增加天数
     *
     * @param localDateTime 给定日期 java.time.LocalDateTime
     * @param day           要加的天数
     * @return
     */
    public static LocalDateTime addDate(LocalDateTime localDateTime, int day) {

        return localDateTime.plusDays(day);
    }

    /**
     * 为日期增加小时数
     *
     * @param date 给定日期 java.util.Date
     * @param hour 要加的小时数
     * @return
     */
    public static Date addHour(Date date, int hour) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneId);
        return convertToDate(localDateTime.plusHours(hour));
    }

    /**
     * 为日期增加小时数
     *
     * @param localDateTime 给定日期  java.time.LocalDateTime
     * @param hour          要加的小时数
     * @return
     */
    public static LocalDateTime addHour(LocalDateTime localDateTime, int hour) {

        return localDateTime.plusHours(hour);
    }

    /**
     * 为日期增加分钟数
     *
     * @param date   给定日期
     * @param minute 要加的分钟数
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneId);
        return convertToDate(localDateTime.plusMinutes(minute));
    }

    /**
     * 为日期增加分钟数
     *
     * @param localDateTime 给定日期
     * @param minute        要加的分钟数
     * @return
     */
    public static LocalDateTime addMinute(LocalDateTime localDateTime, int minute) {

        return localDateTime.plusMinutes(minute);
    }

    /**
     * 为日期加月数
     *
     * @param date  给定日期 java.util.Date
     * @param month 要加的月数
     * @return
     */
    public static Date addMonth(Date date, int month) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneId);
        return convertToDate(localDateTime.plusMonths(month));
    }

    /**
     * 为日期加月数
     *
     * @param localDateTime 给定日期 java.time.LocalDateTime
     * @param month         要加的月数
     * @return
     */
    public static LocalDateTime addMonth(LocalDateTime localDateTime, int month) {

        return localDateTime.plusMonths(month);
    }

    /**
     * 为日期加年数
     *
     * @param date 给定日期 java.util.Date
     * @param year 要加的年数
     * @return
     */
    public static Date addYear(Date date, int year) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneId);
        return convertToDate(localDateTime.plusYears(year));
    }

    /**
     * @param localDateTime 给定日期 java.time.LocalDateTime
     * @param year          要加的年数
     * @return
     */
    public static LocalDateTime addYear(LocalDateTime localDateTime, int year) {
        return localDateTime.plusYears(year);
    }

    /**
     * 获取long型时间戳
     *
     * @param date 日期
     * @return
     */
    public static long getMillis(Date date) {
        return date.toInstant().toEpochMilli();
    }

    /**
     * 获取long型时间戳
     *
     * @param localDateTime 日期
     * @return
     */
    public static long getMillis(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return instant.toEpochMilli();
    }
    /**
     * 将日期字符串转换为java.util.Date对象，使用默认日期格式
     *
     * @param dateString
     * @return
     * @throws Exception
     */
    public static Date toDate(String dateString) throws Exception {
        return getDateParser(DEFAULT_PATTERN_TO_DAY).parse(dateString);
    }

    /**
     * 将时间字符串转换为java.util.Date对象
     *
     * @param dateString
     * @return
     * @throws Exception
     */
    public static Date toDateTime(String dateString) throws Exception {
        return getDateParser(DEFAULT_PATTERN_TO_SECOND).parse(dateString);
    }
    /**
     * 将LocalDateTime 按照一定的格式转换成String
     *
     * @param localDateTime 给定日期 java.time.LocalDateTime
     * @param pattern       日期格式
     * @return
     */
    public static String toDateString(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将Date 按照一定的格式转换成String
     *
     * @param date    给定日期  java.util.Date
     * @param pattern 日期格式
     * @return
     */
    public static String toDateString(Date date, String pattern) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将java.util.Date对象转换为字符串，使用默认日期格式yyyy-MM-dd
     *
     * @param date 给定日期 java.util.Date
     * @return
     */
    public static String toDateString(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN_TO_DAY));
    }

    /**
     * 将java.time.LocalDateTime对象转换为字符串，使用默认日期格式yyyy-MM-dd
     *
     * @param localDateTime 给定日期 java.time.LocalDateTime
     * @return
     */
    public static String toDateString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN_TO_DAY));
    }

    /**
     * 将java.time.LocalDate对象转换为字符串，使用默认日期格式yyyy-MM-dd
     *
     * @param localDate 给定日期 java.time.LocalDate
     * @return
     */
    public static String toDateString(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN_TO_DAY));
    }

    /**
     * 将字符串日期转为Date类型时间，可指定格式
     * 如：2019-01-01 对应格式 yyyy-MM-dd
     * 2019-01-01 01:01:01 对应格式 yyyy-MM-dd HH:mm:ss
     *
     * @param dateString 字符串日期
     * @param pattern    日期格式
     * @return
     */
    public static Date parseDateString(String dateString, String pattern) {
        if (StringUtils.isAnyEmpty(dateString, pattern)) {
            return null;
        }
        LocalDateTime localDateTime;
        try {
            localDateTime = parseLocalDateTime(dateString, pattern);
        } catch (Exception e) {
            LocalDate localDate = parseLocalDate(dateString, pattern);
            localDateTime = localDate == null ? null : localDate.atStartOfDay();
        }

        return convertToDate(localDateTime);
    }


    /**
     * 将String 按照pattern 转换成LocalDate
     *
     * @param time    字符串日期
     * @param pattern 日期格式
     * @return
     */
    public static LocalDate parseLocalDate(String time, String pattern) {
        return LocalDate.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将String 按照pattern 转换成LocalDateTime
     *
     * @param time    字符串日期
     * @param pattern 日期格式
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String time, String pattern) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将date转换成String
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateFormat(Date date, String pattern) {
        return toDateString(convertToLocalDateTime(date), pattern);
    }


    /**
     * 计算两个Date之间的毫秒数
     *
     * @param time1 减去的时间
     * @param time2 被减的时间
     * @return
     */
    public static Long minusToMills(Date time1, Date time2) {
        return minusToMills(convertToLocalDateTime(time1), convertToLocalDateTime(time2));
    }

    /**
     * 计算两个LocalDateTime 之间的毫秒数
     *
     * @param time1 减去的时间
     * @param time2 被减的时间
     * @return
     */
    public static Long minusToMills(LocalDateTime time1, LocalDateTime time2) {
        return Duration.between(time1, time2).toMillis();
    }

    /**
     * 计算两个LocalTime 之间的毫秒数
     *
     * @param time1 减去的时间
     * @param time2 被减的时间
     * @return
     */
    public static Long minusToMills(LocalTime time1, LocalTime time2) {
        return Duration.between(time1, time2).toMillis();
    }

    /**
     * 计算两个LocalDate 之间的毫秒数
     *
     * @param time1 减去的时间
     * @param time2 被减的时间
     * @return
     */
    public static Long minusToMills(LocalDate time1, LocalDate time2) {
        return Duration.between(time1, time2).toMillis();
    }

    /**
     * 计算两个LocalDate 之间的Period
     * Period对象可获取两个日期之间的年数，月数，天数
     * period.getYears(),period.getMonths(),period.getDays()
     *
     * @param time1 减去的日期
     * @param time2 被减的日期
     * @return
     */
    public static Period betweenLocalDate(LocalDate time1, LocalDate time2) {
        return Period.between(time1, time2);
    }

    /**
     * 计算两个Date 之间的Period
     * Period对象可获取两个日期之间的年数，月数，天数
     * period.getYears(),period.getMonths(),period.getDays()
     *
     * @param date1 减去的日期
     * @param date2 被减的日期
     * @return
     */
    public static Period betweenPeriod(Date date1, Date date2) {
        return betweenLocalDate(convertToLocalDate(date1), convertToLocalDate(date2));
    }

    /**
     * 计算两个LocalDateTime之间的Duration
     * Duration对象可获取两个日期之间的天数，小时数，分钟数，秒数，毫秒数
     * duration.toDays(),duration.toHours(),duration.toMinutes(),
     * duration.getSeconds(),duration.toMillis();duration.toNanos()等
     *
     * @param time1 减去的日期
     * @param time2 被减的日期
     * @return
     */
    public static Duration betweenLocalDateTime(LocalDateTime time1, LocalDateTime time2) {
        return Duration.between(time1, time2);
    }

    /**
     * 计算两个Date (java.util.Date)之间的Duration
     * Duration对象可获取两个日期之间的天数，小时数，分钟数，秒数，毫秒数
     * duration.toDays(),duration.toHours(),duration.toMinutes(),
     * duration.getSeconds(),duration.toMillis();duration.toNanos()等
     *
     * @param date1 减去的日期
     * @param date2 被减的日期
     * @return
     */
    public static Duration betweenDuration(Date date1, Date date2) {
        return betweenLocalDateTime(convertToLocalDateTime(date1), convertToLocalDateTime(date1));
    }

    /**
     * 根据季度获得相应的月份
     *
     * @param quarters 季度
     * @return 返回相应的月份
     */
    public static String getMonth(String quarters) {
        String month;
        int m = Integer.parseInt(quarters);
        m = m * 3 - 2;
        if (m > 0 && m < 10) {
            month = "0" + String.valueOf(m);
        } else {
            month = String.valueOf(m);
        }
        return month;
    }

    /**
     * 根据月份获得相应的季度
     *
     * @param month 月份
     * @return 返回相应的季度
     */
    public static String getQuarters(String month) {
        String quarters = null;
        int m = Integer.parseInt(month);
        if (m == 1 || m == 2 || m == 3) {
            quarters = "1";
        }
        if (m == 4 || m == 5 || m == 6) {
            quarters = "2";
        }
        if (m == 7 || m == 8 || m == 9) {
            quarters = "3";
        }
        if (m == 10 || m == 11 || m == 12) {
            quarters = "4";
        }
        return quarters;
    }

    /**
     * 根据给定时间，获取星期几
     *
     * @param localDate
     * @return
     */
    public static String getWeekday(LocalDate localDate) {
        int week = localDate.get(WeekFields.of(DayOfWeek.of(1), 1).dayOfWeek());
        switch (week) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            default:
                return "星期天";
        }
    }

    /**
     * 根据给定时间，获取星期几
     *
     * @param dateString
     * @return
     */
    public static String getWeekday(String dateString) {
        LocalDate localDate = parseLocalDate(dateString, DEFAULT_PATTERN_TO_DAY);
        int week = localDate.get(WeekFields.of(DayOfWeek.of(1), 1).dayOfWeek());
        switch (week) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            default:
                return "星期天";
        }
    }

    /**
     * 根据给定时间，获取其在当年的第几周
     *
     * @param localDateTime 给定日期 java.time.LocalDateTime
     * @return
     */
    public static int getWeekOfYear(LocalDateTime localDateTime) {
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        return localDateTime.get(weekFields.weekOfYear());
    }

    /**
     * 根据给定日期，获取其在当年的第几周
     *
     * @param date 给定日期 java.util.Date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        return localDateTime.get(weekFields.weekOfYear());
    }

    /**
     * 计算两个日期之间相差的天数， 只以天的维度计算
     *
     * @param dateBegin 减去的日期
     * @param dateEnd   被减的日期
     * @return
     */
    public static int getDifferDays(Date dateBegin, Date dateEnd) {
        Period period = betweenPeriod(dateBegin, dateEnd);
        return period.getDays();
    }

    /**
     * 计算两个日期之间相差的天数， 只以天的维度计算
     *
     * @param dateBegin 开始日期
     * @param dateEnd   结束日期
     * @return 天数<br>
     * @see DateUtil#getDifferDays(Date, Date)
     */
    @Deprecated
    public static int getDifferOfDay(Date dateBegin, Date dateEnd) {
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dateBegin);
        calBegin.set(Calendar.HOUR_OF_DAY, 0);
        calBegin.set(Calendar.MINUTE, 0);
        calBegin.set(Calendar.SECOND, 0);
        calBegin.set(Calendar.MILLISECOND, 0);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dateEnd);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);

        int days = (int) ((calEnd.getTimeInMillis() - calBegin.getTimeInMillis()) / MILLIS_A_DAY);
        days = Math.abs(days) + 1;

        return days;
    }


    /**
     * 服务名称：以实际时长维度计算，不足一天按一天算<br>
     *
     * @param dateBegin 开始日期
     * @param dateEnd   结束日期
     * @return 天数<br>
     */
    public static int getDifferOfDayWithHour(Date dateBegin, Date dateEnd) {
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dateBegin);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dateEnd);

        return (int) Math.ceil((double) Math.abs(calEnd.getTimeInMillis() - calBegin.getTimeInMillis()) / MILLIS_A_DAY);
    }

    /**
     * 获取两个时间相差的月数
     *
     * @param date1 被减的时间
     * @param date2 减去的时间
     * @return
     */
    public static int getDifferMonths(Date date1, Date date2) {
        Period period = betweenPeriod(date1, date2);
        return period.getMonths();
    }

    /**
     * 获取两个时间相差的月数
     *
     * @param date1 被减的时间
     * @param date2 减去的时间
     * @param
     * @return int
     * @Title: getDifferOfMonth
     * @Description:
     * @see DateUtil#getDifferMonths(Date, Date)
     */
    @Deprecated
    public static int getDifferOfMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        cal1.setTime(date1);
        return (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH)
                - cal2.get(Calendar.MONTH);
    }

    /**
     * 获取两个时间相差的年数
     *
     * @param date1 减去的时间
     * @param date2 被减的时间
     * @return
     */
    public static int getDifferYears(Date date1, Date date2) {
        Period period = betweenPeriod(date1, date2);
        return period.getYears();
    }

    /**
     * 获取两个时间相差的年数
     *
     * @param  date1 减去的时间
     * @param  date2 被减的时间
     * @return int
     * @Title: getDifferOfYear
     * @Description:
     * @see DateUtil#getDifferYears(Date, Date)
     */
    @Deprecated
    public static int getDifferOfYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        cal1.setTime(date1);
        return cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
    }

    /**
     * 获取月份的起始日期
     *
     * @param date 给定日期 java.util.Date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return convertToDate(localDateTime.with(TemporalAdjusters.firstDayOfMonth()));
    }

    /**
     * 获取月份的起始日期
     *
     * @param dateString 给定字符串日期
     * @return
     */
    public static String getFirstDayOfMonth(String dateString) {
        Date date = parseDateString(dateString, DEFAULT_PATTERN_TO_DAY);
        return toDateString(getFirstDayOfMonth(date));
    }

    /**
     * 获取月份起始日期
     *
     * @param date 给定日期
     * @return
     * @throws
     * @see DateUtil#getFirstDayOfMonth(Date)
     */
    @Deprecated
    public static Date getFirstDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取月份起始日期
     *
     * @param date
     * @return
     * @throws
     * @see DateUtil#getFirstDayOfMonth(String)
     */
    @Deprecated
    public static String getFirstDateOfMonth(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(getFirstDateOfMonth(sdf.parse(date)));
        } catch (ParseException e) {

        }
        return null;
    }

    /**
     * 获取日期所在星期的第一天，这里设置第一天为星期日
     *
     * @param datestr
     * @return
     * @see DateUtil#getFirstDayOfWeek(String)
     */
    @Deprecated
    public static String getFirstDateOfWeek(String datestr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(datestr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 这里设置从周日开始
            return sdf.format(cal.getTime());

        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 获取日期所在星期的第一天，这里设置第一天为星期日
     *
     * @param date 给定日期
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        LocalDate localDate = convertToLocalDate(date);
        TemporalField temporalField = WeekFields.of(Locale.CHINA).dayOfWeek();
        return convertToDate(localDate.with(temporalField, 1L));
    }

    /**
     * 获取日期所在星期的第一天，这里设置第一天为星期日
     *
     * @param dateString 给定的字符串日期
     * @return
     */
    public static String getFirstDayOfWeek(String dateString) {
        LocalDate date = parseLocalDate(dateString, DEFAULT_PATTERN_TO_DAY);
        TemporalField temporalField = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        return toDateString(localDate.with(temporalField, 1L));
    }

    /**
     * 获取日期所在星期的最后一天，这里设置第一天为星期日
     *
     * @param date 给定日期
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        LocalDate localDate = convertToLocalDate(date);
        TemporalField temporalField = WeekFields.of(Locale.CHINA).dayOfWeek();
        return convertToDate(localDate.with(temporalField, 7L));
    }

    /**
     * 获取日期所在星期的最后一天，这里设置第一天为星期日
     *
     * @param dateString 给定的字符串日期
     * @return
     */
    public static String getLastDayOfWeek(String dateString) {
        LocalDate date = parseLocalDate(dateString, DEFAULT_PATTERN_TO_DAY);
        TemporalField temporalField = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        return toDateString(localDate.with(temporalField, 7L));
    }

    /**
     * 获取月份最后一天的日期
     *
     * @param date 给定日期 java.util.Date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return convertToDate(localDateTime.with(TemporalAdjusters.lastDayOfMonth()));
    }

    /**
     * 获取月份最后一天的日期
     *
     * @param dateString
     * @return
     */
    public static String getLastDayOfMonth(String dateString) {
        Date date = parseDateString(dateString, DEFAULT_PATTERN_TO_DAY);
        return toDateString(getLastDayOfMonth(date));
    }

    /**
     * 获取月份最后一天的日期
     *
     * @param date
     * @return
     * @throws ParseException
     * @see DateUtil#getLastDayOfMonth(Date)
     */
    @Deprecated
    public static Date getLastDateOfMonth(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取月份最后一天的日期
     *
     * @param date 给定的字符串日期
     * @return
     * @throws ParseException
     * @see DateUtil#getLastDayOfMonth(String)
     */
    @Deprecated
    public static String getLastDateOfMonth(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(getLastDateOfMonth(sdf.parse(date)));
        } catch (ParseException e) {

        }
        return null;
    }
}
