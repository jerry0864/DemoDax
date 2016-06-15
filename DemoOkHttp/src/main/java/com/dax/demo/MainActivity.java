package com.dax.demo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Platform;
import okhttp3.internal.http.HttpEngine;
import okio.GzipSink;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpClient ohc = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com").cacheControl(CacheControl.FORCE_NETWORK).build();

        final Call call1 = ohc.newCall(request);
        final Call call2 = ohc.newCall(request);

        //同步
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call1.execute();
                    Log.d("dax","同步response："+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //异步
        call2.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("dax","异步response："+response.body().string());
            }
        });
        //api
        CacheControl.Builder builder = new CacheControl.Builder();
        CacheControl cacheControl = builder.build();
        Route route;
        Dispatcher dispatcher;
        Platform platforml;
        HttpEngine httpEngine;
        Interceptor interceptor;
        ohc.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return null;
            }
        });
        GzipSink gzipSink;
        ohc.cache().directory();
        Call call;

        HttpURLConnection hurl;
        View view;
        ViewGroup viewGroup;
        LinearLayout ll;
        FrameLayout frameLayout;

    }
}
