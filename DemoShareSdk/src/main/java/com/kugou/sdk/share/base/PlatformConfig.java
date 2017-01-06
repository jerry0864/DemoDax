package com.kugou.sdk.share.base;

import java.util.HashMap;

/**
 * 分享平台的配置参数
 *@author liuxiong
 *@since 2017/1/4 15:43
 */
public class PlatformConfig {
    private PlatformConfig(){}
    public static PlatformConfig getInstance(){
        return Builder.INSTANCE;
    }

    private static class Builder{
        private static PlatformConfig INSTANCE = new PlatformConfig();
    }

    private HashMap<String ,Object> mConfigMap = new HashMap<String ,Object>();

    public void setConfig(String key,Object value){
        mConfigMap.put(key,value);
    }

    public Object getConfig(String key){
        return mConfigMap.get(key);
    }

    public static class Key {
        public static final String WEIXIN_APPID = "key_weixin_appid";
        public static final String WEIXIN_APPKEY = "key_weixin_appkey";
        public static final String QQ_APPID = "key_qq_appid";
        public static final String QQ_APPKEY = "key_qq_appkey";
    }
}
