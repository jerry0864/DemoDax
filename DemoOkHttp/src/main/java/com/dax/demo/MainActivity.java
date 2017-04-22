package com.dax.demo;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dax.demo.http.NetworkManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //NetworkManager.getInstance().setConnector(new VolleyConnector(getApplicationContext()));
    }

    public void connect(View view){
        NetworkManager.getInstance().request();
    }
}
