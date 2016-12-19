package com.dax.demo.imagecompress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    ImageView image2;
    TextView text2;
    String imagePath = "/storage/emulated/0/DCIM/IMG_-810274061.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
        /storage/emulated/0/DCIM/Camera/1480933583406.jpg
        /storage/emulated/0/DCIM/Camera/1480818985617.jpg
        /storage/emulated/0/DCIM/IMG_-810274061.jpg
        /storage/emulated/0/DCIM/IMG_-1887140474.jpg
         */
        ImageView image1 = (ImageView)findViewById(R.id.image1);
        TextView text1 = (TextView) findViewById(R.id.text1);
        image2 = (ImageView)findViewById(R.id.image2);
        text2 = (TextView) findViewById(R.id.text2);
        File file = new File(imagePath);
        long len = file.length()/1024;
        image1.setImageURI(Uri.fromFile(file));
        text1.setText("压缩前大小："+len+" KB");


    }

    public void compressImage(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(imagePath);
                try {
                    final String compressImage = compress(file,200);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            File file = new File(compressImage);
                            long len = file.length()/1024;
                            FileInputStream fis = null;
                            try {
                                fis = new FileInputStream(new File(compressImage));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.RGB_565;
                            Bitmap bitmap = BitmapFactory.decodeStream(fis, null, options);
                            image2.setImageBitmap(bitmap);
                            text2.setText("压缩后大小："+len+" KB");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 图片压缩
     * @param file 图片文件
     * @param i 压缩后大小
     * @return
     */
    private String compress(File file, int i) throws Exception{
        FileInputStream fis = new FileInputStream(file);
        BitmapFactory.Options options = new BitmapFactory.Options();
        DisplayMetrics dm =getResources().getDisplayMetrics();
        int screenwith = dm.widthPixels;
        int screenheight = dm.heightPixels;
        int size = computeInsampleSize(file.getPath(),screenwith,screenheight);
        options.inSampleSize = size;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Log.d("dax","screenwith:"+screenwith);
        Log.d("dax","screenheight:"+screenheight);
        Log.d("dax","insamplesize:"+size);
        Bitmap bitmap = BitmapFactory.decodeStream(fis, null, options);
        int outwidth = bitmap.getWidth();
        int outheight = bitmap.getHeight();
        Log.d("dax","outwidth:"+outwidth);
        Log.d("dax","outheight:"+outheight);
        Log.d("dax","old_size:"+bitmap.getByteCount()/1024);
        //进行有损压缩
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int ratio = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, ratio, baos);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
        int baosLength = baos.toByteArray().length;
        while (baosLength / 1024 > i&&i>0) {
            Log.d("dax","compress_size: "+baosLength / 1024);
            baos.reset();
            ratio -= 10;//图片质量每次减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, ratio, baos);//将压缩后的图片保存到baos中
            baosLength = baos.toByteArray().length;
        }
//已压缩过
        File f = new File(Environment.getExternalStorageDirectory()+"/"+"compress_"+file.getName());
        Log.d("dax","path:"+f.getAbsolutePath());
//        if(f!=null&&f.exists()&&f.length()>0){
//            return f.getName();
//        }
        try {
            if(!f.exists()){
                f.createNewFile();
            }

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            byte[] b = baos.toByteArray();
            Log.d("dax","result:"+b.length/1000);
            bos.write(b);
            bos.close();

            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f.getPath();
    }

    private int computeInsampleSize(String srcImagePath,float outWidth,float outHeight){
        //进行大小缩放来达到压缩的目的
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcImagePath, options);
        //根据原始图片的宽高比和期望的输出图片的宽高比计算最终输出的图片的宽和高
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        float maxWidth = outWidth;
        float maxHeight = outHeight;
        float srcRatio = srcWidth / srcHeight;
        float outRatio = maxWidth / maxHeight;
        float actualOutWidth = srcWidth;
        float actualOutHeight = srcHeight;

        if (srcWidth > maxWidth || srcHeight > maxHeight) {
            //如果输入比率小于输出比率,则最终输出的宽度以maxHeight为准()
            //比如输入比为10:20 输出比是300:10 如果要保证输出图片的宽高比和原始图片的宽高比相同,则最终输出图片的高为10
            //宽度为10/20 * 10 = 5  最终输出图片的比率为5:10 和原始输入的比率相同

            //同理如果输入比率大于输出比率,则最终输出的高度以maxHeight为准()
            //比如输入比为20:10 输出比是5:100 如果要保证输出图片的宽高比和原始图片的宽高比相同,则最终输出图片的宽为5
            //高度需要根据输入图片的比率计算获得 为5 / 20/10= 2.5  最终输出图片的比率为5:2.5 和原始输入的比率相同
            if (srcRatio < outRatio) {
                actualOutHeight = maxHeight;
                actualOutWidth = actualOutHeight * srcRatio;
            } else if (srcRatio > outRatio) {
                actualOutWidth = maxWidth;
                actualOutHeight = actualOutWidth / srcRatio;
            } else {
                actualOutWidth = maxWidth;
                actualOutHeight = maxHeight;
            }
        }
        return (int)(srcWidth/actualOutWidth);
    }
}
