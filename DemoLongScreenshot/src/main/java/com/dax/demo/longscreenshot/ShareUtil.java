package com.dax.demo.longscreenshot;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Desc:
 * Created by liuxiong on 2017/11/3.
 * Email:liuxiong@corp.netease.com
 */

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
}
