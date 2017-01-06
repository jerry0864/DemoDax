package com.kugou.sdk.share.core;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.kugou.sdk.share.base.IPlatform;
import com.kugou.sdk.share.base.IShareCallback;
import com.kugou.sdk.share.base.Platform;
import com.kugou.sdk.share.base.ShareParams;
import com.kugou.sdk.share.base.ShareType;

import java.util.HashMap;


/**
 * 分享平台管理类
 *@author liuxiong
 *@since 2017/1/4 16:53
 */
public class KGShare {
    private static HashMap<String,String> sPlatformClassPathMap = new HashMap<String,String>();
    static{
        sPlatformClassPathMap.put(Platform.WEIXIN,"com.kugou.sdk.share.platform.weixin.WeixinPlatform");
        sPlatformClassPathMap.put(Platform.QQ,"com.kugou.sdk.share.platform.qq.QQPlatform");
        sPlatformClassPathMap.put(Platform.WEIBO,"com.kugou.sdk.share.platform.weibo.WeiboPlatform");
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
        IPlatform platform = findPlatform(activity,platformname);
        if(platform==null){
            Log.e("KGShare","------------->"+platformname+" module does not exist.");
            return;
        }
        platform.share(shareType,params,callback);
    }

    /**
     * 动态增加分享平台
     * @param platformname
     * @param platformClazz
     */
    public static void addPlatform(String platformname,Class platformClazz){
        boolean isChild = IPlatform.class.isAssignableFrom(platformClazz);
        if(!isChild){
            Log.e("KGShare","new platform must implements IPlatform interface.");
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


    private static IPlatform findPlatform(Activity activity, String name){
        IPlatform platform = null;
        try {
            platform = (IPlatform) Class.forName(sPlatformClassPathMap.get(name)).newInstance();
            if(platform!=null){
                platform.init(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return platform;
    }

    /**
     * QQ和Qzone分享需要实现onActivityResult回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == Constants.REQUEST_QQ_SHARE) {
//            Tencent.onActivityResultData(requestCode, resultCode, data, new ShareUIListener(ShareHelper.IShareCallBack.SCENE_QQ_FRIEND));
//        } else if (requestCode == Constants.REQUEST_QZONE_SHARE) {
//            Tencent.onActivityResultData(requestCode, resultCode, data, new ShareHelper.ShareUIListener(ShareHelper.IShareCallBack.SCENE_QQ_ZONE));
//        }

    }
}
