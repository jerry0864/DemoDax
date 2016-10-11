
package com.kugou.demo.iplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kugou.demo.iplay.R;


public class LoadingView extends ImageView {

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            int id = R.anim.loading;
            if (id != 0) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), id);
                // 使用ImageView显示动画
                startAnimation(anim);
            }
        } else {
            clearAnimation();
        }
    }
}
