package com.dax.demo.largeimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.dax.demo.largeimage.pinch_image_view.PinchImageView;
import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.InputStreamBitmapDecoderFactory;

import java.io.IOException;

//https://github.com/boycy815/PinchImageView
//https://github.com/davemorrissey/subsampling-scale-image-view
//https://github.com/LuckyJayce/LargeImage
public class MainActivity extends AppCompatActivity {
    SubsamplingScaleImageView subsamplingScaleImageView;
    LargeImageView largeImageView;
    PinchImageView pinchImageView;
    int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //showSubsampleScaleImageView();
        showLargeImageView();
        //showPinchImageView();
    }

    private void showPinchImageView() {
        pinchImageView = (PinchImageView) findViewById(R.id.pinch_image_view);
    }

    private void showLargeImageView() {
        largeImageView = (LargeImageView) findViewById(R.id.large_image_view);
        try {
            largeImageView.setImage(new InputStreamBitmapDecoderFactory(getAssets().open("images/"+i+".jpg")));
            largeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i++;
                    if(i<=5){
                        try {
                            largeImageView.setImage(new InputStreamBitmapDecoderFactory(getAssets().open("images/"+i+".jpg")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void showSubsampleScaleImageView() {
        subsamplingScaleImageView = (SubsamplingScaleImageView) findViewById(R.id.sub_sample_scale_image_view);
        subsamplingScaleImageView.setMinimumDpi(240);
        subsamplingScaleImageView.setImage(ImageSource.asset("images/"+i+".jpg"));
        subsamplingScaleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if(i<=5){
                    subsamplingScaleImageView.setImage(ImageSource.asset("images/"+i+".jpg"));
                }
            }
        });
    }
}
