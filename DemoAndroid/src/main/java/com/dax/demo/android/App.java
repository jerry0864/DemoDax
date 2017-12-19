package com.dax.demo.android;

import android.app.Application;

import com.dax.lib.common.AppLib;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppLib.init(this);
    }
}
