package com.dax.demo.tinker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showToast(View view){
        show("toast from base apk");
        //show("toast from patch");
    }

    private void show(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
        Log.d("dax",text);
    }
}
