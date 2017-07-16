package com.casic.accessControl.feature;

/**
 * Created by lenovo on 2016/9/1.
 */
public class Adaptee implements AdapteeImp{
    public void request(){
        System.out.println("adaptee request");
    }
}
