package com.casic.accessControl.feature;

/**
 * Created by lenovo on 2016/9/1.
 */
public class Target {
    public static void main(String[] args){
        Adaptee adaptee = new Adaptee();
        Adapter adapter = new Adapter(adaptee);
        adapter.request();
    }
}
