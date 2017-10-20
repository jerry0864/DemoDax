package com.dax.demo.circleview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    private static final String TAG = "dax_test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CircleProgressView circleProgressView = (CircleProgressView) findViewById(R.id.circleview);
        circleProgressView.setProgress(0.35f);
    }
}
