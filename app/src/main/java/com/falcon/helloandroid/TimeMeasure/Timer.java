package com.falcon.helloandroid.TimeMeasure;

/**
 * Created by micha on 31.07.2017.
 */
public class Timer {
    private long start;
    private long needToElapse;

    public Timer(){}

    public Timer(int seconds){
        start = System.currentTimeMillis();
        needToElapse = seconds * 1000;
    }

    public boolean isElapsed(){
        if(System.currentTimeMillis() - start >= needToElapse){
            return true;
        }
        return false;
    }

    public void reset(){
        start = System.currentTimeMillis();
    }

    public void set(int seconds){
        needToElapse = seconds * 1000;
    }
}
