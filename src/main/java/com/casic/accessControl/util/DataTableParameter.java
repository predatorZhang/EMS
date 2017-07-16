package com.casic.accessControl.util;

/**
 * Created by thc on 2015/4/17.
 */
public class DataTableParameter {
    private int iDisplayStart;//起始记录，第一条为0
    private int iDisplayLength;
    private String sSearch;
    private int sEcho; //请求服务器端次数

    public DataTableParameter(int iDisplayStart, int iDisplayLength, int sEcho, String sSearch) {
        this.iDisplayStart = iDisplayStart;
        this.iDisplayLength = iDisplayLength;
        this.sEcho = sEcho;
        this.sSearch = sSearch;
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public String getsSearch() {
        return sSearch;
    }

    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    }

    public int getsEcho() {
        return sEcho;
    }

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }
}
