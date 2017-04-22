package com.kugou.demo.socheck;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

//    static {
//        //若自己拷贝的so文件存在证明该设备无法载入lib里的so文件，改为获取自己拷贝的so文件
//        String apkLibPath = "/data/data/" + PackageUtil.getPackageName() + "/libs";
//        String pdfSoPath = apkLibPath + "/armeabi-v7a/libmupdf.so";
//        File pdfSoFile = new File(pdfSoPath);
//        boolean isExist = pdfSoFile.exists();
//        if(isExist){
//            System.load(pdfSoFile.getAbsolutePath());
//        }
//        else{
//            System.loadLibrary("mupdf");
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String apkpath  = getPackageCodePath();
        // /data/app/com.kugou.demo.socheck-1/base.apk
        Log.e("dax","apkpath -- "+apkpath);
    }
}
