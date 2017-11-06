package com.netease.xyqcbg.jni;

/**
 * Desc:
 * Created by liuxiong on 2017/11/6.
 * Email:liuxiong@corp.netease.com
 */

public class JNI {
    static {
        System.loadLibrary("native-lib");
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public static native String stringFromJNI();
}
