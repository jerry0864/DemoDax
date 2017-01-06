package com.kugou.sdk.share.base;

/**
 * 平台分享场景
 *@author liuxiong
 *@since 2017/1/5 17:30
 */
public class Scene {
    //微信分享场景 0会话；1朋友圈；2收藏
    public static final class WeiXin{
        /*微信好友*/
        public static final int FRIEND = 0;
        /*微信朋友圈*/
        public static final int ZONE = 1;
        /*微信收藏*/
        public static final int COLLECT = 2;
    }

    //QQ分享场景
    public static final class QQ{
        /*QQ好友*/
        public static final int QQ_FRIEND = 1;
        /*QQ空间*/
        public static final int QQ_ZONE = 2;

    }
}
