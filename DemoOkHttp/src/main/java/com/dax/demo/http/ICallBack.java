package com.dax.demo.http;

/**
 * Created by Administrator on 2016/7/17.
 */
public interface ICallBack {
    void onFailure(String error);

    void onSuccess(IResponsePackage responsePackage);
}
