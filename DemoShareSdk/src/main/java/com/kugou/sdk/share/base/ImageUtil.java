package com.kugou.sdk.share.base;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片处理工具类
 *@author liuxiong
 *@since 2017/1/9 14:46
 */
public class ImageUtil {

    private static final String IMAGE_FOLDER = Environment.getExternalStorageDirectory().toString()+File.separator+".shareimage/";

    public static void preloadImage(final String imgUrl){
        if(TextUtils.isEmpty(imgUrl)){
            return;
        }

        ThreadTaskManager.excuteTask(new Runnable() {
            @Override
            public void run() {
                downloadImage(imgUrl);
            }
        });
    }

    public static File loadImage(String imgUrl){
        if(TextUtils.isEmpty(imgUrl)){
            return null;
        }

        File file = checkLocalImageExsits(imgUrl);
        return file;
    }

    public static File downloadImage(String imgUrl) {
        File image = null;
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream is = conn.getInputStream();
                File folder = new File(IMAGE_FOLDER);
                if(!folder.exists()){
                    folder.mkdirs();
                }
                image = new File(IMAGE_FOLDER,getFileName(imgUrl));
                FileOutputStream fos = new FileOutputStream(image);
                int len = 0;
                byte[] buf = new byte[1024];
                while((len = is.read(buf))!=-1){
                    fos.write(buf,0,len);
                    fos.flush();
                }
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deleteImage(imgUrl);
        }
        return image;
    }

    private static void deleteImage(String imgUrl) {
        File file = checkLocalImageExsits(imgUrl);
        if(file!=null){
            file.delete();
        }
    }

    private static String getFileName(String imgUrl){
        String name = imgUrl.trim().substring(imgUrl.lastIndexOf("/")+1);
        return name;
    }

    private static File checkLocalImageExsits(String imgUrl) {
        String name = getFileName(imgUrl);
        File file = new File(IMAGE_FOLDER,name);
        if(file.exists()){
            return file;
        }
        return null;
    }

    public static void loadImage(final String imgUrl,final LoadImageListener listner){
        ThreadTaskManager.excuteTask(new Runnable() {
            @Override
            public void run() {
                if(TextUtils.isEmpty(imgUrl)){
                    if(listner!=null){
                        listner.onLoadEnd(null);
                    }
                }

                File file = checkLocalImageExsits(imgUrl);
                if(file == null){
                    file = downloadImage(imgUrl);
                }
                listner.onLoadEnd(file);
            }
        });
    }


    /**
     * 加载图片回调
     */
    public interface LoadImageListener{
        void onLoadEnd(File image);
    }
}
