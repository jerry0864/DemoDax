package com.dax.demo.nagivator_slow_test;

import android.os.Bundle;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        delay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //delay();
    }
}
