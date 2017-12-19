package com.dax.lib.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dax.demo.lib.R;
import com.dax.lib.common.AppLib;
import com.dax.lib.http.HttpCookieManager;
import com.dax.lib.util.LogHelper;
import com.dax.lib.util.Singleton;

import java.io.InputStream;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.OkHttpClient;

public class ImageHelper {

    public static final String TAG = "ImageHelper";

    private static Singleton<ImageHelper> singleton = new Singleton<ImageHelper>() {
        @Override
        protected ImageHelper init() {
            return new ImageHelper(AppLib.getContext());
        }
    };

    private Context mContext;

    public ImageHelper(Context context){
        mContext = context;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(HttpCookieManager.getInstance());
        Glide.get(context).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(builder.build()));
    }

    public static ImageHelper getInstance() {
        return singleton.get();
    }

    @Deprecated
    public static ImageHelper getInstance(Context context) {
        return getInstance();
    }

    public void display(ImageView iv, String url) {
        display(iv, url, R.drawable.placeholder, R.drawable.placeholder, true, true);
    }

    public void display(ImageView iv, String url, boolean animation) {
        display(iv, url, R.drawable.placeholder, R.drawable.placeholder, true, animation);
    }

    public void display(ImageView iv, Uri uri) {
        display(iv, uri, R.drawable.placeholder, R.drawable.placeholder, true);
    }

    public void display(ImageView imageView, String url,
                        int defaultImage, int errorImage, boolean cache, boolean animation) {
        LogHelper.d(TAG, "display:" + url);
        BitmapRequestBuilder<String, Bitmap> builder = Glide.with(mContext)
                .load(url)
                .asBitmap();
        performImage(builder, imageView, defaultImage, errorImage, cache, animation);
    }

    public void display(ImageView imageView, Uri uri,
                        int defaultImage, int errorImage, boolean cache) {
        LogHelper.d(TAG, "display:" + uri);
        BitmapRequestBuilder<Uri, Bitmap> builder = Glide.with(mContext)
                .load(uri)
                .asBitmap();
        performImage(builder, imageView, defaultImage, errorImage, cache, true);
    }

    public void displayCircle(ImageView imageView, String url) {
        displayCircle(imageView, url, R.drawable.placeholder, R.drawable.placeholder, true);
    }

    public void displayCircle(ImageView imageView, String url,
                              int defaultImage, int errorImage, boolean cache) {
        LogHelper.d(TAG, "displayRound:" + url);
        BitmapRequestBuilder<String, Bitmap> builder = Glide.with(mContext)
                .load(url)
                .asBitmap()
                .transform(new CropCircleTransformation(mContext));
        performImage(builder, imageView, defaultImage, errorImage, cache, true);
    }

    public void displayRound(ImageView imageView, String url, int radius) {
        displayRound(imageView, url, R.drawable.placeholder, R.drawable.placeholder, true, radius);
    }

    public void displayRound(ImageView imageView, String url, int radius, boolean animation) {
        displayRound(imageView, url, R.drawable.placeholder, R.drawable.placeholder, true, radius, animation);
    }
    public void displayRound(ImageView imageView, String url,
                             int defaultImage, int errorImage, boolean cache, int radius) {
        displayRound(imageView, url, defaultImage, errorImage, cache, radius, true);
    }

    public void displayRound(ImageView imageView, String url,
                             int defaultImage, int errorImage, boolean cache, int radius, boolean animation) {
        LogHelper.d(TAG, "displayRound:" + url);
        BitmapRequestBuilder<String, Bitmap> builder = Glide.with(mContext)
                .load(url)
                .asBitmap()
                .transform(new RoundedCornersTransformation(mContext, radius, 0));
        performImage(builder, imageView, defaultImage, errorImage, cache, animation);
    }

    public void displayRound(ImageView imageView, String url,
                             Drawable defaultImage, Drawable errorImage, boolean cache, int radius) {
        displayRound(imageView, url, defaultImage, errorImage, cache, radius, true);
    }

    public void displayRound(ImageView imageView, String url,
                             Drawable defaultImage, Drawable errorImage, boolean cache, int radius, boolean animation) {
        LogHelper.d(TAG, "displayRound:" + url);
        BitmapRequestBuilder<String, Bitmap> requestBuilder = Glide.with(mContext)
                .load(url)
                .asBitmap()
                .transform(new RoundedCornersTransformation(mContext, radius, 0));
        performImage(requestBuilder, imageView, defaultImage, errorImage, cache, animation);
    }

    public void displayCircle(ImageView imageView, String url,
                              Drawable defaultImage, Drawable errorImage, boolean cache) {
        LogHelper.d(TAG, "displayRound:" + url);
        BitmapRequestBuilder<String, Bitmap> requestBuilder = Glide.with(mContext)
                .load(url)
                .asBitmap()
                .transform(new CropCircleTransformation(mContext));
        performImage(requestBuilder, imageView, defaultImage, errorImage, cache, true);
    }

    private void performImage(BitmapRequestBuilder<?, Bitmap> requestBuilder, ImageView imageView, Drawable defaultImage, Drawable errorImage, boolean cache, boolean animation) {
        requestBuilder = requestBuilder.diskCacheStrategy(cache ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                .skipMemoryCache(!cache)
                .placeholder(defaultImage)
                .error(errorImage);
        if (!animation) {
            requestBuilder.dontAnimate();
        }
        requestBuilder.into(imageView);
    }

    private void performImage(BitmapRequestBuilder<?, Bitmap> requestBuilder, ImageView imageView, int defaultImage, int errorImage, boolean cache, boolean animation) {
        requestBuilder = requestBuilder.diskCacheStrategy(cache ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                .skipMemoryCache(!cache)
                .placeholder(defaultImage)
                .error(errorImage);
        if (!animation) {
            requestBuilder.dontAnimate();
        }
        requestBuilder.into(imageView);
    }

    public void showGif(Context context, ImageView imageView, int gifID) {
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(context).load(gifID).into(imageViewTarget);
    }

    public interface DownloadListener {
        void onDownload(Bitmap bitmap);
    }

    public void downloadBitmap(String imageUrl, final DownloadListener listener) {
        LogHelper.d(TAG, "downloadBitmap:" + imageUrl);
        Glide.with(mContext)
                .load(imageUrl)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (listener != null) {
                            listener.onDownload(resource);
                        }
                    }
                });
    }

    /**
     * 预加载图片
     */
    public void preLoadImage(final String url) {
        ImageHelper.getInstance().downloadBitmap(url, new ImageHelper.DownloadListener() {
            @Override
            public void onDownload(Bitmap bitmap) {
                LogHelper.d(TAG, "preload image success:" + url);
            }
        });
    }
}
