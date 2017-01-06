package com.kugou.sdk.share.platform.qq;

import android.app.Activity;
import android.util.Log;

import com.kugou.sdk.share.base.IPlatform;
import com.kugou.sdk.share.base.IShareCallback;
import com.kugou.sdk.share.base.PlatformConfig;
import com.kugou.sdk.share.base.Scene;
import com.kugou.sdk.share.base.ShareParams;
import com.kugou.sdk.share.base.ShareType;
import com.tencent.tauth.Tencent;

import java.lang.ref.WeakReference;

/**
 * QQ分享平台
 *@author liuxiong
 *@since 2017/1/5 14:33
 */
public class QQPlatform implements IPlatform {
    private final String TAG = getClass().getSimpleName();
    Tencent mTencent;
    WeakReference<Activity> mReference;
    @Override
    public void init(Activity activity) {
        mReference = new WeakReference<Activity>(activity);
        //创建qq分享实例
        try {
            String appid = PlatformConfig.getInstance().getConfig(PlatformConfig.Key.QQ_APPID).toString();
            mTencent = Tencent.createInstance(appid, activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static IShareCallback sCallback;
    @Override
    public void share(ShareType shareType, ShareParams params, IShareCallback callback) {
        if(checkNull()){
            return;
        }
        sCallback = callback;
        int scene = params.scene;
        if(scene == Scene.QQ.QQ_FRIEND){
            shareToQQ(shareType,params);
        }else if(scene == Scene.QQ.QQ_ZONE){
            shareToQZone(shareType,params);
        }
    }

    private void shareToQZone(ShareType shareType, ShareParams params) {
        switch(shareType){
            case IMAGE:
                break;
            case WEB:
                break;
            case VEDIO:
                break;
        }
    }

    private void shareToQQ(ShareType shareType, ShareParams params) {
        switch(shareType){
            case IMAGE:
                break;
            case WEB:
                break;
            case VEDIO:
                break;
        }
    }


    private Activity getActivity(){
        if(mReference!=null){
            return mReference.get();
        }
        return null;
    }

    private boolean checkNull(){
        if(getActivity() == null|| getActivity().isFinishing()){
            Log.d(TAG,"分享页面已关闭");
            return true;
        }

        if(mTencent == null){
            Log.d(TAG,"tencent实例为空");
            return true;
        }
        return false;
    }
}
