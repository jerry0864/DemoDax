package com.dax.lib.common;


import android.content.Context;

public class AppLib {
    static Context mContext;
    public static void init(Context context){
        mContext = context.getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
