package com.kugou.sdk.share.platform;

import android.app.Activity;

import com.kugou.sdk.share.core.IShareCallback;
import com.kugou.sdk.share.core.PlatformConfig;
import com.kugou.sdk.share.core.PlatformHandler;
import com.kugou.sdk.share.core.ShareParams;
import com.kugou.sdk.share.core.ShareType;

/**
 * Created by jerryliu on 2017/1/12.
 */

public class AlipayPlatform extends PlatformHandler {
    @Override
    public void init(Activity activity) {
        PlatformConfig.getInstance().getConfig("alipay_appid");
        PlatformConfig.getInstance().getConfig("alipay_appkey");
    }

    @Override
    public void share(ShareType shareType, ShareParams params, IShareCallback callback) {

    }
}
