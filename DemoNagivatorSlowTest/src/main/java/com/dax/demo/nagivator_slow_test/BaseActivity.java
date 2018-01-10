package com.dax.demo.nagivator_slow_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "dax_test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,getClass().getSimpleName()+"---onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,getClass().getSimpleName()+"---onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,getClass().getSimpleName()+"---onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,getClass().getSimpleName()+"---onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,getClass().getSimpleName()+"---onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,getClass().getSimpleName()+"---onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,getClass().getSimpleName()+"---onDestroy");
    }

    protected void delay(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
