package com.dax.demo.http;

/**
 * Created by Administrator on 2016/7/17.
 */
public interface IHttpClient {

    void request(IRequestPackage requestPackage,ICallBack callBack);
}
