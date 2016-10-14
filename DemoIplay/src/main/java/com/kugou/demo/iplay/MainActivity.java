
package com.kugou.demo.iplay;

import com.kugou.demo.iplay.adapter.HomePagerAdapter;
import com.kugou.demo.iplay.fragment.GameHomeFragmet;
import com.kugou.demo.iplay.fragment.MenuFragment;
import com.kugou.demo.iplay.fragment.WelfareFragment;
import com.kugou.demo.iplay.slidingmenu.BaseSlidingActivity;
import com.kugou.demo.iplay.slidingmenu.SlidingMenu;
import com.kugou.demo.iplay.widget.MainTabViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * 应用首页
 * 
 * @author jerryliu
 * @since 2016/7/23 14:35
 */
public class MainActivity extends BaseSlidingActivity
        implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private RadioGroup mRadioGroup;

    private MainTabViewPager mViewPager;

    private SlidingMenu mSlidingMenu;

    private static final int TAB_GAME = 0;

    private static final int TAB_WELFARE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSlideMenu();
    }

    public MainTabViewPager getMainViewPager() {
        return mViewPager;
    }

    public SlidingMenu getHomeSlidingMenu() {
        return mSlidingMenu;
    }

    private void initSlideMenu() {
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setShadowDrawable(R.drawable.slidingmenu_img_shadow);
        mSlidingMenu.setBehindScrollScale(0);
        setBehindContentView(R.layout.slidingmenu_behind_view);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_framemenu, new MenuFragment()).commit();
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_home_tab);
        mRadioGroup.setOnCheckedChangeListener(this);

        mViewPager = (MainTabViewPager) findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(this);
        ArrayList<Fragment> fragments = new ArrayList<>();
        GameHomeFragmet gameFragment = new GameHomeFragmet();
        WelfareFragment welfareFragment = new WelfareFragment();
        fragments.add(gameFragment);
        fragments.add(welfareFragment);
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_tab_game:
                mViewPager.setCurrentItem(TAB_GAME);
                mViewPager.setCanScrollRight(true);
                break;
            case R.id.rb_tab_welfare:
                mViewPager.setCurrentItem(TAB_WELFARE);
                mViewPager.setCanScrollLeft(true);
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case TAB_GAME:
                mRadioGroup.check(R.id.rb_tab_game);
                mViewPager.setCanScrollRight(true);
                break;
            case TAB_WELFARE:
                mRadioGroup.check(R.id.rb_tab_welfare);
                mViewPager.setCanScrollLeft(true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
