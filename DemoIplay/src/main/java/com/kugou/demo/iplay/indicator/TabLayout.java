
package com.kugou.demo.iplay.indicator;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kugou.demo.iplay.R;

/**
 * 描述:Tab基类
 *
 * @author littlechuanxia
 * @since 2015年4月18日 下午5:32:26
 */
public class TabLayout extends LinearLayout {

    private int mMaxTabWidth;

    private int mIndex;

    /**
     * @param context
     * @param tabName tab名称
     */
    public TabLayout(Context context, CharSequence tabName) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        setBackgroundColor(Color.WHITE);
        TextView view = new TextView(context, null, R.attr.game_vpiTabPageIndicatorStyle);
        view.setText(tabName);
        addView(view);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Re-measure if we went beyond our maximum size.
        if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth, MeasureSpec.EXACTLY),
                    heightMeasureSpec);
        }
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public void setMaxTabWidth(int width) {
        mMaxTabWidth = width;
    }
}
