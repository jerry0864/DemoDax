package com.dax.demo.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ImageView imageView = (ImageView) findViewById(R.id.image);
//        ImageHelper.getInstance().display(imageView,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513694778002&di=8bab30eda49f73df8d81dd37a3777ec7&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F29381f30e924b899deb0d7ea64061d950b7bf650.jpg");
        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                <intent-filter>
//                <action android:name="android.intent.action.VIEW" />
//
//                <category android:name="android.intent.category.DEFAULT" />
//                <category android:name="android.intent.category.BROWSABLE" />
//
//                <data android:scheme="@string/url_open_scheme" />
//            </intent-filter>
                Intent intent = new Intent();
                //ComponentName name = new ComponentName("com.netease.xyqcbg.debug","com.netease.xyqcbg.activities.LoadingActivity");
                intent.setData(Uri.parse("netease-xyqcbg://show_equip/?server_id=496&ordersn=89_1516626719_89000030&view_loc=link_copy&equip_refer=333&activity_promo_spreader=yang92sell@163.com|b83c00d7e7afed4e6ab7d196c47c5057"));
                //intent.setComponent(name);
//                IntentFilter filter = new IntentFilter();
//                filter.addAction("android.intent.action.VIEW");
//                filter.addCategory("android.intent.category.DEFAULT");
//                filter.addCategory("android.intent.category.BROWSABLE");

                startActivity(intent);

            }
        });
    }
}
