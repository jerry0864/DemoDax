package com.dax.demo.alarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setAlarm(View view){
        AlarmHelper.setAlarm(this,10);
    }

    public void cancelAlarm(View view){
        AlarmHelper.cancelAlarm(this,10);
    }


}
