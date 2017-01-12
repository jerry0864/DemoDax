package com.kugou.sdk.share.core;

import java.util.HashMap;

/**
 * 分享平台的配置参数
 *@author liuxiong
 *@since 2017/1/4 15:43
 */
public class PlatformConfig {
    private HashMap<String ,Object> mConfigMap = new HashMap<String ,Object>();
    private PlatformConfig(){}
    public static PlatformConfig getInstance(){
        return Builder.INSTANCE;
    }

    public void setConfig(String key,Object value){
        mConfigMap.put(key,value);
    }

    public Object getConfig(String key){
        return mConfigMap.get(key);
    }

    private static class Builder{
        private static PlatformConfig INSTANCE = new PlatformConfig();
    }
}
