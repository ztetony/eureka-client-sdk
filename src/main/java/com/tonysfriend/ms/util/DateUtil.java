package com.tonysfriend.ms.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * @author tony.lu
 */
public class DateUtil {

    public final static String DATE_FORMAT_1 = "yyyy-MM-dd";

    public final static String DATE_FORMAT_2 = "yyyyMMdd";

    public final static String DATE_FORMAT_4 = "yyyy-MM";

    public final static String DATE_FORMAT_5 = "yyyyMM";

    public final static String DATE_FORMAT_6 = "yyyy";

    public final static String DATETIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";

    public final static String DATETIME_FORMAT_3 = "yyyy-MM-dd HH-mm-ss";

    public final static String DATETIME_FORMAT_31 = "yyyy-M-d HH-mm-ss";

    public final static String DATETIME_FORMAT_32 = "yyyy-M-d H:m:s";

    public final static String DATETIME_FORMAT_2 = "yyyyMMddHHmmss";

    public final static String DATETIME_FORMAT_4 = "yyyy/MM/dd HH:mm:ss";

    public final static String DATETIME_FORMAT_5 = "yyyy/MM/dd HH:mm";

    public final static String DATE_FORMAT_3 = "yyyy年MM月dd日";

    public static String DEFAULT_DATE_FORMAT = DATE_FORMAT_1;

    public static String DEFAULT_TIME_FORMAT = DATETIME_FORMAT_1;

    public static java.sql.Date getNowDate() {
        return new java.sql.Date(new Date().getTime());
    }

    public static java.sql.Timestamp getTimestamp() {
        return new java.sql.Timestamp(new Date().getTime());
    }

    public static String date2String(Date date) {
        return date2String(date, DEFAULT_DATE_FORMAT);
    }

    public static String date2String(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = getDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获得处理过后的时间字符串(用于最近来访) 如： 1、刚刚 2、n分钟前 3、n小时前 4、今天 08:08 5、昨天 08:08 6、前天
     * 08:08 7、01-01 08:08
     *
     * @return
     */
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    @SuppressWarnings("deprecation")
    public static String getDateStr(Date createDate) {
        long nowTime = new Date().getTime();

        long createTime = createDate.getTime();

        long timeDiff = nowTime - createTime;
        if ((timeDiff / 1000) < 60)
            return "刚刚";
        else if ((timeDiff / 1000 / 60) < 60)
            return (timeDiff / 1000 / 60) + "分钟前";
        else if ((timeDiff / 1000 / 60 / 60) < 24)
            return (timeDiff / 1000 / 60 / 60) + "小时前";
        else if ((timeDiff / 1000 / 60 / 60 / 24) < 30)
            return (timeDiff / 1000 / 60 / 60 / 24) + "天前";
        else
            return getCreateDay(createDate) + " " + getCreateTime2(createDate);
    }

    private static String getCreateTime2(Date createDate) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm");
        return sdFormat.format(createDate);
    }

    private static String getCreateDay(Date createDate) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("MM-dd");
        return sdFormat.format(createDate);
    }

    /**
     * @return
     */
    public static String getSemesters(String sdate) {
        Date dd = strToDate(sdate);
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(dd);
        int aWeek = c.get(Calendar.WEEK_OF_YEAR);

        String bDate = c.get(Calendar.YEAR) + "-09-01";
        Date bDd = strToDate(bDate);
        Calendar bC = Calendar.getInstance(Locale.CHINA);
        bC.setTime(bDd);
        int bWeek = bC.get(Calendar.WEEK_OF_YEAR);

        String cDate = c.get(Calendar.YEAR) + "-09-01";
        Date cDd = strToDate(cDate);
        Calendar cC = Calendar.getInstance(Locale.CHINA);
        cC.setTime(cDd);
        int cWeek = cC.get(Calendar.WEEK_OF_YEAR);
        return c.get(Calendar.MONTH) + 1 > 8 || c.get(Calendar.MONTH) + 1 < 3
                || aWeek == bWeek || aWeek == cWeek ? "上学期" : "下学期";
    }

    /**
     * @return
     */
    public static String getYears(String sdate) {
        Date dd = strToDate(sdate);
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(dd);
        int aWeek = c.get(Calendar.WEEK_OF_YEAR);

        String bDate = c.get(Calendar.YEAR) + "-09-01";
        Date bDd = strToDate(bDate);
        Calendar bC = Calendar.getInstance(Locale.CHINA);
        bC.setTime(bDd);

        int bWeek = bC.get(Calendar.WEEK_OF_YEAR);

        if (c.get(Calendar.MONTH) + 1 > 8 || aWeek == bWeek) {
            String years = String.valueOf(c.get(Calendar.YEAR)) + "-";
            c.add(Calendar.YEAR, 1);
            years += String.valueOf(c.get(Calendar.YEAR));
            return years;
        } else {
            String years = "-" + String.valueOf(c.get(Calendar.YEAR));
            c.add(Calendar.YEAR, -1);
            years = String.valueOf(c.get(Calendar.YEAR)) + years;
            return years;
        }
    }

    /**
     * @return
     */
    public static String getSeqWeek(String sdate) {
        Date dd = strToDate(sdate);
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(dd);
        int week = c.get(Calendar.WEEK_OF_YEAR);

        int weekJilu = 0;

        String beginsDate = c.get(Calendar.YEAR) + "-09-01";
        Date beginsDd = strToDate(beginsDate);
        Calendar beginsC = Calendar.getInstance(Locale.CHINA);
        beginsC.setTime(beginsDd);
        int beginsWeek = beginsC.get(Calendar.WEEK_OF_YEAR);

        String yiYueErYueDate = c.get(Calendar.YEAR) + "-03-01";
        Date yiYueErYue = strToDate(yiYueErYueDate);
        Calendar yiYueErYueC = Calendar.getInstance(Locale.CHINA);
        yiYueErYueC.setTime(yiYueErYue);
        int yiYueErYueWeek = yiYueErYueC.get(Calendar.WEEK_OF_YEAR);

        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        if (week <= yiYueErYueWeek && dd.getTime() < yiYueErYue.getTime()) {// 1月到2月底的算法

            Date beginDate = strToDate(c.get(Calendar.YEAR) + "-03-01");
            Calendar date = Calendar.getInstance(Locale.CHINA);
            date.setTime(beginDate);
            date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);

            String yiYueErYueDiDate = dft.format(date.getTime());// 得到2月底是几号
            Date yiYueErYueDi = strToDate(yiYueErYueDiDate);
            Calendar yiYueErYueDiC = Calendar.getInstance(Locale.CHINA);
            yiYueErYueDiC.setTime(yiYueErYueDi);
            int yiYueErYueDiWeek = yiYueErYueDiC.get(Calendar.WEEK_OF_YEAR);// 得到当年2月底时第几周

            c.add(Calendar.YEAR, -1);
            String years = String.valueOf(c.get(Calendar.YEAR));

        }

        if (week <= beginsWeek && week >= yiYueErYueWeek
                && dd.getTime() >= yiYueErYue.getTime()
                && dd.getTime() < beginsDd.getTime()) {

            weekJilu = (week - yiYueErYueWeek) + 1; // 3月1号-8月31号的算法
        }
        if (week >= beginsWeek && dd.getTime() >= beginsDd.getTime()) {// 9月1号的算法
            weekJilu = (week - beginsWeek) + 1;
        }
        if (week >= beginsWeek) {// 9月1号的算法
            weekJilu = (week - beginsWeek) + 1;
        }
        String weeks = Integer.toString(weekJilu);
        if (weeks.length() == 1)
            weeks = "0" + weeks;
        return weeks;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        if (strDate == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date strToDateByFormat(String strDate, String format)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        // ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate);
        return strtodate;
    }

    /**
     * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     *
     * @param sdate
     * @param num
     * @return
     */
    public static String getWeek(String sdate, String num) {
        // 再转换为时间
        Date dd = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")) // 返回星期一所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        else if (num.equals("2")) // 返回星期二所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        else if (num.equals("3")) // 返回星期三所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        else if (num.equals("4")) // 返回星期四所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        else if (num.equals("5")) // 返回星期五所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        else if (num.equals("6")) // 返回星期六所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        else if (num.equals("0")) // 返回星期日所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 换成要求格式的时间表示 "yyyy-MM-dd HH:mm:ss"
     *
     * @param dateTime  long：long型格式的时间
     * @param formatStr String
     * @return String：要求格式的时间字符串
     */
    public static String getDateTime(Date dateTime, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String strDate = sdf.format(dateTime);
        return strDate;
    }

    /**
     * 获取时间戳 Jul 6, 2011
     *
     * @return String
     */
    public static String getTimeStamp(String format) {
        SimpleDateFormat dateFm = new SimpleDateFormat(format); // 格式化当前系统日期
        return dateFm.format(new Date());
    }

    /**
     * 获取时间段天数
     *
     * @param now
     * @param before
     * @return
     */
    public static long diffDays(Date now, Date before) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long days = 0;
        try {
            // Date d1 = df.parse("2004-03-26 13:31:40");
            // Date d2 = df.parse("2004-01-02 11:30:24");
            long diff = now.getTime() - before.getTime();
            days = diff / (1000 * 60 * 60 * 24);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 获取时间段内的天数
     *
     * @param createat
     * @param leaveat
     * @param startdate
     * @param enddate
     * @param uid
     * @return
     */
    public static long diffDays(Date createat, Date leaveat, String startdate,
                                String enddate, long uid) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long days = -1;
        try {
            Date ds = df.parse(startdate);
            Date de = df.parse(enddate);

            if (df.parse(df.format(createat)).after(de)) {
                days = 0;
            } else if (df.format(createat).equals(enddate)) {
                days = 1;
            } else if (ds.before(createat)
                    || df.format(createat).equals(startdate)) {
                if (leaveat == null) {
                    long diff = df.parse(df.format(de)).getTime()
                            - df.parse(df.format(createat)).getTime();
                    days = diff / (1000 * 60 * 60 * 24) + 1;
                } else if (leaveat.before(de)
                        || df.format(leaveat).equals(enddate)) {
                    long diff = df.parse(df.format(leaveat)).getTime()
                            - df.parse(df.format(createat)).getTime();
                    days = diff / (1000 * 60 * 60 * 24);
                } else {
                    long diff = df.parse(df.format(de)).getTime()
                            - df.parse(df.format(createat)).getTime();
                    days = diff / (1000 * 60 * 60 * 24) + 1;
                }
            } else if (ds.after(createat)
                    || df.format(createat).equals(startdate)) {

                if (leaveat == null) {
                    long diff = df.parse(df.format(de)).getTime()
                            - df.parse(df.format(ds)).getTime();
                    days = diff / (1000 * 60 * 60 * 24) + 1;
                } else if (leaveat.before(ds)
                        || df.format(leaveat).equals(startdate)) {
                    return 0;
                } else if ((leaveat.after(ds))
                        && (leaveat.before(de) || df.format(leaveat).equals(
                        enddate))) {
                    long diff = df.parse(df.format(leaveat)).getTime()
                            - df.parse(df.format(ds)).getTime();
                    days = diff / (1000 * 60 * 60 * 24);
                } else if (leaveat.after(de)
                        || df.format(leaveat).equals(enddate)) {
                    long diff = df.parse(df.format(de)).getTime()
                            - df.parse(df.format(ds)).getTime();
                    days = diff / (1000 * 60 * 60 * 24) + 1;
                } else {
                    long diff = df.parse(df.format(de)).getTime()
                            - df.parse(df.format(ds)).getTime();
                    days = diff / (1000 * 60 * 60 * 24) + 1;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return days;
    }

    /**
     * 当前日期-时间
     *
     * @return
     */
    public static String getCurDateStr() {
        Calendar cal = Calendar.getInstance();// 使用日历类
        int year = cal.get(Calendar.YEAR);// 得到年
        int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
        int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
        int hour = cal.get(Calendar.HOUR);// 得到小时
        int minute = cal.get(Calendar.MINUTE);// 得到分钟
        int second = cal.get(Calendar.SECOND);// 得到秒
        String fullDateStr = year + "-" + month + "-" + day + " " + hour + ":"
                + minute + ":" + second;
        System.out.println("结果：" + fullDateStr);
        return fullDateStr;
    }

    /**
     * 当前年
     *
     * @return
     */
    public static String yearStr() {
        Calendar cal = Calendar.getInstance();// 使用日历类
        int year = cal.get(Calendar.YEAR);// 得到年
        return String.valueOf(year);
    }

    /**
     * 当前月份
     *
     * @return
     */
    public static String monthStr() {
        Calendar cal = Calendar.getInstance();// 使用日历类

        int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
        return String.valueOf(month);
    }

    /**
     * 当前月份-full
     *
     * @return
     */
    public static String fullMonthStr() {
        Calendar cal = Calendar.getInstance();// 使用日历类

        int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
        if (month > 9)
            return String.valueOf(month);
        else
            return "0" + String.valueOf(month);
    }

    /**
     * 当前天，几号
     *
     * @return
     */
    public static String dayStr() {
        Calendar cal = Calendar.getInstance();// 使用日历类
        int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
        if (day < 10)
            return "0" + day;

        return String.valueOf(day);
    }

    /**
     * 当前小时
     *
     * @return
     */
    public static String hourStr() {
        Calendar cal = Calendar.getInstance();// 使用日历类
        int hour = cal.get(Calendar.HOUR);// 得到小时
        if (hour < 10)
            return "0" + hour;

        return String.valueOf(hour);
    }

    /**
     * 当前分钟
     *
     * @return
     */
    public static String minutesStr() {
        Calendar cal = Calendar.getInstance();// 使用日历类
        int minute = cal.get(Calendar.MINUTE);// 得到分钟

        if (minute < 10)
            return "0" + minute;

        return String.valueOf(minute);

    }

    /**
     * 当前秒
     *
     * @return
     */
    public static String secondStr() {
        Calendar cal = Calendar.getInstance();// 使用日历类
        int second = cal.get(Calendar.SECOND);// 得到秒
        if (second < 10)
            return "0" + second;

        return String.valueOf(second);
    }

    /**
     * 根据年-月算最第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String startMonthFullDateStr(int year, int month) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);

        String result = year + "-" + month;
        int begin = calendar.getActualMinimum(calendar.DAY_OF_MONTH);
        if (begin < 10) {
            result = result + "-0" + begin;
        } else {
            result = result + "-" + begin;
        }

        return result;
    }

    /**
     * 根据年-月算最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String endMonthFullDateStr(int year, int month) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);

        int end = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
        String result = year + "-" + month + "-" + end;

        return result;
    }

    public static java.sql.Timestamp string2Timestamp(String date) {
        java.sql.Date dDate = string2SQLDate(date);
        if (dDate != null) {
            return new java.sql.Timestamp(dDate.getTime());
        }
        return null;
    }

    public static java.sql.Date string2SQLDate(String date) {
        java.sql.Date ret = null;

        if (date == null || date.length() == 0) {
            return null;
        }
        if (date.length() > 11) {
            if (date.indexOf('-') > 0) {

                if (date.indexOf(':') > 0) {
                    try {
                        ret = string2SQLDate(date, DATETIME_FORMAT_1);
                    } catch (Exception e) {
                        try {
                            ret = string2SQLDate(date, DATETIME_FORMAT_31);
                        } catch (Exception e1) {
                            ret = string2SQLDate(date, DATETIME_FORMAT_32);
                        }
                    }
                } else {
                    ret = string2SQLDate(date, DATETIME_FORMAT_3);
                }
            } else if (date.indexOf('/') > 0) {
                if (date.trim().length() == 16) {
                    ret = string2SQLDate(date, DATETIME_FORMAT_5);
                } else {
                    ret = string2SQLDate(date, DATETIME_FORMAT_4);
                }
            } else {
                ret = string2SQLDate(date, DATETIME_FORMAT_2);
            }
        } else {
            if (date.indexOf('-') > 0) {
                if (date.length() == 7) {
                    ret = string2SQLDate(date, DATE_FORMAT_4);
                } else {
                    ret = string2SQLDate(date, DATE_FORMAT_1);
                }
            } else if (date.length() == 4) {
                ret = string2SQLDate(date, DATE_FORMAT_6);
            } else if (date.length() == 6) {
                ret = string2SQLDate(date, DATE_FORMAT_5);
            } else if (date.length() == 8) {
                ret = string2SQLDate(date, DATE_FORMAT_2);
            } else {
                ret = string2SQLDate(date, DATE_FORMAT_3);
            }
        }
        return ret;
    }

    public static SimpleDateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    public synchronized static java.sql.Date string2SQLDate(String date,
                                                            String format) {
        boolean isSucc = true;
        Exception operateException = null;
        if (!ValidateUtil.validateNotEmpty(format)) {
            isSucc = false;
            operateException = new IllegalArgumentException(
                    "the date format string is null!");
        }
        SimpleDateFormat sdf = getDateFormat(format);
        if (!ValidateUtil.validateNotNull(sdf)) {
            isSucc = false;
            operateException = new IllegalArgumentException(
                    "the date format string is not matching available format object");
        }
        Date tmpDate = null;
        try {
            if (isSucc) {
                tmpDate = sdf.parse(date);
                String tmpDateStr = sdf.format(tmpDate);
                if (!tmpDateStr.equals(date)) {
                    isSucc = false;
                    operateException = new IllegalArgumentException(
                            "the date string " + date
                                    + " is not matching format: " + format);
                }
            }
        } catch (Exception e) {
            isSucc = false;
            operateException = e;
        }

        if (!isSucc) {
            if (operateException instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) operateException;
            } else {
                throw new IllegalArgumentException("the date string " + date
                        + " is not matching format: " + format
                        + ".\n cause by :" + operateException.toString());
            }
        } else {
            return new java.sql.Date(tmpDate.getTime());
        }

    }

    /**
     * 获得当前0点
     *
     * @return
     */
    public static Date getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // 获得当天24点时间
    public static Date getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得本月初
     *
     * @return
     */
    public static Date getMonthBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    // 获得本月底
    public static Date getMonthEnd() {
        Calendar cal = Calendar.getInstance();
        int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastday);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static void main(String[] args) throws ParseException {

        String a = "2015-2-9 0:0:0";

        String b = DateUtil.date2String(new Date(), "yyyy-M-d H:m:s");
        System.out.println("b= " + DateUtil.getMonthBegin());
        System.out.println("c= " + DateUtil.getMonthEnd());

    }

}