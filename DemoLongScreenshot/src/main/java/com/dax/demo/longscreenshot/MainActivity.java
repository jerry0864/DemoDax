package com.dax.demo.longscreenshot;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//https://juejin.im/entry/5980419d5188254ae4503c1c
//https://github.com/shallcheek/TimeTableView
//https://magiclen.org/android-drawingcache/ --getDrawingCache 返回null
public class MainActivity extends AppCompatActivity {
    ScrollView scrollView;
    ImageView imageView,iconImage;
    TextView textView;
    WebView webView;
    LinearLayout layoutTop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= 21) {//缓存整个网页内容的快照
//            WebView.enableSlowWholeDocumentDraw();
//        }
        setContentView(R.layout.activity_main);
        initView();
        //监听截图
        //registerFileObserver();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //webView.loadUrl("http://www.baidu.com");
                webView.loadUrl("http://www.qq.com");
            }
        },200);
        imageView = (ImageView) findViewById(R.id.iv_screen_shot);
        iconImage = (ImageView) findViewById(R.id.image);
        textView = (TextView) findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ShareUtil.shareToWeiXinFriend(MainActivity.this);
                //ShareUtil.shareToWeiXinZone(MainActivity.this);
                //ShareUtil.shareToQQFriend(MainActivity.this);
                //screenShot(webView);
            }
        });
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        layoutTop = (LinearLayout)findViewById(R.id.layout_top);
    }

    public void screenshot(View view){
        Bitmap bitmap = ScreenShotHelper.createScreenShot(webView);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageBitmap(bitmap);
    }

    private void screenShot(View view) {
        //Bitmap bitmap = viewShot(textView);
        //view = getWindow().getDecorView();
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
//        view.buildDrawingCache();
//        Bitmap bitmap = view.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        int layoutHeight = layoutTop.getMeasuredHeight();
        layoutTop.buildDrawingCache();
        Bitmap textBitMap = layoutTop.getDrawingCache();

        Log.d("dax_test","width:"+width+" --height:"+(height + layoutHeight));
        Bitmap longImage = Bitmap.createBitmap(width, height + layoutHeight, Bitmap.Config.ARGB_4444);
        //Log.d("dax_test", "size-->" + longImage.getByteCount());
        Canvas canvasLong = new Canvas(longImage);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvasLong.drawBitmap(textBitMap, 0, 0, paint);
        canvasLong.drawBitmap(bitmap, 0, layoutHeight, paint);

        imageView.setVisibility(View.VISIBLE);
        imageView.setImageBitmap(longImage);
    }

    public static Bitmap getScrollViewBitmap(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getMeasuredWidth(), h,
                Bitmap.Config.ARGB_4444);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#f2f7fa"));
        scrollView.draw(canvas);
        return bitmap;
    }


    public static Bitmap viewShot(final View view){
        if (view == null)
            return null;
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(measureSpec, measureSpec);

        if (view.getMeasuredWidth()<=0 || view.getMeasuredHeight()<=0) {
            return null;
        }
        Bitmap bm;
        try {
            bm = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        }catch (OutOfMemoryError e){
            System.gc();
            try {
                bm = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            }catch (OutOfMemoryError ee){
                return null;
            }
        }
        Canvas bigCanvas = new Canvas(bm);
        Paint paint = new Paint();
        int iHeight = bm.getHeight();
        bigCanvas.drawBitmap(bm, 0, iHeight, paint);
        view.draw(bigCanvas);
        return bm;
    }

    public static String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "saveImage");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        String path = Environment.getExternalStorageDirectory() + "/saveImage /" + fileName;    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        return Environment.getExternalStorageDirectory() + "/saveImage/" + fileName;
    }

    /**
     * 截取scrollview的屏幕
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(
                    Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 压缩图片
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 100) {
            // 重置baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 保存到sdcard
     * @param b
     * @return
     */
    public static String savePic(Bitmap b) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
                Locale.US);
        File outfile = new File("/sdcard/image");
        // 如果文件不存在，则创建一个新文件
        if (!outfile.isDirectory()) {
            try {
                outfile.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fname = outfile + "/" + sdf.format(new Date()) + ".png";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fname);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fname;
    }

    private static final File DIRECTORY_PICTURES = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_PICTURES);
    private static final File DIRECTORY_DCIM = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DCIM);
    private File DIRECTORY_SCREENSHOT;
    private FileObserver FILE_OBSERVER;

    private void registerFileObserver(){
        if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi")) {
            DIRECTORY_SCREENSHOT = new File(DIRECTORY_DCIM, "Screenshots");
        } else {
            DIRECTORY_SCREENSHOT = new File(DIRECTORY_PICTURES, "Screenshots");
        }

        //Log.d("dax_test","1--> "+DIRECTORY_SCREENSHOT.getPath());
        //4.4.4红米手机有回调，华为7.0系统没有回调
        FILE_OBSERVER = new FileObserver(DIRECTORY_SCREENSHOT.getPath(), FileObserver.ALL_EVENTS) {
            @Override
            public void onEvent(int event, String path) {
                //注意：在子线程回调的
                //Log.d("dax_test","main thread-1->"+(Looper.getMainLooper() == Looper.myLooper()));
                Log.d("dax_test", "1--> event:" + event+" path-->"+path);
                if (event == FileObserver.CREATE) {
                    String newPath = new File(DIRECTORY_SCREENSHOT, path).getAbsolutePath();
                    Log.d("dax_test", "2--> " + newPath);
                    //Toast.makeText(MainActivity.this,"path: " + newPath,Toast.LENGTH_SHORT).show();
                }
            }
        };
        //开始监听
        FILE_OBSERVER.startWatching();
        getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, CONTENT_OBSERVER);
    }

    private Handler HANDLER = new Handler();
    private final static String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID,
    };
    private final ContentObserver CONTENT_OBSERVER = new ContentObserver(HANDLER) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            // TODO: 2017/11/2  记得先检查读文件的权限
            //主线程回调
            //Log.d("dax_test","main thread-2->"+(Looper.getMainLooper() == Looper.myLooper()));
            ContentResolver resolver = getContentResolver();
            if (uri.toString().matches(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "(/\\d+)?")) {
                Cursor cursor = resolver.query(uri, IMAGE_PROJECTION, null, null, MediaStore.MediaColumns.DATE_ADDED + " DESC");
                if (cursor != null && cursor.moveToFirst()) {
                    //完整路径
                    String newPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                    Log.d("dax_test", "3---> " + newPath);
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(FILE_OBSERVER !=null){
            //停止监听
            FILE_OBSERVER.stopWatching();
        }
    }
}
