/**
 * 
 */

package com.kugou.demo.iplay.indicator;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kugou.demo.iplay.R;

/**
 * 描述:实现带数量显示的tab
 * 
 * @author zhj
 * @since 2014-5-12 上午11:15:51
 */
public class CountTabLayout extends TabLayout {

    private TextView mTvCount;

    /**
     * @param context
     * @param tabName tab名称
     * @param count tab的角标，如果传入null或者“”则不显示角标
     */
    public CountTabLayout(Context context, CharSequence tabName, int count) {
        super(context, tabName);
        setCounts(count);
    }

    /**
     * 设置tab的角标数
     * 
     * @param count
     */
    public void setCounts(int count) {
        if (mTvCount == null) {
            mTvCount = new TextView(getContext(), null, R.attr.game_vpiTabTextCountStyle);
            addView(mTvCount);
        }
        if (count > 0) {
            showCount();
            mTvCount.setText(String.valueOf(count));
        } else {
            hideCount();
        }
    }

    /**
     * 将tab角标进行隐藏
     */
    private void hideCount() {
        mTvCount.setVisibility(View.GONE);
    }

    /**
     * 将角标显示
     */
    private void showCount() {
        mTvCount.setVisibility(View.VISIBLE);
    }
}
