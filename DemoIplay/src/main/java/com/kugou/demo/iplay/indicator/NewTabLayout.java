
package com.kugou.demo.iplay.indicator;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.kugou.demo.iplay.R;

/**
 * 描述:实现显示new的tab
 *
 * @author littlechuanxia
 * @since 2015年4月18日 下午4:40:38
 */
public class NewTabLayout extends TabLayout {
    private ImageView mIvTips;

    /**
     * @param context
     * @param tabName tab名称
     */
    public NewTabLayout(Context context, CharSequence tabName) {
        super(context, tabName);
    }

    /**
     * 将tab角标进行隐藏
     */
    public void hideNewTips() {
        if (mIvTips != null) {
            mIvTips.setVisibility(View.GONE);
        }
    }

    /**
     * 将角标显示
     */
    public void showNewTips() {
        if (mIvTips == null) {
            mIvTips = new ImageView(getContext());
            mIvTips.setLayoutParams(new LayoutParams(25,18));
            mIvTips.setImageResource(R.drawable.img_icon_new);
            addView(mIvTips);
        }
        mIvTips.setVisibility(View.VISIBLE);
    }
}
