package com.dax.demo.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickbtn(View view){
        Intent intent = new Intent("com.netease.push.action.client.METHOD");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
        int flag = getIntent().getFlags();
        Log.d("dax_test","onCreate--1-flag-->"+getIntent().getFlags());
        if(flag == 268435456){
            //10000000-268435456-FLAG_ACTIVITY_NEW_TASK
            //10200000-270532608-Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            //10600000--274726912-Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED|Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
            //10100000-269484032-Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY
            //getIntent().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            getIntent().setFlags(270532608);
        }
        Log.d("dax_test","onCreate--2-flag-->"+getIntent().getFlags());
        Set<String> set = getIntent().getCategories();
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()){
            String cat = iterator.next();
            Log.d("dax_test","onCreate--2-getCategories--->"+cat);
        }
    }
}
