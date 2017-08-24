package com.lions.sparsh23.laltern.laltern;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

/**
 * Created by Panwar on 24/02/17.
 */
public class New_Image_Slider extends BaseSliderView {




    public New_Image_Slider(Context context) {
        super(context);
        setScaleType(ScaleType.CenterInside);
        //        setScaleType(ScaleType.Fit);
    }



    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(com.daimajia.slider.library.R.layout.render_type_default,null);
        ImageView target = (ImageView)v.findViewById(com.daimajia.slider.library.R.id.daimajia_slider_image);
        //target.setScaleType(ImageView.ScaleType.FIT_CENTER);

        bindEventAndShow(v, target);
        return v;
    }
}
