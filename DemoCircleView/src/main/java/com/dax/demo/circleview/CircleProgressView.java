package com.dax.demo.circleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Desc:圆形进度条
 * Created by liuxiong on 2017/10/16.
 */
public class CircleProgressView extends View {
    private Paint mPaintRing;//圆环画笔
    private Paint mPaintProgress; //进度画笔
    private RectF mRectProgress;
    private float mRingWidth;
    private int mRingColor;
    private int mProgressColor;
    private float mProgressAngle;//进度 角度度数
    private int mDefaultRingColor = Color.parseColor("#EEEEEE");
    private int mDefaultProgressColor = Color.parseColor("#FF7070");

    public CircleProgressView(Context context) {
        super(context);
        initAttrs(null, 0);
        init();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs, 0);
        init();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs, defStyleAttr);
        init();
    }

    private void initAttrs(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleProgressView,
                defStyle, 0);
        mRingWidth = a.getDimension(R.styleable.CircleProgressView_ringWidth, 20f);
        mRingColor = a.getColor(R.styleable.CircleProgressView_ringColor, mDefaultRingColor);
        mProgressColor = a.getColor(R.styleable.CircleProgressView_progressColor,mDefaultProgressColor);
    }

    private void init() {
        mPaintRing = new Paint();
        mPaintRing.setColor(mRingColor);
        mPaintRing.setStrokeWidth(mRingWidth);
        mPaintRing.setAntiAlias(true);
        mPaintRing.setStyle(Paint.Style.STROKE);

        mPaintProgress = new Paint();
        mPaintProgress.setColor(mProgressColor);
        mPaintProgress.setStrokeWidth(mRingWidth);
        mPaintProgress.setAntiAlias(true);
        mPaintProgress.setStyle(Paint.Style.STROKE);
        mPaintProgress.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRectProgress = new RectF(mRingWidth / 2, mRingWidth / 2, getMeasuredWidth() - mRingWidth / 2, getMeasuredHeight() - mRingWidth / 2);
        //渐变
        Shader shader = new LinearGradient(getMeasuredWidth() / 2, 0, getMeasuredWidth(), getMeasuredHeight() / 2, new int[]{0xFFFFA7A7, 0xFFFF7070}, null, Shader.TileMode.CLAMP);
        mPaintProgress.setShader(shader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外圆
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2 - mRingWidth / 2, mPaintRing);
        //画进度
        canvas.drawArc(mRectProgress, -90f, mProgressAngle, false, mPaintProgress);
    }

    /**
     * 设置百分比
     *
     * @param percent 进度比例，如0.5
     */
    public void setProgress(float percent) {
        if(percent > 1.0f){
            this.mProgressAngle = 360f;
        }else{
            this.mProgressAngle = 360 * percent;
        }
        postInvalidate();
    }
}
