package com.falcon.helloandroid;

import android.app.IntentService;
import android.content.Intent;
import android.os.FileObserver;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.sleep;

/**
 * Created by micha on 20.10.2017.
 */

public class BackgroundService extends IntentService {
    private boolean suAcceseed = false;
    List<String> string = new ArrayList<>();
    List<String> string2 = new ArrayList<>();

    public BackgroundService(){
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        createObserver("/sdcard/");


        //string.add("screencap -p /sdcard/screen.png");
        string.add("sendevent /dev/input/event1 3 57 14");
        string.add("sendevent /dev/input/event1 1 330 1");
        string.add("sendevent /dev/input/event1 1 325 1");
        string.add("sendevent /dev/input/event1 3 53 300");
        string.add("sendevent /dev/input/event1 3 54 300");
        string.add("sendevent /dev/input/event1 0 0 0");
        string.add("sendevent /dev/input/event1 3 53 400");
        string.add("sendevent /dev/input/event1 3 54 400 ; sendevent /dev/input/event1 0 0 0 ; sendevent /dev/input/event1 3 53 500 ; sendevent /dev/input/event1 3 54 500 ; sendevent /dev/input/event1 0 0 0 ; sendevent /dev/input/event1 3 53 600 ; sendevent /dev/input/event1 3 54 600 ; sendevent /dev/input/event1 0 0 0 ; sendevent /dev/input/event1 3 53 700 ; sendevent /dev/input/event1 3 54 700 ; sendevent /dev/input/event1 0 0 0 ; sendevent /dev/input/event1 3 53 800 ; sendevent /dev/input/event1 3 54 800");
        string.add("sendevent /dev/input/event1 0 0 0");
        string.add("sendevent /dev/input/event1 3 57 4294967295");
        string.add("sendevent /dev/input/event1 1 330 0");
        string.add("sendevent /dev/input/event1 1 325 0");
        string.add("sendevent /dev/input/event1 0 0 0");

        string2.add(touch()+" && "+move(300,1000)+" && "+move(600,1000)+" && "+move(900,1000)+" && "+move(1300,1000)+" && "+move(1300,1300)+" && "+move(300,1000)+" && "+move(300,500)+" && "+release());
        string2.add(touch()+" && "+move(300,1000)+" && "+move(600,1000)+" && "+move(900,1000)+" && "+move(1300,1000)+" && "+move(1300,1300)+" && "+move(300,1000)+" && "+move(300,500)+" && "+release());
        string2.add(touch()+" && "+move(300,1000)+" && "+move(600,1000)+" && "+move(900,1000)+" && "+move(1300,1000)+" && "+move(1300,1300)+" && "+move(300,1000)+" && "+move(300,500)+" && "+release());
        string2.add(touch()+" && "+move(300,1000)+" && "+move(600,1000)+" && "+move(900,1000)+" && "+move(1300,1000)+" && "+move(1300,1300)+" && "+move(300,1000)+" && "+move(300,500)+" && "+release());
        string2.add(touch()+" && "+move(300,1000)+" && "+move(600,1000)+" && "+move(900,1000)+" && "+move(1300,1000)+" && "+move(1300,1300)+" && "+move(300,1000)+" && "+move(300,500)+" && "+release());
        sudo(string2);
    }

    private String sendevent = "sendevent /dev/input/event1";

    private String touch(){
        return  sendevent+" 3 57 14 &&"+
                sendevent+" 1 330 1 &&"+
                sendevent+" 1 325 1";
    }
    private String move(int x, int y){
        return  sendevent+" 3 53 "+x+" &&"+
                sendevent+" 3 54 "+y+" &&"+
                sendevent+" 0 0 0";
    }
    private String release(){
        return  sendevent+" 3 57 4294967295 &&"+
                sendevent+" 1 330 0 &&"+
                sendevent+" 1 325 0 &&"+
                sendevent+" 0 0 0";
    }


    public void sudo(List<String> strings) {
        try{
            Process su;
            su = Runtime.getRuntime().exec("su");

            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            for (String s : strings) {
                outputStream.writeBytes(s+"\n");
                outputStream.flush();
            }

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            try {
                su.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void createObserver(String pathToWatch){
        FileObserver observer = new FileObserver(pathToWatch) { // set up a file observer to watch this directory on sd card

            @Override
            public void onEvent(int event, String path) {
                //if(event == FileObserver.CREATE && !file.equals(".probe")){ // check if its a "create" and not equal to .probe because thats created every time camera is launched
                Log.d(TAG, "File created [" + path + "]");

                Toast.makeText(getBaseContext(), path + " was saved!", Toast.LENGTH_LONG).show();
                sudo(string);
                //}
            }
        };
        observer.startWatching(); //START OBSERVING
    }
}
