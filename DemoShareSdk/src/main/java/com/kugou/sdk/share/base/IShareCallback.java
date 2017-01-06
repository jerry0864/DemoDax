package com.kugou.sdk.share.base;

/**
 * 分享回调接口
 *@author liuxiong
 *@since 2017/1/4 10:17
 */
public interface IShareCallback {
    void onSuccess(int scene);
    void onFaile(int scene,String errStr);
    void onCancel(int scene);
}
