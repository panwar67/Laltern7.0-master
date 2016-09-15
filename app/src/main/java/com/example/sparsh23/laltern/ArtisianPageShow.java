package com.example.sparsh23.laltern;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;

public class ArtisianPageShow extends AppCompatActivity {

    HashMap<String,String> data = new HashMap<String, String>();
    TextView names, tob, state, craft, awards;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artisian_page_show);

        back = (ImageView)findViewById(R.id.backart);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        names = (TextView)findViewById(R.id.artname);
        tob = (TextView)findViewById(R.id.arttob);
        state = (TextView)findViewById(R.id.artstate);
        craft = (TextView)findViewById(R.id.artcarft);
        awards = (TextView)findViewById(R.id.artaward);
        SliderLayout sliderShow = (SliderLayout) findViewById(R.id.slider);


        Intent intent = getIntent();
        data = (HashMap<String,String>)intent.getSerializableExtra("artmap");

        int noimg = Integer.parseInt(data.get("noimg"));


        names.setText("Name : "+data.get("name"));
        tob.setText("Type Of Buisiness "+data.get("tob"));
        state.setText("State : "+data.get("state"));
        craft.setText(data.get("craft"));
        awards.setText(data.get("awards"));






        Log.d("No Images in art", ""+noimg);




        if(noimg>0){

            sliderShow.addSlider(new TextSliderView(this).image( "http://www.whydoweplay.com/lalten/Images/"+data.get("artuid")+".jpg"));


            for(int i=1;i<noimg;i++){


                String n =  "http://www.whydoweplay.com/lalten/Images/"+data.get("artuid")+"_"+i+".jpg";

                Log.d("path", n);

                sliderShow.addSlider(new TextSliderView(this).image(n));


            }


        }



    }

}
