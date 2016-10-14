
package com.dax.demo.smallgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 游戏View Created by jerryliu on 2016/10/11.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    SurfaceHolder mHolder;

    boolean mRunning;

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    Thread thread;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("dax", "surfaceCreated");
        mRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    int x;

    private void render(Canvas canvas) {
        canvas.drawText(String.valueOf(x++), getWidth() / 2, getHeight() / 2, paint);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("dax", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("dax", "surfaceDestroyed");
        mRunning = false;
        mHolder.removeCallback(this);
        // 销毁后台绘图线程
        if (thread != null && thread.isAlive()) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    Paint paint = new Paint();

    @Override
    public void run() {
        Canvas canvas = null;
        while (mRunning) {
            synchronized (GameView.class) {
                // 获取画布
                canvas = getHolder().lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.WHITE);
                    paint.setAntiAlias(true);
                    paint.setColor(Color.RED);
                    paint.setTextSize(40f);
                    paint.setStrokeWidth(10f);
                    // 开始绘制
                    render(canvas);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 释放画布
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
