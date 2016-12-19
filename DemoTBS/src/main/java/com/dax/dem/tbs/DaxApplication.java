package com.dax.dem.tbs;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by jerryliu on 2016/11/17.
 */

public class DaxApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化tbs
        QbSdk.initX5Environment(this,null);
    }
}
