
package com.kugou.demo.iplay.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kugou.demo.iplay.R;


/**
 * 描述:提示界面
 * 
 * @author chenys
 * @since 2013-10-27 下午4:13:33
 */
public class TipsLayout extends RelativeLayout {

    /** 显示正在加载提示 */
    public static final int TYPE_LOADING = 1;

    /** 显示加载错误提示 */
    public static final int TYPE_FAILE = 2;

    /** 显示空白提示 */
    public static final int TYPE_EMPTY_CONTENT = 3;

    /** 显示自定义提示界面 */
    public static final int TYPE_CUSTOM_VIEW = 4;

    private View mLoadingView;

    private View mLoadFaileView;

    private Button befreshBtn;

    private LoadingView progressBar;

    private TextView loadingText;

    public TipsLayout(Context context) {
        this(context, null);
    }

    public TipsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipsLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLoadingView = inflater.inflate(R.layout.layout_loading, null);
        progressBar = (LoadingView) mLoadingView.findViewById(R.id.progress_loading_bar);
        loadingText = (TextView) mLoadingView.findViewById(R.id.loading_tips);
        mLoadFaileView = inflater.inflate(R.layout.layout_load_faile, null);
        befreshBtn = (Button) mLoadFaileView.findViewById(R.id.btn_refresh);
        View emptyView = inflater.inflate(R.layout.layout_load_empty, null);
        LayoutParams rlp = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mLoadingView, rlp);
        addView(mLoadFaileView, rlp);
        addView(emptyView, rlp);

    }

    /**
     * 设置刷新按钮点击事件
     *
     * @param listener
     */
    public void setOnRefreshButtonClickListener(OnClickListener listener) {
        if (befreshBtn != null) {
            befreshBtn.setOnClickListener(listener);
        }
    }

    /**
     * 设置刷新按钮的背景色
     *
     * @param resId
     */
    public void setRefreshButtonBackgroud(int resId) {
        befreshBtn.setBackgroundResource(resId);
    }

    /**
     * 设置加载进度框的背景图
     */
    public void setLoadingViewBackground(int resId) {
        progressBar.setImageResource(resId);
    }

    /**
     * 设置加载提示文字
     *
     * @param text
     */
    public void setLoadingText(String text) {
        if (loadingText != null) {
            loadingText.setText(text);
        }
    }

    /**
     * 显示提示界面
     *
     * @param showType 显示类型，分为显示loading和显示刷新按钮
     */
    public void show(int showType) {
        setVisibility(View.VISIBLE);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(View.GONE);
        }
        if (showType == TYPE_LOADING) {
            getChildAt(0).setVisibility(View.VISIBLE);
        } else if (showType == TYPE_FAILE) {
            getChildAt(1).setVisibility(View.VISIBLE);
        } else if (showType == TYPE_EMPTY_CONTENT) {
            getChildAt(2).setVisibility(View.VISIBLE);
        } else if (showType == TYPE_CUSTOM_VIEW) {
            if (getChildCount() == 4) {
                getChildAt(3).setVisibility(View.VISIBLE);
            }
        }
        if (mLoadingView != null && mLoadingView.getVisibility() == View.VISIBLE) {
            mLoadingView.findViewById(R.id.progress_loading_bar).setVisibility(View.VISIBLE);
        } else {
            mLoadingView.findViewById(R.id.progress_loading_bar).setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏提示界面
     */
    public void hide() {
        setVisibility(View.GONE);
    }

    /**
     * 设置自定义提示View
     *
     * @param customTipsView
     */
    public void setCustomView(View customTipsView) {
        if (getChildCount() == 3) {
            LayoutParams rlp = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            rlp.addRule(RelativeLayout.CENTER_IN_PARENT);

            addView(customTipsView, rlp);
        }
    }

}
