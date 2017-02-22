package com.kugou.iplay.wz;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.kugou.sdk.share.core.IShareCallback;
import com.kugou.sdk.share.core.KGShare;
import com.kugou.sdk.share.core.Platform;
import com.kugou.sdk.share.core.PlatformConfig;
import com.kugou.sdk.share.core.Scene;
import com.kugou.sdk.share.core.ShareParams;
import com.kugou.sdk.share.core.ShareType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //配置平台参数，或放在自定义Application的onCreate方法内
        PlatformConfig.getInstance().setConfig(Platform.Key.WEIXIN_APPID,"wx8859f03b3da4971c");
        PlatformConfig.getInstance().setConfig(Platform.Key.WEIXIN_APPKEY,"06dccc2e7766f556345d353f7f9d21be");
        PlatformConfig.getInstance().setConfig(Platform.Key.QQ_APPID,"1105638776");
        PlatformConfig.getInstance().setConfig(Platform.Key.QQ_APPKEY,"hlejRjvG4QK0DbVw");
        //PlatformConfig.getInstance().setConfig("alipay_appid","hlejRjvG4QK0DbVw");
        //PlatformConfig.getInstance().setConfig("alipay_appkey","hlejRjvG4QK0DbVw");
        //KGShare.addPlatform("alipay",Object.class);
    }

    public void alipayShare(View view){
        //KGShare.share(MainActivity.this, "alipay", ShareType.VEDIO, null, new ShareListener());
    }

    public void weixinShare(View view){
        ShareParams params = new ShareParams();
        //params.webUrl = "http://www.baidu.com";
        params.title = "weixin demo title";
        params.desc ="hello world desc";
        params.scene = Scene.WeiXin.MOMENT;
        params.vedioUrl = "http://www.qq.com";

        KGShare.share(MainActivity.this, Platform.Name.WEIXIN, ShareType.VEDIO, params, new ShareListener());
    }

    public void qqShare(View view ){
        ShareParams params = new ShareParams();
        //params.webUrl = "http://www.baidu.com";
        params.title = "qq demo title";
        params.desc ="hello world desc";
        params.scene = Scene.QQ.ZONE;
        params.vedioLocalPath = "/storage/emulated/0/Samsung/Video/Wonders_of_Nature.mp4";
        //params.imgUrl = "http://img5.duitang.com/uploads/blog/201507/21/20150721211759_MzGsW.thumb.224_0.jpeg";

        KGShare.share(MainActivity.this, Platform.Name.QQ, ShareType.VEDIO, params, new ShareListener());
    }

    public void weiboShare(View view){
        ShareParams params = new ShareParams();
        params.title = "weibo demo title";
        params.desc ="hello world desc";
        params.vedioUrl = "http://www.sina.com.cn";
        //params.imgUrl = "http://img5.duitang.com/uploads/blog/201507/21/20150721211759_MzGsW.thumb.224_0.jpeg";

        KGShare.share(MainActivity.this, Platform.Name.WEIBO, ShareType.VEDIO, params, new ShareListener());
    }

    class ShareListener implements IShareCallback{
        @Override
        public void onSuccess(int scene) {
            Toast.makeText(MainActivity.this,"分享成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFaile(int scene,String errStr) {
            Toast.makeText(MainActivity.this,"分享失败:"+errStr,Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel(int scene) {
            Toast.makeText(MainActivity.this,"分享取消",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        KGShare.onActivityResult(requestCode,resultCode,data);
    }
}
