package com.dax.demo.longscreenshot;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//参考资料：
//https://my.oschina.net/lifj/blog/396613
//https://my.oschina.net/lifj/blog/396617
public class ShareUtil {

    public static void shareToWeiXinFriend(Context context){
        String imagePath = Environment.getExternalStorageDirectory() + File.separator +
                "dax/datu.png";
        File file = new File(imagePath);
        Log.d("dax_test",file.exists()+" -- " +file.getAbsolutePath());
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Intent shareIntent = new Intent();
        //发送图片给好友
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    public static void shareToWeiXinZone(Context context){
        // TODO: 2017/11/9 检查是否有安装微信
        String imagePath = Environment.getExternalStorageDirectory() + File.separator +
                "dax/datu.png";
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Intent shareIntent = new Intent();
        //发送图片到朋友圈
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    public static void shareToQQFriend(Context context){
        String imagePath = Environment.getExternalStorageDirectory() + File.separator +
                "dax/datu.png";
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Intent shareIntent = new Intent();
        //发送图片到qq
        ComponentName comp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    //把文件插入到系统图库
    public void saveToGallery(Context context,File file){
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
            MediaScannerConnection.scanFile(context,
                    new String[]{file.getAbsolutePath()}, null, null);
            //保存图片后发送广播通知更新数据库
            //过时方法
//        Uri uri = Uri.fromFile(file);
//        App.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void shareToSinaWeibo(Activity activity, String msg, ArrayList<Uri> images) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            intent.setType("image/jpeg");
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, images);
            intent.setClassName("com.sina.weibo", "com.sina.weibo.EditActivity");
            activity.startActivity(intent);
    }
}
