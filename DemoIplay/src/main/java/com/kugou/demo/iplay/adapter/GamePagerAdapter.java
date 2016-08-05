package com.kugou.demo.iplay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 游戏页ViewPager适配器
 * @author jerryliu
 * @since 2016/7/23 16:33
 */
public class GamePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitles;

    public GamePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(ArrayList<Fragment> fragments,ArrayList<String> titles){
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if(mFragments!=null&&mFragments.size()>0){
            return mFragments.get(position);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitles!=null&&mTitles.size()>0){
            return mTitles.get(position);
        }
        return "";
    }

    @Override
    public int getCount() {
        if(mFragments!=null){
            return mFragments.size();
        }
        return 0;
    }
}
