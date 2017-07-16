package com.casic.accessControl.util;

import java.util.List;

/**
 * Created by Administrator on 2015/4/17.
 */
public class DataTable<T> {
    private List<T> aaData;//数据
    private int iTotalDisplayRecords;//得到的记录数
    private int iTotalRecords;//数据库中记录数
    private int sEcho; //请求服务器端次数

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getsEcho() {
        return sEcho;
    }

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }
}
