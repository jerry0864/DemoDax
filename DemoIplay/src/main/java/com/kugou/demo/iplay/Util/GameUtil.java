package com.kugou.demo.iplay.Util;

import android.content.Context;

/**
 * Created by jerryliu on 2016/7/22.
 */
public class GameUtil {

    /**
     * 根据手机分辨率将dp转为px单位
     */
    public static int dip2px(Context context, float dpValue) {
        if (context == null) {
            return (int) dpValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机分辨率将px转为dp单位
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        if (context == null) {
            return (int) pxValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
