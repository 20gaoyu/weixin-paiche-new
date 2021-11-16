package cn.gy.util;

import cn.gy.core.service.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author kuyun
 * @date 2018年7月4日
 * @description
 */
public class DateTimeUtil {

    public static final String YMDHMS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_FORMAT = "yyyy-MM-dd";
    public static final String YM_FORMAT = "yyyy-MM";
    public static final String MDHM_FORMAT = "MM-dd HH:mm";
    public static final int MS_TO_S = 1000;
    private static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    public static Date addTime(Date date, Integer cnt, String unit) {
        switch (unit) {
            case "m":
                return DateUtils.addMonths(date, cnt);
            case "y":
                return DateUtils.addYears(date, cnt);
            default:
                throw new ServiceException("时间单位错误:" + unit + "");
        }
    }

    /**
     * @param dateTime
     * @return int
     * @description 以 yyyy-MM-dd HH:mm:ss格式输入时间串，返回精度为秒的时间戳
     */
    public static Long getTimeStampByDateTime(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat(YMDHMS_FORMAT);
        Date date;
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
            logger.info(String.format("时间串(%s)转换时间戳异常,置为当前时间.", dateTime));
            date = new Date();
        }

        return date.getTime();
    }

    /**
     * @param timeStamp 精度到秒
     * @return String
     * @description 时间戳转换时间
     */
    public static String getDateTimeByTimeStamp(String timeStamp) {
        Long times;
        try {
            times = Long.parseLong(timeStamp) * MS_TO_S;
        } catch (Exception e) {
            logger.info(String.format("时间戳(%s)转换异常，原值返回", timeStamp));
            return timeStamp;
        }
        return getDateTimeByTimeStamp(times);
    }

    /**
     * @param timeStamp
     * @return String
     * @description 时间戳转换时间
     */
    public static String getDateTimeByTimeStamp(Long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat(YMDHMS_FORMAT);
        Date date = new Date(timeStamp);
        return format.format(date);
    }

    /**
     * 通过相对当前的时间单位和时间值 获得绝对时间的时间戳
     *
     * @param val
     * @param unit
     * @return
     */
    public static int getRelativeTime(int val, String unit) {
        LocalDateTime time = getRelativeLocalDateTime(val, unit);
        return localDateTimeToSecond(time);
    }

    public static LocalDateTime getRelativeLocalDateTime(int val, String unit) {
        LocalDateTime time = LocalDateTime.now();
        switch (unit) {
            case "m":
                time = time.minusMinutes(val);
                break;
            case "h":
                time = time.minusHours(val);
                break;
            case "d":
                time = time.minusDays(val);
                break;
            case "w":
                time = time.minusWeeks(val);
                break;
            case "n":
                time = time.minusMonths(val);
                break;
            default:
                throw new ServiceException("时间单位错误:" + unit + "");
        }
        return time;
    }

    public static int localDateTimeToSecond(LocalDateTime time) {
        return (int) time.toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * @param timeStamp
     * @return String
     * @description 获取时间戳的格式化时间 MM-dd HH:mm
     */
    public static String getMMDDHHMIByTimeStamp(Long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat(MDHM_FORMAT);
        Date date = new Date(timeStamp);
        return format.format(date);
    }
    /**
     * @param decreDays
     * @return Date
     * @description 以当前时间为起点，减量天数后的日期
     */
    public static Date getDateByDecreDays(int decreDays) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dayBeforeNow = now.minusDays((long) decreDays);
        return localDateTimeToDate(dayBeforeNow);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    public static String localDateTimeToString(LocalDateTime time) {
        SimpleDateFormat format = new SimpleDateFormat(YMDHMS_FORMAT);
        return format.format(localDateTimeToDate(time));
    }
    public static String localDateToString(LocalDate time) {
        return localDateTimeToString(LocalDateTime.of(time, LocalTime.MIDNIGHT));
    }
    public static String localDayFormatToString(LocalDate time) {
        return localDateTimeToString(LocalDateTime.of(time, LocalTime.MIDNIGHT),YMD_FORMAT);
    }
    public static String localMonthFormatToString(LocalDate time) {
        return localDateTimeToString(LocalDateTime.of(time, LocalTime.MIDNIGHT),YM_FORMAT);
    }
    public static String localDateTimeToString(LocalDateTime time,String toformat) {
        SimpleDateFormat format = new SimpleDateFormat(toformat);
        return format.format(localDateTimeToDate(time));
    }
    public static String getDateString(Date date) {
        return new SimpleDateFormat(YMDHMS_FORMAT).format(date);
    }

    public static boolean isValidDate(String date, String format) {
        boolean isDate = false;
        try {
            if (StringUtils.isNotBlank(date)) {
                DateFormat df = new SimpleDateFormat(format);
                df.parse(date);
                isDate = true;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return isDate;
    }

    public static boolean isValidDate(String date) {
        return isValidDate(date, YMDHMS_FORMAT);
    }

    /**
     * 根据time和format获取date
     * @param time
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date getDate(String time, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(time);
    }

}
