package com.kugou.demo.iplay.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kugou.demo.iplay.R;

public class WelfareFragment extends Fragment {


    public WelfareFragment() {
        // Required empty public constructor
    }

    public WelfareFragment newInstance(){
        WelfareFragment fragment = new WelfareFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welfare, container, false);
    }

}
