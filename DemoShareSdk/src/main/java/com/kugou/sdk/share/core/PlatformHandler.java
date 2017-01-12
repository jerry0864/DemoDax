package com.kugou.sdk.share.core;

import android.app.Activity;
import android.content.Intent;

/**
 * 分享平台抽象
 *@author liuxiong
 *@since 2017/1/3 10:11
 */
public abstract class PlatformHandler {
    /**
     * 初始化平台
     * @param activity
     */
    public abstract void init(Activity activity);

    /**
     * 分享
     * @param shareType 分享类型
     * @param params 分享参数
     * @param callback 分享回调
     */
    public abstract void share(ShareType shareType, ShareParams params, IShareCallback callback);

    /**
     * 平台activity接收回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){}

    /**
     * 平台activity接收回调
     * @param intent
     */
    public void onNewIntent(Intent intent){}
}
