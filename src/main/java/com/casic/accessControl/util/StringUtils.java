package com.casic.accessControl.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/7.
 */
public class StringUtils extends com.casic.accessControl.core.util.StringUtils{

    public static final String SYS_USER = "_current_user";
    public static final String LOG_SYS = "系统日志";
    public static final String LOG_RUN = "运行日志";
    public static final String LOG_ERROR = "错误日志";
    public static final String LOG_ALARM = "报警日志";

    public static List<Long> ConvertToLongList(String[] array)
    {
        List<Long> list = new ArrayList<Long>();
        for(String str : array)
        {
            list.add(Long.parseLong(str));
        }
        return list;
    }
}
