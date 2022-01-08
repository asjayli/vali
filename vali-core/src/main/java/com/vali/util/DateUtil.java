package com.vali.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * 日期操作工具类
 *
 * @author luzh
 */
@Slf4j
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
     * 默认日期格式：yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 默认日期格式：yyyyMM
     */
    public static final String DEFAULT_DATE_YEARMONTH_PATTERN = "yyyyMM";
    /**
     * 默认时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认时间戳格式，到毫秒 yyyy-MM-dd HH:mm:ss SSS
     */
    public static final String DEFAULT_DATEDETAIL_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
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
     * 取得系统当前年份
     *
     * @return
     */
    public static int currentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间字符串<br>
     *
     * @param pattern 时间格式化参数<br>
     * @return 当前时间字符串<br>
     */
    public static String getDateString(String pattern) {
        try {
            LocalDateTime arrivalDate = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
            return arrivalDate.format(format);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * 取得当前系统日期
     *
     * @return
     */
    public static Date currentDate() {
        return new Date();
    }


    /**
     * 取得系统当前日期，返回默认日期格式的字符串。
     *
     * @param strFormat
     * @return
     */
    public static String nowDate(String strFormat) {
        Date date = new Date();
        return getDateParser(strFormat).format(date);
    }

    /**
     * 取得当前系统时间戳
     *
     * @return
     */
    public static Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 将日期字符串转换为java.util.Date对象
     *
     * @param dateString
     * @param pattern    日期格式
     * @return
     * @throws Exception
     */
    public static Date getDate(String dateString, String pattern) throws Exception {
        return getDateParser(pattern).parse(dateString);
    }

    /**
     * 将日期字符串转换为java.util.Date对象，使用默认日期格式
     *
     * @param dateString
     * @return
     * @throws Exception
     */
    public static Date getDate(String dateString) throws Exception {
        return getDateParser(DEFAULT_DATE_PATTERN).parse(dateString);
    }

    /**
     * 将时间字符串转换为java.util.Date对象
     *
     * @param dateString
     * @return
     * @throws Exception
     */
    public static Date getDateTime(String dateString) throws Exception {
        return getDateParser(DEFAULT_DATETIME_PATTERN).parse(dateString);
    }

    /**
     * 将java.util.Date对象转换为字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        return getDateParser(pattern).format(date);
    }

    /**
     * 将java.util.Date对象转换为字符串，使用默认日期格式
     *
     * @param date
     * @return
     */
    public static String getDateString(Date date) {
        if (date == null) {
            return "";
        }
        return getDateParser(DEFAULT_DATE_PATTERN).format(date);
    }

    /**
     * 将java.util.Date对象转换为时间字符串，使用默认日期格式
     *
     * @param date
     * @return
     */
    public static String getDateTimeString(Date date) {
        if (date == null) {
            return "";
        }
        return getDateParser(DEFAULT_DATETIME_PATTERN).format(date);
    }

    /**
     * 将日期字符串转换为java.util.Date对象
     *
     * @param dateString
     * @param pattern    日期格式
     * @return
     * @throws Exception
     */
    public static Date toDate(String dateString, String pattern) throws Exception {
        return getDateParser(pattern).parse(dateString);
    }

    /**
     * 将日期字符串转换为java.util.Date对象，使用默认日期格式
     *
     * @param dateString
     * @return
     * @throws Exception
     */
    public static Date toDate(String dateString) throws Exception {
        return getDateParser(DEFAULT_DATE_PATTERN).parse(dateString);
    }

    /**
     * 将时间字符串转换为java.util.Date对象
     *
     * @param dateString
     * @return
     * @throws Exception
     */
    public static Date toDateTime(String dateString) throws Exception {
        return getDateParser(DEFAULT_DATETIME_PATTERN).parse(dateString);
    }

    /**
     * 将java.util.Date对象转换为字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String toDateString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        return getDateParser(pattern).format(date);
    }

    /**
     * 将java.util.Date对象转换为字符串，使用默认日期格式
     *
     * @param date
     * @return
     */
    public static String toDateString(Date date) {
        if (date == null) {
            return "";
        }
        return getDateParser(DEFAULT_DATE_PATTERN).format(date);
    }

    /**
     * 将java.util.Date对象转换为时间字符串，使用默认日期格式
     *
     * @param date
     * @return
     */
    public static String toDateTimeString(Date date) {
        if (date == null) {
            return "";
        }
        return getDateParser(DEFAULT_DATETIME_PATTERN).format(date);
    }

    /**
     * 日期相减
     *
     * @param date
     * @param day
     * @return
     */
    public static Date diffDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) - ((long) day) * MILLIS_A_DAY);
        return c.getTime();
    }

    /**
     * 返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     * @author doumingjun create 2007-04-07
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 服务名称：以实际时长维度计算，不足一天按一天算<br>
     *
     * @param dateBegin
     * @param dateEnd
     * @return 天数<br>
     * @author Minh<br>
     * @since 1.0 2017-12-04<br>
     */
    public static int getDifferOfDayWithHour(Date dateBegin, Date dateEnd) {
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dateBegin);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dateEnd);

        return (int) Math.ceil((double) Math.abs(calEnd.getTimeInMillis() - calBegin.getTimeInMillis()) / MILLIS_A_DAY);
    }

    /**
     * 服务名称：只以天的维度计算<br>
     *
     * @param dateBegin
     * @param dateEnd
     * @return 天数<br>
     * @author Minh<br>
     * @since 1.0 2017-12-04<br>
     */
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
     * 获取两个时间相差的月数
     *
     * @param @param  date1
     * @param @param  date2
     * @param @return
     * @return int
     * @Title: getDifferOfMonth
     * @Description:
     */
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
     * @param @param  date1
     * @param @param  date2
     * @param @return
     * @return int
     * @Title: getDifferOfYear
     * @Description:
     */
    public static int getDifferOfYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        cal1.setTime(date1);
        return cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
    }

    /**
     * 日期相加
     *
     * @param date 日期
     * @param day  天数
     * @return 返回相加后的日期
     * @author doumingjun create 2007-04-07
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * MILLIS_A_DAY);
        return c.getTime();
    }

    /**
     * 日期增加年数
     *
     * @param date
     * @param year
     * @return
     * @author zhf
     */
    public static Date addYear(Date date, int year) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.add(Calendar.YEAR, year);
        return calender.getTime();
    }

    /**
     * 日期增加月数
     *
     * @param date
     * @return
     * @author zhf
     */
    public static Date addMonth(Date date, int month) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.add(Calendar.MONTH, month);
        return calender.getTime();
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
     * 获取月份起始日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getFirstDateOfMonth(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(getFirstDateOfMonth(sdf.parse(date)));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取月份起始日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取月份最后日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getLastDateOfMonth(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(getLastDateOfMonth(sdf.parse(date)));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取月份最后日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getLastDateOfMonth(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取日期所在星期的第一天，这里设置第一天为星期日
     *
     * @param datestr
     * @return
     */
    public static String getFirstDateOfWeek(String datestr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(datestr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 这里设置从周日开始
            return sdf.format(cal.getTime());

        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取日期所在当年的第几周
     *
     * @param datestr
     * @return
     */
    public static int getWeekOfYear(String datestr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(datestr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.WEEK_OF_YEAR);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return -1;
    }

    public static String getWeekday(String datestr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(datestr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
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
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static Date getDate(Object object) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
            if (object instanceof String) {
                try {
                    date = format.parse((String) object);
                } catch (Exception e) {
                    try {
                        date = format2.parse((String) object);
                    } catch (Exception ee) {
                        date = format3.parse((String) object);
                    }
                }
            } else if (object instanceof Date) {
                date = (Date) object;
            } else if (object instanceof Timestamp) {
                date = (Date) object;
            } else {
                date = new Date();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return date;
    }

    public static Date addHour(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) hour) * MILLIS_A_HOUR);
        return c.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) minute) * MILLIS_A_MINUTE);
        return c.getTime();
    }
}