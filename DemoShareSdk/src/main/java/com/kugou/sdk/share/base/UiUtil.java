package com.kugou.sdk.share.base;

import android.os.Handler;
import android.os.Looper;

/**
 * UI工具类
 *@author liuxiong
 *@since 2017/1/5 16:26
 */
public class UiUtil {

    private static final Handler mHandler = new Handler(Looper.getMainLooper());
    public static void post(Runnable runnable){
        mHandler.post(runnable);
    }
}
