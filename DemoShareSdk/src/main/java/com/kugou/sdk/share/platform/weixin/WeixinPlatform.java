package com.kugou.sdk.share.platform.weixin;


import android.app.Activity;
import android.content.Intent;

import com.kugou.sdk.share.base.ShareParams;
import com.kugou.sdk.share.base.ShareType;
import com.kugou.sdk.share.base.IPlatform;
import com.kugou.sdk.share.base.IShareCallback;

import java.lang.ref.WeakReference;

/**
 * 微信平台
 *@author liuxiong
 *@since 2017/1/4 16:02
 */
public class WeixinPlatform implements IPlatform {
    private WeakReference<Activity> mReference;
    @Override
    public void init(Activity activity) {
        mReference = new WeakReference<Activity>(activity);
    }

    public static IShareCallback sCallback;
    @Override
    public void share(ShareType shareType, ShareParams params, IShareCallback callback) {
        if(getActivity()==null){
            return;
        }

        sCallback = callback;
        Intent intent = new Intent(getActivity(), WeiXinShareHandlerActivity.class);
        intent.putExtra("shareType",shareType.ordinal());
        intent.putExtra("info",params);
        getActivity().startActivity(intent);
    }

    private Activity getActivity(){
        if(mReference!=null){
            return mReference.get();
        }
        return null;
    }
}
