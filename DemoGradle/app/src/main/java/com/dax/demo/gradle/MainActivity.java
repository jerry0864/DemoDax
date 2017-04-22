package com.dax.demo.gradle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = (TextView) findViewById(R.id.text);
        boolean update = BuildConfig.AUTO_UPDATES;
        boolean log = BuildConfig.LOG_DEBUG;
        text.setText("update:"+update+"\n showlog:"+log);

    }
}
