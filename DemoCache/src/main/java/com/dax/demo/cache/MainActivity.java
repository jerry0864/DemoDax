package com.dax.demo.cache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import util.ACache;

public class MainActivity extends AppCompatActivity {
    EditText filename,filedata;
    TextView textShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         filename  = (EditText) findViewById(R.id.edit_filename);
         filedata  = (EditText) findViewById(R.id.edit_filedata);
         textShow  = (TextView) findViewById(R.id.text_show);
    }

    public void writedata(View view){
        String name = filename.getText().toString().trim();
        String value = filedata.getText().toString().trim();
        ACache.get(this).put(name,value);
    }

    public void readdata(View view){
        String name = filename.getText().toString().trim();
        String value = ACache.get(this).getAsString(name);
        textShow.setText(value);
    }
}
