package com.kugou.demo.iplay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * 首页ViewPager适配器
 * @author jerryliu
 * @since 2016/7/23 14:25
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> mFraments;
    public HomePagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.mFraments = list;
    }

    @Override
    public Fragment getItem(int position) {
        if(mFraments !=null){
            return mFraments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(mFraments !=null){
            return mFraments.size();
        }
        return 0;
    }

    public void release(){
        if(mFraments !=null){
            mFraments.clear();
        }
    }
}
