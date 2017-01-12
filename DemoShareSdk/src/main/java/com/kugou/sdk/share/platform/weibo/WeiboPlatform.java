package com.kugou.sdk.share.platform.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.kugou.sdk.share.R;
import com.kugou.sdk.share.core.IShareCallback;
import com.kugou.sdk.share.core.Platform;
import com.kugou.sdk.share.core.PlatformConfig;
import com.kugou.sdk.share.core.PlatformHandler;
import com.kugou.sdk.share.core.ShareParams;
import com.kugou.sdk.share.core.ShareType;
import com.sina.weibo.sdk.api.BaseMediaObject;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 微博分享平台
 *@author liuxiong
 *@since 2017/1/5 15:12
 */
public class WeiboPlatform extends PlatformHandler implements IWeiboHandler.Response {
    /** 微博微博分享接口实例 */
    private IWeiboShareAPI mWeiboShareAPI = null;
    private Context mContext = null;
    private WeakReference<Activity> mReference;
    private static IShareCallback sCallback;
    private ShareParams mShareInfo;
    @Override
    public void init(Activity activity) {
        mReference = new WeakReference<Activity>(activity);
        String appkey = PlatformConfig.getInstance().getConfig(Platform.Key.WEIBO_APPKEY).toString();
        if(activity !=null){
            mContext = activity.getApplicationContext();
            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mContext, appkey);
            mWeiboShareAPI.registerApp();
        }
    }

    @Override
    public void share(ShareType shareType, ShareParams params, IShareCallback callback) {
        sCallback = callback;
        this.mShareInfo = params;

        switch(shareType){
            case IMAGE:
                sendSingleMessage(getImageObj());
                break;
            case WEB:
                sendSingleMessage(getWebObj());
                break;
            case VEDIO:
                sendSingleMessage(getVedioObj());
                break;
        }
    }

    private BaseMediaObject getVedioObj() {
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = mShareInfo.title;
        videoObject.description = mShareInfo.desc;
        Bitmap  bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_app);
        // 设置 Bitmap 类型的图片到视频对象里  设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        videoObject.setThumbImage(bitmap);
        videoObject.actionUrl = mShareInfo.vedioUrl;
        videoObject.dataUrl = mShareInfo.vedioUrl;
        videoObject.dataHdUrl = mShareInfo.vedioUrl;
        videoObject.duration = 10;
        videoObject.defaultText = "Vedio 默认文案";
        return videoObject;
    }

    private BaseMediaObject getWebObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = mShareInfo.title;
        mediaObject.description = mShareInfo.desc;

        Bitmap  bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_app);
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = mShareInfo.webUrl;
        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }

    private BaseMediaObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_app);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    private void sendSingleMessage(BaseMediaObject object) {
        WeiboMessage weiboMessage = new WeiboMessage();
        weiboMessage.mediaObject = object;
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
        mWeiboShareAPI.sendRequest(getActivity(), request);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        if(baseResp!= null){
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    if(sCallback !=null){
                        sCallback.onSuccess(0);
                    }
                    Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    if(sCallback !=null){
                        sCallback.onCancel(0);
                    }
                    Toast.makeText(mContext, "取消分享", Toast.LENGTH_SHORT).show();
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    if(sCallback !=null){
                        sCallback.onFaile(0,null);
                    }
                    Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private Activity getActivity(){
        if(mReference!=null){
            return mReference.get();
        }
        return null;
    }
}
