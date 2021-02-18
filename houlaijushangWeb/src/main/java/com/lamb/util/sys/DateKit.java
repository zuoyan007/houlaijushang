package com.lamb.util.sys;

import com.jeesite.common.lang.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 对框架日期类扩展
 */
public class DateKit extends DateUtils {

    /**
     * 获取月最后一天
     * @param date
     * @param pattern
     * @return
     */
    public static Date getMonthLastDay(Date date){
        Date nextMonths = DateKit.addMonths(date,1);
        Date fDay = DateKit.setDays(nextMonths,1);
        fDay  = DateKit.addDays(fDay,-1);
        fDay  = DateKit.setHours(fDay,23);
        fDay  = DateKit.setMinutes(fDay,59);
        fDay  = DateKit.setSeconds(fDay,59);
        fDay  = DateKit.setMilliseconds(fDay,0);
        return fDay;
    }

    /**
     * 获取月最后一天
     * @param date
     * @param pattern
     * @return
     */
    public static String getMonthLastDayStr(Date date){
        return getMonthLastDayStr(date,"yyyy-MM-dd");
    }

    /**
     * 获取月最后一天
     * @param date
     * @param pattern
     * @return
     */
    public static String getMonthLastDayStr(Date date,String pattern){
        Date day = getMonthLastDay(date);
        return DateKit.formatDate(day,pattern);
    }

    /**
     * 获取月 第一天
     * @param date
     * @param pattern
     * @return
     */
    public static Date getMonthFirstDay(Date date){
        Date fDay = DateKit.setDays(date,1);
        fDay  = DateKit.setHours(fDay,0);
        fDay  = DateKit.setMinutes(fDay,0);
        fDay  = DateKit.setSeconds(fDay,0);
        fDay  = DateKit.setMilliseconds(fDay,0);
        return fDay;
    }

    /**
     * 获取月 第一天
     * @param date
     * @param pattern
     * @return
     */
    public static String getMonthFirstDayStr(Date date){
        return getMonthFirstDayStr(date,"yyyy-MM-dd");
    }

    /**
     * 获取月 第一天
     * @param date
     * @param pattern
     * @return
     */
    public static String getMonthFirstDayStr(Date date,String pattern){
        Date day = DateKit.getMonthFirstDay(date);
        return DateKit.formatDate(day,pattern);
    }
    /**
     * 根据小写数字格式的日期转换成大写格式的日期
     * @param date
     * @return
     */
    public static String dataToUpper(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return numToUpper(year) + "年" + monthToUppder(month) + "月" + dayToUppder(day) + "日";
    }

    // 将数字转化为大写
    public static String numToUpper(int num) {
        String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++) {
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }
        return rstr;
    }

    // 月转化为大写
    public static String monthToUppder(int month) {
        if(month < 10) {
            return numToUpper(month);
        } else if(month == 10){
            return "拾";
        } else {
            return "拾" + numToUpper(month - 10);
        }
    }

    // 日转化为大写
    public static String dayToUppder(int day) {
        if(day < 20) {
            return monthToUppder(day);
        } else {
            char[] str = String.valueOf(day).toCharArray();
            if(str[1] == '0') {
                return numToUpper(Integer.parseInt(str[0] + "")) + "拾";
            }else {
                return numToUpper(Integer.parseInt(str[0] + "")) + "拾" + numToUpper(Integer.parseInt(str[1] + ""));
            }
        }
    }


    public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_WEEK, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal.getTime();
    }

    /**
     * 获取日期对应的礼拜几。礼拜1就返回1，礼拜7就返回7
     * @param date
     * @return
     */
    public static int getWeekCHS(Date date){
        Calendar  cal = Calendar.getInstance();
        cal.setTime(date);
        int ww = cal.get(Calendar.DAY_OF_WEEK);
        ww = ww-1;
        return ww==0?7:ww;
    }

    /**
     * 获取日期对应的礼拜几。礼拜1就返回1，礼拜7就返回7
     * @return
     */
    public static int getWeekCHS(){
        return getWeekCHS(new Date());
    }

}
