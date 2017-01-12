package com.kugou.sdk.share.platform.qq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kugou.sdk.share.base.UiUtil;
import com.kugou.sdk.share.core.PlatformHandler;
import com.kugou.sdk.share.core.IShareCallback;
import com.kugou.sdk.share.core.Platform;
import com.kugou.sdk.share.core.PlatformConfig;
import com.kugou.sdk.share.core.Scene;
import com.kugou.sdk.share.core.ShareParams;
import com.kugou.sdk.share.core.ShareType;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * QQ分享平台
 *@author liuxiong
 *@since 2017/1/5 14:33
 */
public class QQPlatform extends PlatformHandler {
    private final String TAG = getClass().getSimpleName();
    Tencent mTencent;
    WeakReference<Activity> mReference;
    ShareParams mShareInfo;
    @Override
    public void init(Activity activity) {
        mReference = new WeakReference<Activity>(activity);
        //创建qq分享实例
        try {
            String appid = PlatformConfig.getInstance().getConfig(Platform.Key.QQ_APPID).toString();
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
        this.sCallback = callback;
        this.mShareInfo = params;
        int scene = params.scene;

        if(scene == Scene.QQ.FRIEND){
            shareToQQ(shareType);
        }else if(scene == Scene.QQ.ZONE){
            shareToQZone(shareType);
        }
    }

    private void shareToQZone(ShareType shareType) {
        switch(shareType){
            case IMAGE:
                shareImageToQZone();
                break;
            case WEB:
                shareWebToQZone();
                break;
            case VEDIO:
                shareVedioToQZone();
                break;
        }
    }

    private void shareToQQ(ShareType shareType) {
        switch(shareType){
            case IMAGE:
                shareImageToQQ();
                break;
            case WEB:
                shareWebToQQ();
                break;
            case VEDIO:
                shareVedioToQQ();
                break;
        }
    }

    /**
     * 分享网页到QQ好友
     */
    private void shareWebToQQ(){
        if(checkNull()){
            return;
        }

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, mShareInfo.title);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mShareInfo.webUrl);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, mShareInfo.desc);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mShareInfo.imgUrl);
        UiUtil.post(new Runnable() {
            @Override
            public void run() {
                mTencent.shareToQQ(getActivity(), params, new ShareUIListener(Scene.QQ.FRIEND));
            }
        });
    }

    /**
     * 分享图片到QQ好友
     */
    private void shareImageToQQ(){
        if(checkNull()){
            return;
        }
        Log.d(TAG,mShareInfo.toString());
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mShareInfo.imgUrl);
        UiUtil.post(new Runnable() {
            @Override
            public void run() {
                mTencent.shareToQQ(getActivity(), params, new ShareUIListener(Scene.QQ.FRIEND));
            }
        });
    }

    private void shareVedioToQQ(){
        //QQ好友不支持视频分享
        if(getActivity()!=null){
            Toast.makeText(getActivity(),"QQ好友不支持视频分享",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 分享网页到QZone
     */
    private void shareWebToQZone(){
        if(checkNull()){
            return;
        }
        final Bundle params = new Bundle();
        ArrayList<String> imageList = new ArrayList<>();
        imageList.add(mShareInfo.imgUrl);
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageList);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, mShareInfo.title);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, mShareInfo.webUrl);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, mShareInfo.desc);
        UiUtil.post(new Runnable() {
            @Override
            public void run() {
                mTencent.shareToQzone(getActivity(), params, new ShareUIListener(Scene.QQ.ZONE));
            }
        });
    }

    /**
     * 分享图片到QZone
     */
    private void shareImageToQZone(){
        if(checkNull()){
            return;
        }
        final Bundle params = new Bundle();
        ArrayList<String> imageList = new ArrayList<>();
        imageList.add(mShareInfo.imgUrl);
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "desc");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageList);

        UiUtil.post(new Runnable() {
            @Override
            public void run() {
                mTencent.publishToQzone(getActivity(), params, new ShareUIListener(Scene.QQ.ZONE));
            }
        });
    }

    private void shareVedioToQZone(){
        if(checkNull()){
            return;
        }
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, mShareInfo.desc);
        params.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, mShareInfo.vedioLocalPath);

        UiUtil.post(new Runnable() {
            @Override
            public void run() {
                mTencent.publishToQzone(getActivity(), params, new ShareUIListener(Scene.QQ.ZONE));
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, new ShareUIListener(Scene.QQ.FRIEND));
        } else if (requestCode == Constants.REQUEST_QZONE_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, new ShareUIListener(Scene.QQ.ZONE));
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

    public class ShareUIListener implements IUiListener {
        private int type;
        public ShareUIListener(int type){
            this.type = type;
        }
        @Override
        public void onComplete(Object obj) {
            if(sCallback !=null){
                sCallback.onSuccess(type);
            }
        }

        @Override
        public void onError(UiError uiError) {
            if(sCallback !=null){
                sCallback.onFaile(type,uiError.errorMessage);
            }
        }

        @Override
        public void onCancel() {
            if(sCallback !=null){
                sCallback.onCancel(type);
            }
        }
    }

}
