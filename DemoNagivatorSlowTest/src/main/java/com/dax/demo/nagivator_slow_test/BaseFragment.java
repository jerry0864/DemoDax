package com.dax.demo.nagivator_slow_test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Desc:
 * Created by liuxiong on 2018/1/5.
 * Email:liuxiong@corp.netease.com
 */
public class BaseFragment extends Fragment {
    private static final String TAG = "dax_test";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,getClass().getSimpleName()+"--onCreate");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,getClass().getSimpleName()+"--onViewCreated");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,getClass().getSimpleName()+"--onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,getClass().getSimpleName()+"--onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,getClass().getSimpleName()+"--onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,getClass().getSimpleName()+"--onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,getClass().getSimpleName()+"--onResume");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,getClass().getSimpleName()+"--onAttach");
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Log.d(TAG,getClass().getSimpleName()+"--onInflate");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,getClass().getSimpleName()+"--onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,getClass().getSimpleName()+"--onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG,getClass().getSimpleName()+"--onDestroyView");
    }

    protected void delay(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
