package com.dax.demo.security;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Log;

/**
 * Desc:
 * Created by liuxiong on 2017/9/28.
 */
public class Jni {
    static {
        System.loadLibrary("security");
    }

    public static native String stringFromJNI(String source);

    public static String getPackageName(Context context){
        return context.getPackageName();
    }

    public static String getSignature(Context context) {
        try {
            String packagename = context.getPackageName();
            PackageInfo pi = context.getPackageManager().getPackageInfo(packagename,
                    PackageManager.GET_SIGNATURES);
            Signature[] asignature = pi.signatures;
            int j = asignature.length;
            for (int i = 0; i < j; i++) {
                Signature signature = asignature[i];
                String s = MD5Util.getMd5(signature.toByteArray());
                if (!TextUtils.isEmpty(s)) {
                    return s;
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public String test(){
        Log.d("dax_test","log call from c");
        return "hello from java";
    }
}
