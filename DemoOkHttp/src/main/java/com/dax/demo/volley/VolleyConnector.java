package com.dax.demo.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dax.demo.http.IHttpConnector;

/**
 * Created by jerryliu on 2017/3/22.
 */

public class VolleyConnector implements IHttpConnector {
    RequestQueue mRequestQueue;
    public VolleyConnector(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
    }
    @Override
    public void request() {
        StringRequest request = new StringRequest("http://www.163.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("dax","volley-- onResponse");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("dax","volley-- onErrorResponse");
            }
        });
        mRequestQueue.add(request);
    }
}
