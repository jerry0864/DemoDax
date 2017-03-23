package com.dax.demo.okhttp;

import android.util.Log;

import com.dax.demo.http.IHttpConnector;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jerryliu on 2017/3/22.
 */

public class OKHttpConnector implements IHttpConnector {
    OkHttpClient mOkHttpClient;
    public OKHttpConnector(){
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.MILLISECONDS)
                .writeTimeout(3000, TimeUnit.MILLISECONDS)
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .build();
    }


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public String json = "{\"key\":\"value\"}";
    public void request() {
        RequestBody requestBody = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url("http://www.baidu.com").post(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("dax","okhttp-- onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("dax","okhttp-- onResponse");
            }
        });
    }
}
