
package com.dax.demo.swipelayout.widget;

import android.os.Bundle;

import com.dax.demo.swipelayout.R;

public class BaseSwipeBackActivity extends SwipeBackActivity {

    protected SwipeBackLayout mSwipeBackLayout;

    protected boolean mCanSwipe;

    public final static String ACTIVITY_CAN_SWIPE = "canSwipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCanSwipe = isCanSwipe();
        if (mCanSwipe) {
            overridePendingTransition(R.anim.comm_activity_open_swipe,
                    R.anim.comm_activity_exit_swipe);
        }
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setAllAreaCanScroll(true);
        mSwipeBackLayout.setEnableGesture(mCanSwipe);
    }

    protected boolean isCanSwipe() {
        return getIntent().getBooleanExtra(ACTIVITY_CAN_SWIPE, true);
    }


    @Override
    public void finish() {
        super.finish();
        if (mCanSwipe) {
            this.overridePendingTransition(R.anim.comm_activity_open_swipe,
                    R.anim.comm_activity_exit_swipe);
        }
    }
}
