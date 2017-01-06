package com.kugou.iplay.wz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.kugou.sdk.share.base.IShareCallback;
import com.kugou.sdk.share.base.PlatformConfig;
import com.kugou.sdk.share.core.KGShare;
import com.kugou.sdk.share.base.Platform;
import com.kugou.sdk.share.base.Scene;
import com.kugou.sdk.share.base.ShareParams;
import com.kugou.sdk.share.base.ShareType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //配置平台参数，或放在自定义Application的onCreate方法内
        PlatformConfig.getInstance().setConfig(PlatformConfig.Key.WEIXIN_APPID,"wx8859f03b3da4971c");
        PlatformConfig.getInstance().setConfig(PlatformConfig.Key.WEIXIN_APPKEY,"06dccc2e7766f556345d353f7f9d21be");
        PlatformConfig.getInstance().setConfig(PlatformConfig.Key.QQ_APPID,"1105638776");
        PlatformConfig.getInstance().setConfig(PlatformConfig.Key.QQ_APPKEY,"hlejRjvG4QK0DbVw");
    }

    public void weixinshare(View view){
        ShareParams params = new ShareParams();
        params.webUrl = "http://www.baidu.com";
        params.title = "weixin demo title";
        params.desc ="hello world desc";
        //0会话；1朋友圈；2收藏
        params.scene = Scene.WeiXin.ZONE;

        KGShare.share(MainActivity.this, Platform.WEIXIN, ShareType.WEB, params, new IShareCallback() {
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
        });
    }
}
