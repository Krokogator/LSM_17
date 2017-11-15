package com.falcon.helloandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.falcon.helloandroid.Weights.WeightActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if(grant != PackageManager.PERMISSION_GRANTED){
            String [] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.help: {
                openBrowser();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void openBrowser(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"));
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
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

    public void testToast(View view){
        Toast toast = Toast.makeText(this, "Tościk!", Toast.LENGTH_LONG);
        toast.show();
    }

    public void onClickButtonWeight(View view){
        Intent i = new Intent(this, WeightActivity.class);
        startActivity(i);
    }
}
