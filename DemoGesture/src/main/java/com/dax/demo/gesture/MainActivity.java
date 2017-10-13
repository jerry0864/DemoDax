package com.dax.demo.gesture;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    GestureLibrary library;
    GestureOverlayView gestureOverlayView;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.imageView);
        //final GestureLibrary library = GestureLibraries.fromRawResource(this, R.raw.about);
        gestureOverlayView = (GestureOverlayView) findViewById(R.id.gesture);
        gestureOverlayView.setGestureColor(Color.RED);
        gestureOverlayView.setGestureStrokeWidth(10f);
        library = GestureLibraries.fromFile("/sdcard/dax/about");
    }

    public void createGesture(View view){
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                library.addGesture("open",gesture);
                boolean result = library.save();
                showToast(result?"saveSuccess":"saveFail");
            }
        });
    }

    public void regonizeGesture(View view){
        boolean loadSuccess = library.load();
        showToast(loadSuccess?"loadSuccess":"loadFail");
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                List<Prediction> predictions = library.recognize(gesture);
                if (predictions != null && predictions.size() > 0) {
                    Prediction prediction = predictions.get(0);
                    Log.d("dax_test","prediction.score--> "+prediction.score);
                    Log.d("dax_test","1--> "+(prediction.score > 8.0));
                    Log.d("dax_test","2--> "+(prediction.score > 8.0d));
                    if (prediction.score > 8.0d) {
                        showToast("on");
                    } else {
                        showToast("off");
                    }
                }
                Bitmap bitmap = gesture.toBitmap(128, 128, 10, 0xffff0000);
                iv.setImageBitmap(bitmap);
            }
        });
    }

    private void showToast(String content){
        Toast.makeText(MainActivity.this,content,Toast.LENGTH_SHORT).show();
    }
}
