package com.example.sparsh23.laltern;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Profile extends AppCompatActivity {
    ImageView stream;
    ImageView search;
    ImageView upload;
    DBHelper dbHelper;

    HashMap<String,String> map = new HashMap<String, String>();
    HashMap<String,String> data = new HashMap<String, String>();
    TextView name, company, desig, tob, addr, cont, pan, email, webs, state, city;
    Button logout, orders;
    SessionManager sessionManager;
    ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sessionManager=new SessionManager(getApplicationContext());
        dbHelper = new DBHelper(getApplicationContext());
        expandableListView = (ExpandableListView)findViewById(R.id.requests);

        ArrayList<HashMap<String,String>> map = new ArrayList<HashMap<String, String>>();
        map = dbHelper.GetOrders(sessionManager.getUserDetails().get("uid"));
        Log.d("request_size",""+map.size());
        expandableListView.setAdapter(new RequestExpandableAdapter(map,"Requests",getApplicationContext()));
        name = (TextView)findViewById(R.id.nameprofile);
        cont = (TextView)findViewById(R.id.contactprofile);

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),"Raleway-SemiBold.ttf");
        name.setText(dbHelper.GetProfile(sessionManager.getUserDetails().get("uid")).get("name"));
        name.setTypeface(typeface);
        Typeface typeface1 = Typeface.createFromAsset(getApplicationContext().getAssets(),"Raleway-Regular.ttf");
        email = (TextView)findViewById(R.id.emailpro);
        email.setTypeface(typeface1);
        cont.setTypeface(typeface1);


        email.setText(sessionManager.getUserDetails().get("email"));

        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                sessionManager.logoutUser();
                return false;
            }
        });

        cont.setText(dbHelper.GetProfile(sessionManager.getUserDetails().get("uid")).get("cont"));






     /*   stream=(ImageView)findViewById(R.id.feed);
        stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Stream.class).putExtra("map",map));
                finish();
            }
        });
        upload=(ImageView)findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Upload.class).putExtra("map",map) );
                finish();
            }
        });
        search=(ImageView)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Search.class).putExtra("map",map));
                finish();
            }
        });

        */


      //  data  = dbHelper.GetProfile();










    }

}
