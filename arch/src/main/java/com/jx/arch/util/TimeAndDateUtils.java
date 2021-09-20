package com.jx.arch.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class TimeAndDateUtils
{
    /**
     * 格式化时间格式
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateAndTime(String date)
    {
        if (GeneralUtils.isNull(date))
        {
            return "";
        }
        String time = date.substring(0, date.indexOf(" "));
        return time.replace("-", "/");
    }

    public static String getStringTime(String date)
    {
        if (GeneralUtils.isNull(date))
        {
            return "";
        }
        String time = date.replace("T", " ");
        int end = date.indexOf("+");
        if (end > 0)
        {
            time = time.substring(0, end);
        }
        return time;
    }

    public static String getTodayOrYesterday(Date date)
    {//date 是存储的时间戳
        //所在时区时8，系统初始时间是1970-01-01 80:00:00，注意是从八点开始，计算的时候要加回去
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis() + offSet) / 86400000;
        long start = (cal.getTimeInMillis() + offSet) / 86400000;
        long intervalTime = start - today;
        //-2:前天,-1：昨天,0：今天,1：明天,2：后天
        String strDes = "";
        if (intervalTime == 0)
        {
            strDes = "今天";//今天
        }
        else if (intervalTime == 1)
        {
            strDes = "明天";
        }
        else
        {
            strDes = "";//直接显示时间
        }
        return strDes;
    }

    /**
     * 获取当前时间
     *
     * @return time 2019-12-25 10:02:48
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static Date getCurrentTimeDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return curDate;
    }

    public static String getCurrentTimeMs()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTimeDateShort()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 判断当前时间是否是今天
     *
     * @param time 2019-12-25 10:02:48
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean isToday(String time)
    {
        if (GeneralUtils.isNullOrZeroLength(time))
        {
            return false;
        }
        String today = getCurrentTimeDateShort();
        String[] date = time.split(" ");
        if (date.length <= 0 || GeneralUtils.isNullOrZeroLength(date[0]))
        {
            return false;
        }
        return today.equals(date[0]);
    }

    /**
     * 将时间转换为时间戳
     */
    @SuppressLint("SimpleDateFormat")
    public static long dateToStamp(String time)
    {
        if (GeneralUtils.isNullOrZeroLength(time))
        {
            return System.currentTimeMillis();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try
        {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return GeneralUtils.isNotNull(date) ? date.getTime() : 0L;
    }

    /**
     * 将UTC时间转换为时间戳
     */
    @SuppressLint("SimpleDateFormat")
    public static long dateToStampUTC(String time) throws ParseException
    {
        if (GeneralUtils.isNullOrZeroLength(time))
        {
            return System.currentTimeMillis();
        }
        time = time.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date date = format.parse(time);
        return date.getTime();
    }

    /**
     * 本地时间转换为utc时间
     *
     * @param localTime 本地时间
     * @return
     */
    public static String localTimeToUTC(String localTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date localDate = null;
        try
        {
            localDate = sdf.parse(localTime);
        } catch (ParseException e)
        {
            e.printStackTrace();
            localDate = getCurrentTimeDate();
        }
        long localTimeInMillis = localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return utcFormat.format(utcDate);
    }

    /**
     * 时间格式化
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatTime(long time)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(time);
        return formatter.format(curDate);
    }

    /**
     * 获取格式化时间
     *
     * @return time 2019-12-25 10:02:48
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatTime(Date date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }


    /**
     * 获取当前时间 HH:mm:ss
     *
     * @return time 10:02:48
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTimeShort()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 获得当天零时零分零秒
     *
     * @return
     */
    public static long getZeroDateByDay()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime().getTime();
    }

    /**
     * 获得当天 23:59:59
     *
     * @return
     */
    public static long getLastDateByDay()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime().getTime();
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date date)
    {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
        {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 获取时间戳的tid
     * 格式化时间,精确到毫秒
     */
    @SuppressLint("SimpleDateFormat")
    public static String getRandomTid()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date curDate = new Date(System.currentTimeMillis());
        String timeFormat = formatter.format(curDate);
        Random random = new Random();
        String result = "";
        result += random.nextInt(9);
        return timeFormat + result;
    }


    /**
     * 获取当前日期
     *
     * @return time 12月25日
     */
    @SuppressLint("SimpleDateFormat")
    public static String getFormatDate(Date date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        return formatter.format(date);
    }

    /**
     * 设置某个时间多少天
     *
     * @param time
     * @param day
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String setTimeOfDay(String time, int day)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date;
        Calendar calender = Calendar.getInstance();
        try
        {
            date = formatter.parse(time);
            calender.setTime(date);
            calender.set(Calendar.DAY_OF_MONTH, calender.get(Calendar.DAY_OF_MONTH) + day);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return formatter.format(calender.getTime());
    }


    /**
     * 计算时间差
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getTimeRange(String startTime, String endTime)
    {
        long rangTime = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CHINA);
        try
        {
            Date startDate = formatter.parse(startTime);
            Date endDate = formatter.parse(endTime);

            Calendar startCalender = Calendar.getInstance();
            startCalender.setTime(startDate);
            long startM = startCalender.getTimeInMillis();

            Calendar endCalender = Calendar.getInstance();
            endCalender.setTime(endDate);
            long endM = endCalender.getTimeInMillis();

            rangTime = endM - startM;

        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        return rangTime;
    }

    public static long getTimeRange(String startTime)
    {
        long rangTime = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CHINA);
        try
        {
            Date startDate = formatter.parse(startTime);


            Calendar startCalender = Calendar.getInstance();
            startCalender.setTime(startDate);
            long startM = startCalender.getTimeInMillis();
            long endM = System.currentTimeMillis();

            rangTime = endM - startM;

        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        return rangTime;
    }

    public static long getDiffDay(String endTime)
    {
        if (GeneralUtils.isNullOrZeroLength(endTime))
        {
            return 0;
        }
        return TimeAndDateUtils.getTimeRange(TimeAndDateUtils.getCurrentTime(), endTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 设置为1号,当前日期既为本月第一天
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getMonthFirstDayTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        return dateFormat.format(c.getTime());
    }

    /**
     * 设置为1号,当前日期既为本月第一天
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getMonthFirstDay()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return dateFormat.format(c.getTime());
    }

    /**
     * 本周的第一天
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getWeekFirstDay()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return dateFormat.format(c.getTime());
    }

    /**
     * 当天的前一天23:59:59
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getBeforeDayTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 当天的前一天00:00:00
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getBeforeZeroDayTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 当天的前一天
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getBeforeDay()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 前一个月的第一天
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getBeforeMonthFirstDay()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return dateFormat.format(c.getTime());
    }

    /**
     * 前一个月的最后一天
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getBeforeMonthLastDay()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 半年前
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getHalfYearAgoTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -6);
        return dateFormat.format(c.getTime());
    }

    /**
     * 现在
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(System.currentTimeMillis());
    }

    /**
     * 半年前
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Long getLongHalfYearAgoTime()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -6);
        return c.getTime().getTime();
    }

    /**
     * 转化时间格式
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String longTimeToString(Long s)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(s);

    }

    /**
     * 判断是否超过多少小时 如：24
     *
     * @param tableTime 业务时间
     * @return boolean
     * @throws Exception
     */
    public static boolean judgmentDate(String tableTime) throws Exception
    {
        String currentTime = getCurrentTime();//当前时间
        if (GeneralUtils.isNullOrZeroLength(tableTime))
        {
            tableTime = currentTime;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        Date start = sdf.parse(tableTime);//业务时间
        Date end = sdf.parse(currentTime);//当前时间
        long cha = end.getTime() - start.getTime();
        if (cha < 0)
        {
            return false;
        }
        double result = cha * 1.0 / (1000 * 60 * 60);
        if (result <= 24)
        {
            return true;//是小于等于 hour 小时
        }
        else
        {
            return false;
        }
    }

}
