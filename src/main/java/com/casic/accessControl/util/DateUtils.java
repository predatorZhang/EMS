package com.casic.accessControl.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/4/16.
 */
public class DateUtils
{
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sdfFile = new SimpleDateFormat("yyyyMMddHHmmss");

    public static Calendar getCalendar(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static int getFistDayOfMonth(Calendar calendar)
    {
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return  calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getLastDayOfMonth(Calendar calendar)
    {
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.get(Calendar.DAY_OF_MONTH)+1;
    }

    public static int getYear(Calendar calendar)
    {
        return  calendar.get(Calendar.YEAR);
    }

    public static  int getMonth(Calendar calendar)
    {
        return  calendar.get(Calendar.MONTH);
    }
}
