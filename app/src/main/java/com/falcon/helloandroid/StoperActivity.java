package com.falcon.helloandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class StoperActivity extends AppCompatActivity {

    private Date initialTime = new Date();
    private TextView tvStoper;
    private Button clearButton;
    private Button lapButton;
    private TextView lapList;
    private boolean isRunning;
    private boolean paused;
    private long timeOffset=0;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoper);

        tvStoper = (TextView) findViewById(R.id.stoperText);
        clearButton = (Button) findViewById(R.id.lapClearButton);
        lapList = (TextView) findViewById(R.id.lapView);
        lapButton = (Button) findViewById(R.id.lapButton);
        isRunning = false;
        paused = false;

    }

    public void onClickStart(View view){
        if(paused) {
            paused = false;
        }
        else{
            this.initialTime = Calendar.getInstance().getTime();
        }


        isRunning = true;

        clearButton.setText("Stop");

        if(!t.isAlive()){
            t.start();
        }


    }

    Thread t = new Thread() {
        @Override
        public void run(){
            try{
                while(!isInterrupted()){
                    Thread.sleep(10);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isRunning){
                                updateStoper();
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private void updateStoper(){
        long diff = Calendar.getInstance().getTime().getTime() - initialTime.getTime() + timeOffset;
        long milis = diff / 10 % 100;
        long seconds = diff / 1000 % 60;
        long minutes = diff / (60 * 1000) % 60;
        long hours = diff / (60 * 60 * 1000) % 60;

        result = (hours+":"+minutes+":"+seconds+":"+milis);
        tvStoper.setText(result);
    }

    public void onClickClear(View view){
        if(isRunning){
            isRunning = false;
            timeOffset = Calendar.getInstance().getTime().getTime() - this.initialTime.getTime() + timeOffset;
            clearButton.setText("Clear");
        }
        else{
            tvStoper.setText("00:00:00:00");
            clearLaps();
            timeOffset = 0;
        }
    }

    public void onClickLap(View view){
        addLap(result);
    }

    private void addLap(String lapTime){
        lapList.append(result+"\n");
    }

    private void clearLaps(){
        lapList.setText("");
    }



}
