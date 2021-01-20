package com.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eric on 2015-10-21.
 */
public class DateUtils {

    public static Date getNowDate() {
        return new Date();
    }

    public static Date getDateFormat(String date,String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date dd = null;
        try {
            dd = formatter.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return dd;
    }

    /**
     * 返回yyyy-MM-dd HH:mm:ss 格式当前时间
     * @return
     */
    public static String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Now = new Date();
        String NDate = formatter.format(Now);
        return NDate;
    }

    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date Now = new Date();
        String NDate = formatter.format(Now);
        return NDate;
    }

    public static String getDateStr(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        String NDate = formatter.format(date);
        return NDate;
    }

    public static String getDateNumbers() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date Now = new Date();
        String NDate = formatter.format(Now);
        return NDate;
    }

    public static String getIndexDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date Now = new Date();
        String NDate = formatter.format(Now);
        return NDate;
    }

    public static long getSystemNow(){
        Date now = new Date();
        return now.getTime();
    }


    /**
     * 生成短信、邮件发送批次号
     * @return
     */
    public static String getBatchNo() {
        int temp = (int)((Math.random()*9+1)*1000);
        String batchNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return batchNo+"-"+temp;
    }

    /**
     * 比较报名时间
     */
    public static boolean compareDate(String startDate,String endDate){
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String startDay = startDate.substring(0, 10).replace("-","");
        String endDay = endDate.substring(0, 10).replace("-", "");
        if(Integer.valueOf(dateStr)<Integer.valueOf(startDay) || Integer.valueOf(dateStr)>Integer.valueOf(endDay))return false;
        String hour = new SimpleDateFormat("HH").format(new Date());
        String startHour = startDate.substring(11, 13);
        String endHour = endDate.substring(11, 13);
        return !(Integer.valueOf(hour) < Integer.valueOf(startHour) || Integer.valueOf(hour) >= Integer.valueOf(endHour));
    }

    /**
     * 日期加减
     * @param date
     * @param hour
     * @return
     */
    public static String getOprDateStr(Date date,int hour){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期
        calendar.add(Calendar.HOUR_OF_DAY, hour);//加、减
        return format.format(calendar.getTime());
    }

    /**
     * 获取当前周一日期
     * @return
     */
    public static String getMondayStr(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar cld = Calendar.getInstance(Locale.CHINA);
        cld.setFirstDayOfWeek(Calendar.MONDAY);//以周一为首日
        cld.setTimeInMillis(System.currentTimeMillis());//当前时间

        cld.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//周一
        return df.format(cld.getTime())+" 00:00:00";
    }

    /**
     * 获取最近几月的日期
     * @return
     */
    public static String getMonthStr(int mouth){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, mouth);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }

    /**
     * 获取一周前日期
     * @return
     */
    public static String getWeekDayStr(int day){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, day);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }


    public static void main(String ages[]) {
        System.out.print(DateUtils.getWeekDayStr(-2));
    }
}
