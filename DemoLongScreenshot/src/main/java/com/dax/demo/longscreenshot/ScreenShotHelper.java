package com.dax.demo.longscreenshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;


public class ScreenShotHelper {

    private static final String TAG = "dax_test";
    public static Bitmap createScreenShot(WebView view){
        // 可见高度
        int viewHeight = view.getHeight();
        Log.d(TAG,"viewHeight---> "+viewHeight);
        int viewWidth = view.getWidth();
        Log.d(TAG,"viewWidth---> "+viewWidth);
        // 容器内容实际高度
        int contentHeight = (int)(view.getContentHeight()*view.getScale());
        Log.d(TAG,"contentHeight---> "+contentHeight);

        int scale = 4;
        int widthLong = viewWidth/scale;
        Log.d(TAG,"widthLong---> "+widthLong);
        int heightLong = contentHeight/scale;
        Log.d(TAG,"heightLong---> "+heightLong);
        Bitmap longImage = Bitmap.createBitmap(widthLong,heightLong, Bitmap.Config.ARGB_8888);
        Canvas canvasLongImage = new Canvas(longImage);

        Bitmap bitmap = Bitmap.createBitmap(viewWidth,viewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        int joinTop = 0;
        int tempHeight = bitmap.getHeight() / scale;
        Rect rectSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect rectDst = new Rect(0, 0, widthLong, tempHeight);
        joinTop += tempHeight;
        Log.d(TAG,"joinTop--1-> "+joinTop);
        Paint paint = new Paint();
        canvasLongImage.drawBitmap(bitmap,rectSrc,rectDst,paint);
        bitmap.recycle();

        if(contentHeight <= viewHeight){
            return longImage;
        }

        int leftHeight = contentHeight - viewHeight;
        Bitmap partBitmap;
        Log.d(TAG,"leftHeight--1-> "+leftHeight);
        while (leftHeight > 0) {
            Log.d(TAG,"leftHeight--2-> "+leftHeight +" viewHeight--->"+viewHeight);
            if (leftHeight > viewHeight) {
                view.scrollBy(0, viewHeight);
                partBitmap = getPartViewBitmap(view);
                Rect partRectSrc = new Rect(0, 0, partBitmap.getWidth(), partBitmap.getHeight());
                Rect partRectDst = new Rect(0, joinTop, widthLong, joinTop + tempHeight);
                canvasLongImage.drawBitmap(partBitmap, partRectSrc, partRectDst, paint);
                leftHeight = leftHeight - viewHeight;
                joinTop += tempHeight;
                Log.d(TAG,"joinTop--2-> "+joinTop);
            } else {
                view.scrollBy(0, leftHeight);
                partBitmap = getPartViewBitmap(view);
                Rect partRectSrc = new Rect(0, partBitmap.getHeight() - leftHeight, partBitmap.getWidth(), partBitmap.getHeight());
                Rect partRectDst = new Rect(0, joinTop, widthLong, joinTop+leftHeight);
                canvasLongImage.drawBitmap(partBitmap, partRectSrc, partRectDst, paint);
                leftHeight = 0;
            }
            partBitmap.recycle();
        }
        return longImage;
    }

    private static Bitmap getPartViewBitmap(WebView view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    public static Bitmap getWebViewBitmap(Context context, WebView view) {
        if (null == view) return null;
        view.scrollTo(0, 0);
        view.buildDrawingCache(true);
        view.setDrawingCacheEnabled(true);
        view.setVerticalScrollBarEnabled(false);
        Bitmap b = getViewBitmapWithoutBottom(view);
        // 可见高度
        int vh = view.getHeight();
        // 容器内容实际高度
        int th = (int)(view.getContentHeight()*view.getScale());
        Bitmap temp = null;
        if (th > vh) {
            int w = getScreenWidth(context);
            int absVh = vh - view.getPaddingTop() - view.getPaddingBottom();
            do {
                int restHeight = th - vh;
                if (restHeight <= absVh) {
                    view.scrollBy(0, restHeight);
                    vh += restHeight;
                    temp = getViewBitmap(view);
                } else {
                    view.scrollBy(0, absVh);
                    vh += absVh;
                    temp = getViewBitmapWithoutBottom(view);
                }
                b = mergeBitmap(vh, w, temp, 0, view.getScrollY(), b, 0, 0);
            } while (vh < th);
        }
        // 回滚到顶部
        view.scrollTo(0, 0);
        view.setVerticalScrollBarEnabled(true);
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return b;
    }

    private static Bitmap mergeBitmap(int newImageH, int newImageW, Bitmap background, float backX, float backY, Bitmap foreground, float foreX, float foreY) {
        if (null == background || null == foreground) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(newImageW, newImageH, Bitmap.Config.RGB_565);
        Canvas cv = new Canvas(bitmap);
        cv.drawBitmap(background, backX, backY, null);
        cv.drawBitmap(foreground, foreX, foreY, null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return bitmap;
    }

    /**
     * get the width of screen
     */
    public static int getScreenWidth(Context ctx) {
        int w = 0;
        if (Build.VERSION.SDK_INT > 13) {
            Point p = new Point();
            ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(p);
            w = p.x;
        } else {
            w = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        }
        return w;
    }

    private static Bitmap getViewBitmapWithoutBottom(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(), (int) v.getX() + v.getMeasuredWidth(), (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        Bitmap bp = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight() - v.getPaddingBottom());
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return bp;
    }

    public static Bitmap getViewBitmap(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(), (int) v.getX() + v.getMeasuredWidth(), (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return b;
    }

}
