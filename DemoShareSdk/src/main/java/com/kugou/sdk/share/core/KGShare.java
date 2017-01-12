package com.kugou.sdk.share.core;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;


/**
 * 分享平台管理类
 *@author liuxiong
 *@since 2017/1/4 16:53
 */
public class KGShare {
    private static final String TAG = "KGShare";
    private static HashMap<String,String> sPlatformClassPathMap = new HashMap<String,String>();
    static{
        sPlatformClassPathMap.put(Platform.Name.WEIXIN,"com.kugou.sdk.share.platform.weixin.WeixinPlatform");
        sPlatformClassPathMap.put(Platform.Name.QQ,"com.kugou.sdk.share.platform.qq.QQPlatform");
        sPlatformClassPathMap.put(Platform.Name.WEIBO,"com.kugou.sdk.share.platform.weibo.WeiboPlatform");
    }

    /**
     * 调用分享
     * @param activity
     * @param platformname 第三方平台
     * @param shareType 分享类型
     * @param params 分享参数
     * @param callback 分享回调
     */
    public static void share(Activity activity, String platformname, ShareType shareType, ShareParams params, IShareCallback callback){
        PlatformHandler platform = findPlatform(activity,platformname);
        if(platform==null){
            Log.e(TAG,"------------->"+platformname+" module does not exist.");
            return;
        }
        //Log.e(TAG,"share:--->"+params.toString());
        platform.share(shareType,params,callback);
    }

    /**
     * 动态增加分享平台
     * @param platformname
     * @param platformClazz
     */
    public static void addPlatform(String platformname,Class platformClazz){
        boolean isChild = PlatformHandler.class.isAssignableFrom(platformClazz);
        if(!isChild){
            Log.e(TAG,"new platform must implements PlatformHandler interface.");
            return;
        }
        try {
            Object obj = platformClazz.newInstance();
            if(obj!=null){
                sPlatformClassPathMap.put(platformname,platformClazz.getName());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private static PlatformHandler findPlatform(Activity activity, String name){
        PlatformHandler platform = null;
        try {
            Class clazz =  Class.forName(sPlatformClassPathMap.get(name));
            platform = (PlatformHandler)clazz.newInstance();
            if(platform!=null && activity!=null){
                platform.init(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return platform;
    }

    /**
     * 在分享的activity里接收平台回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        PlatformHandler platform = findPlatform(null,Platform.Name.QQ);
        if(platform!=null){
            platform.onActivityResult(requestCode,resultCode,data);
        }
    }
}
