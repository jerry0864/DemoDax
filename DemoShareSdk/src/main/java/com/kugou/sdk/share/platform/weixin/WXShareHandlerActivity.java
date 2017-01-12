package com.kugou.sdk.share.platform.weixin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kugou.sdk.share.core.IShareCallback;
import com.kugou.sdk.share.core.Platform;
import com.kugou.sdk.share.core.PlatformConfig;
import com.kugou.sdk.share.core.ShareParams;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXShareHandlerActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI mIWXAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String appid = PlatformConfig.getInstance().getConfig(Platform.Key.WEIXIN_APPID).toString();
        mIWXAPI = WXAPIFactory.createWXAPI(this, appid, false);
        mIWXAPI.registerApp(appid);
        mIWXAPI.handleIntent(getIntent(), this);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mIWXAPI.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        //微信分享结果回调
        IShareCallback callBack = WeixinPlatform.sCallback;
        ShareParams info = WeixinPlatform.sInfo;
        if (callBack != null&&info!=null) {
            int scene = info.scene;
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
