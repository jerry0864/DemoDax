package com.kugou.demo.iplay.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kugou.demo.iplay.MainActivity;
import com.kugou.demo.iplay.R;
import com.kugou.demo.iplay.adapter.GamePagerAdapter;
import com.kugou.demo.iplay.pagerslidingtabstrip.PagerSlidingTabStrip;
import com.kugou.demo.iplay.slidingmenu.SlidingMenu;
import com.kugou.demo.iplay.widget.MainTabViewPager;

import java.util.ArrayList;
/**
 * 游戏首页
 * @author jerryliu
 * @since 2016/7/25 11:33
 */
public class GameHomeFragmet extends Fragment implements ViewPager.OnPageChangeListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private MainTabViewPager mMainTabViewPager;
    private SlidingMenu mSlidingMenu;

    public GameHomeFragmet() {
        // Required empty public constructor
    }

    public static GameHomeFragmet newInstance(String param1, String param2) {
        GameHomeFragmet fragment = new GameHomeFragmet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        initView(view);
        return view;
    }

    private final String[] TITLES = { "英雄出装", "排位攻略", "冒险攻略", "视频教学", "冒险攻略视频教学" };
    private ViewPager mViewPager;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private GamePagerAdapter mAdapter;
    private void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mAdapter = new GamePagerAdapter(getChildFragmentManager());
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        for(int i = 0;i<5;i++){
            GameStrategyFragment fragment = GameStrategyFragment.newInstance("资讯列表页："+i);
            fragments.add(fragment);
            titles.add(TITLES[i]);
        }
        mAdapter.setFragments(fragments,titles);
        mViewPager.setAdapter(mAdapter);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.psts);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMainTabViewPager = getMainTabViewPager();
        mSlidingMenu = getHomeSlidingMenu();
    }

    private MainTabViewPager getMainTabViewPager() {
        Activity activity = getActivity();
        if(activity!=null&&activity instanceof MainActivity){
            return ((MainActivity) activity).getMainViewPager();
        }
        return null;
    }

    private SlidingMenu getHomeSlidingMenu() {
        Activity activity = getActivity();
        if(activity!=null&&activity instanceof MainActivity){
            return ((MainActivity) activity).getHomeSlidingMenu();
        }
        return null;
    }

    @Override
    public void onPageSelected(int position) {
        if (mViewPager != null && mMainTabViewPager != null) {
            if (position == 0) {
                mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                mMainTabViewPager.setCanScrollLeft(true);
            } else if (position == mViewPager.getAdapter().getCount() - 1) {
                mMainTabViewPager.setCanScrollRight(true);
                mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            } else {
                mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                mMainTabViewPager.setCanScroll(false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
}
