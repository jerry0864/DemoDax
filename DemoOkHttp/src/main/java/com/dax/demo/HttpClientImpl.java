package com.dax.demo;

import android.util.Log;

import com.dax.demo.http.ICallBack;
import com.dax.demo.http.IHttpClient;
import com.dax.demo.http.IRequestPackage;
import com.dax.demo.http.IResponsePackage;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/17.
 */
public class HttpClientImpl implements IHttpClient {

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    @Override
    public void request(IRequestPackage requestPackage, final ICallBack callBack) {
        Request request  = createRequest(requestPackage);
        //添加设置
        HashMap<String,Object> settings = requestPackage.getSettings();
        if(settings!=null){
            addSetting(settings);

        }
        //添加头部参数
        HashMap<String,String> headers = requestPackage.getRequestHeaders();
        if(headers!=null){
            addHeader(request,headers);
        }
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                IResponsePackage responsePackage = createResponsePackage(response);
                callBack.onSuccess(responsePackage);
            }
        });

    }

    private void addHeader(Request request,HashMap<String,String> headers){
        for (String key : headers.keySet()) {
            request.newBuilder().addHeader(key,headers.get(key));
        }
    }

    private void addSetting(HashMap<String,Object> settings){
        if (settings.containsKey("connect-timeout")) {
            // 设置 连接超时时间
            mOkHttpClient.newBuilder().connectTimeout((Integer) settings.get("connect-timeout"),TimeUnit.SECONDS);
        }

        if (settings.containsKey("read-timeout")) {
            // 设置 读数据超时时间
            mOkHttpClient.newBuilder().readTimeout((Integer) settings.get("read-timeout"),TimeUnit.SECONDS);
        }
    }




    private Request createRequest(IRequestPackage requestPackage) {
        Request.Builder builder = new Request.Builder();
        String url = requestPackage.getUrl();
        String params = requestPackage.getGetRequestParams();
        Log.d("http",url+params);
        int method = requestPackage.getRequestType();
        if(method == IRequestPackage.TYPE_GET){
            builder.url(url+params).get().tag("TODO").build();
        }else{
            byte[] data = requestPackage.getPostRequestEntity();
        }
        return null;
    }

    private IResponsePackage createResponsePackage(Response response) {

        return null;
    }
}
