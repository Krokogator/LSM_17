package com.falcon.helloandroid.TimeMeasure;

/**
 * Created by micha on 31.07.2017.
 */
public class Stopper {
    private long start;

    public Stopper(){
        start = 0;
    }

    public void start(){
        start = System.currentTimeMillis();
    }

    public long getMilis(){
        return System.currentTimeMillis() - start;
    }

    public void reset(){
        start = System.currentTimeMillis();
    }
}
