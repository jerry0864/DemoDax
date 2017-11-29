package com.dax.demo.circleview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    private static final String TAG = "dax_test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CircleProgressView circleProgressView = (CircleProgressView) findViewById(R.id.circleview);
        circleProgressView.setProgress(0.35f);
        //测试跨应用调用app
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                intent.setComponent(new ComponentName("com.dax.demo.android", "com.dax.demo.android.MainActivity"));
//                //intent.setPackage("com.dax.demo.android");
//                intent.addFlags(0x10200000);
//                startActivity(intent);
//            }
//        });
    }
}
