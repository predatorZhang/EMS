package com.casic.accessControl.feature;

/**
 * Created by lenovo on 2016/9/1.
 */
public class Adapter {

    private AdapteeImp adaptee;

    public Adapter(){}
    public Adapter(AdapteeImp adaptee){
        this.adaptee = adaptee;
    }
    public void request(){
        adaptee.request();
    }
}
