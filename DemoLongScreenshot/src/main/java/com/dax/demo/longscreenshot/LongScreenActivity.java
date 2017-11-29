package com.dax.demo.longscreenshot;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class LongScreenActivity extends AppCompatActivity {
    //https://github.com/Piasy/BigImageViewer
    //https://github.com/davemorrissey/subsampling-scale-image-view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_screen);


        SubsamplingScaleImageView imageView  = (SubsamplingScaleImageView) findViewById(R.id.image);
        //imageView.setImage(ImageSource.asset("long1.jpg"),new ImageViewState(0.5f,new PointF(0,0),0));
        //imageView.setImage(ImageSource.asset("long1.jpg"));
        imageView.setScaleAndCenter(1f,new PointF(0,0));
//        imageView.setMinScale(1.5f);
//        imageView.setMinimumWidth(400);
//        imageView.setMinimumDpi(10);

        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
    }
}
