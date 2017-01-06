package com.kugou.sdk.share.platform.weixin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.kugou.sdk.share.R;
import com.kugou.sdk.share.base.IShareCallback;
import com.kugou.sdk.share.base.PlatformConfig;
import com.kugou.sdk.share.base.ShareParams;
import com.kugou.sdk.share.base.ShareType;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class WeiXinShareHandlerActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String appid = PlatformConfig.getInstance().getConfig(PlatformConfig.Key.WEIXIN_APPID).toString();
        api = WXAPIFactory.createWXAPI(this, appid, false);
        api.registerApp(appid);
        api.handleIntent(getIntent(), this);
        handleIntent();
        share();
    }

    private int mShareType;
    private ShareParams mInfo;
    private void handleIntent() {
        mShareType = getIntent().getIntExtra("shareType", 1);
        mInfo = getIntent().getParcelableExtra("info");
    }

    private void share(){
        if(mShareType == ShareType.WEB.ordinal()){//分享网页
            shareWeb();
        }else if(mShareType == ShareType.IMAGE.ordinal()){//分享本地图片
            shareImage();
        }else if(mShareType == ShareType.VEDIO.ordinal()){//分享视频
            shareVedio();
        }
    }

    private void shareVedio() {
        //TODO
    }

    private void shareImage() {
        if (mInfo == null) {
            finish();
            return;
        }
        String path = mInfo.imgLocalPath;
        File file = new File(path);
        if (!file.exists()) {
            finish();
            return;
        }

        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(path);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        byte[] result = fileToByte(file);
        msg.thumbData = result;
        if (msg.thumbData == null) {
            msg.thumbData = bmpToByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.icon_app), true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = mInfo.scene;
        api.sendReq(req);
        finish();
    }

    private void shareWeb() {
        if (mInfo == null) {
            finish();
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = mInfo.webUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = mInfo.title;
        msg.description = mInfo.desc;
        msg.thumbData = null;

        if (!TextUtils.isEmpty(mInfo.imgUrl)) {
//            File file = ImageLoader.getCachedImageOnDisk(Uri.parse(mInfo.imgUrl));
//            if (file != null) {
//                byte[]result = fileToByte(file);
//                msg.thumbData = result;
//            }
        }
        if (msg.thumbData == null) {
            msg.thumbData = bmpToByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.icon_app), true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = mInfo.scene;
        api.sendReq(req);
        finish();
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
                result = bmpToByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.icon_app), true);
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

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        //微信分享结果回调
        IShareCallback callBack = WeixinPlatform.sCallback;
        if (callBack != null) {
            int scene = mInfo.scene;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    callBack.onSuccess(scene);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    callBack.onCancel(scene);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    callBack.onFaile(scene,resp.errStr);
                    break;
                default:
                    callBack.onFaile(scene,resp.errStr);
                    break;
            }
        }
    }
}
