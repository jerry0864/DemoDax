package com.kugou.demo.socheck;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by jerryliu on 2017/3/17.
 */

public class SoCheckRunnable implements Runnable {
    private static final String TAG = "SoRepairRunnable";
    private Context context;

    public SoCheckRunnable(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        String apkLibPath = "/data/data/" + context.getPackageName() + "/lib";
        String pdfSoPath = apkLibPath + "/libmupdf.so";
        File pdfSoFile = new File(pdfSoPath);
        boolean pdfSoExist = pdfSoFile.exists();
        if(pdfSoExist){
            return;
        }

        File v7aFile = new File("/data/data/" + context.getPackageName() + "/libs/armeabi-v7a");
        if(!v7aFile.exists()){
            v7aFile.mkdirs();
        }
        try {
            // /data/app/com.kugou.demo.socheck-1/base.apk
            String apkPath = getApkPath();
            //获取apk压缩包
            ZipFile apkFile = new ZipFile(apkPath);
            //取得压缩包内so文件
            ZipEntry pdfZipEntry = apkFile.getEntry("lib/armeabi-v7a/libmupdf.so");
            //将so文件写入到手机目录下
            writeSoToLibs(apkFile, pdfZipEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getApkPath(){
        return context.getPackageCodePath();
    }

    private void writeSoToLibs(ZipFile zipFile, ZipEntry entry) throws IOException {
        String entryName = entry.getName();
        String fileName = "/data/data/" + context.getPackageName() + "/" + entryName.replaceFirst("lib/", "libs/");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = zipFile.getInputStream(entry);
            File file = new File(fileName  + ".tmp");
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);

            int byteCount = 0;

            byte[] bytes = new byte[8096];
            while ((byteCount = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, byteCount);
            }
            outputStream.flush();
            File newFile = new File(fileName);
            file.renameTo(newFile);
            file.delete();

        }
        catch (IOException e){
            e.printStackTrace();
        }finally {
            if(null != inputStream) {
                inputStream.close();
            }
            if(null != outputStream) {
                outputStream.close();
            }
        }
    }
}
