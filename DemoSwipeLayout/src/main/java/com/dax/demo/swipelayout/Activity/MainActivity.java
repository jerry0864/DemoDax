package com.dax.demo.swipelayout.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.dax.demo.swipelayout.R;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void swipeNormal(View view){
        startActivity(new Intent(this,NormalActivity.class));

    }
    public void swipeListView(View view){
        startActivity(new Intent(this,SwipeListViewActivity.class));
    }
    public void swipeViewPager(View view){
        startActivity(new Intent(this,SwipeViewPagerActivity.class));
    }
}