package com.kugou.sdk.share.platform.weixin;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kugou.sdk.share.R;
import com.kugou.sdk.share.base.ImageUtil;
import com.kugou.sdk.share.core.IShareCallback;
import com.kugou.sdk.share.core.Platform;
import com.kugou.sdk.share.core.PlatformConfig;
import com.kugou.sdk.share.core.PlatformHandler;
import com.kugou.sdk.share.core.ShareParams;
import com.kugou.sdk.share.core.ShareType;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 微信平台
 *@author liuxiong
 *@since 2017/1/4 16:02
 */
public class WeixinPlatform extends PlatformHandler {
    private WeakReference<Activity> mReference;
    private IWXAPI mIWXAPI;
    private Context mContext;
    public static IShareCallback sCallback;
    public static int sShareType;
    public static ShareParams sInfo;
    @Override
    public void init(Activity activity) {
        mReference = new WeakReference<Activity>(activity);
        String appid = PlatformConfig.getInstance().getConfig(Platform.Key.WEIXIN_APPID).toString();
        mContext = activity.getApplicationContext();
        mIWXAPI = WXAPIFactory.createWXAPI(mContext, appid, false);
        mIWXAPI.registerApp(appid);
    }

    @Override
    public void share(ShareType shareType, ShareParams params, IShareCallback callback) {
        if(getActivity()==null){
            return;
        }
        //预加载分享图片
        ImageUtil.preloadImage(params.imgUrl);

        sCallback = callback;
        sShareType = shareType.ordinal();
        sInfo = params;

        deliverShare();
    }

    private void deliverShare(){
        if(sShareType == ShareType.WEB.ordinal()){//分享网页
            shareWeb();
        }else if(sShareType == ShareType.IMAGE.ordinal()){//分享本地图片
            shareImage();
        }else if(sShareType == ShareType.VEDIO.ordinal()){//分享视频
            shareVedio();
        }
    }

    private void shareImage() {
        if (sInfo == null) {
            return;
        }
        String path = sInfo.imgLocalPath;
        File file = new File(path);
        if (!file.exists()) {
            return;
        }

        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(path);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        byte[] result = fileToByte(file);
        msg.thumbData = result;
        if (msg.thumbData == null) {
            msg.thumbData = bmpToByteArray(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_app), true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = sInfo.scene;
        mIWXAPI.sendReq(req);
    }

    private void shareWeb() {
        if (sInfo == null) {
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = sInfo.webUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = sInfo.title;
        msg.description = sInfo.desc;
        msg.thumbData = null;

        File file = ImageUtil.loadImage(sInfo.imgUrl);
        if (file != null) {
            byte[]result = fileToByte(file);
            msg.thumbData = result;
        }
        if (msg.thumbData == null) {
            msg.thumbData = bmpToByteArray(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_app), true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = sInfo.scene;
        mIWXAPI.sendReq(req);
    }

    private void shareVedio() {
        if (sInfo == null) {
            return;
        }

        WXVideoObject video = new WXVideoObject();
        video.videoUrl = sInfo.vedioUrl;
        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = sInfo.title;
        msg.description = sInfo.desc;

        File file = ImageUtil.loadImage(sInfo.imgUrl);
        if (file != null) {
            byte[]result = fileToByte(file);
            msg.thumbData = result;
        }
        if (msg.thumbData == null) {
            msg.thumbData = bmpToByteArray(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_app), true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = sInfo.scene;
        mIWXAPI.sendReq(req);
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    //图片大小不能超过32k
    int IMAGE_SIZE_LIMIT = 32 * 1000;
    private byte[] fileToByte(File file){
        Bitmap thumb = BitmapFactory.decodeFile(file.getPath());
        if (thumb != null) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            thumb.compress(Bitmap.CompressFormat.JPEG, 100, output);
            if (output.toByteArray().length > IMAGE_SIZE_LIMIT) {
                output.reset();
                thumb.compress(Bitmap.CompressFormat.JPEG, 10, output);
            }
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            thumb.recycle();

            byte[] result = output.toByteArray();
            if (result.length > IMAGE_SIZE_LIMIT) {
                result = bmpToByteArray(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_app), true);
                return result;
            }
        }
        return null;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Activity getActivity(){
        if(mReference!=null){
            return mReference.get();
        }
        return null;
    }
}
