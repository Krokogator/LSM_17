package com.falcon.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickRandomNumber(View view){
        TextView tv = (TextView) findViewById(R.id.textView);
        Random random = new Random();
        int result = random.nextInt(101);
        tv.setText("Hello Android  " + result);
    }

    @Override
    protected void onPause(){
        super.onPause();

        /*
        Intent msgIntent = new Intent(this, BackgroundService.class);
        startService(msgIntent);
        */
    }

    public void onClickClearNumber(View view){
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText("Hello Android");
    }

    public void onClickStoperActivity(View view){
        Intent i = new Intent(this, StoperActivity.class);
        startActivity(i);
    }
}
