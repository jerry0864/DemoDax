package com.dax.demo.swipelayout.Activity;

import android.os.Bundle;

import com.dax.demo.swipelayout.R;
import com.dax.demo.swipelayout.widget.BaseSwipeBackActivity;

public class NormalActivity extends BaseSwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
    }
}
