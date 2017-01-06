package com.kugou.sdk.share.base;

import android.app.Activity;

/**
 * 分享平台
 *@author liuxiong
 *@since 2017/1/3 10:11
 */
public interface IPlatform {
    /**
     * 初始化平台
     * @param activity
     */
    public void init(Activity activity);

    /**
     * 分享
     * @param shareType 分享类型
     * @param params 分享参数
     * @param callback 分享回调
     */
    public void share(ShareType shareType, ShareParams params, IShareCallback callback);
}
