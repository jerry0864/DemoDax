package com.dax.demo.http;

import com.dax.demo.okhttp.OKHttpConnector;

/**
 * Created by jerryliu on 2017/3/22.
 */

public class NetworkManager {
    private IHttpConnector mConnector;
    private NetworkManager(){
        //默认使用OKHttp联网
        mConnector = new OKHttpConnector();
    }

    public NetworkManager setConnector(IHttpConnector connector){
        this.mConnector  = connector;
        return this;
    }

    public void request(){
        mConnector.request();
    }

    public static NetworkManager getInstance(){
        return Builder.INSTANCE;
    }

    public static class Builder{
        private static NetworkManager INSTANCE = new NetworkManager();
    }
}
