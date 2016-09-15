package com.example.sparsh23.laltern;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        Intent intent = getIntent();
        map = (HashMap<String, String>)intent.getSerializableExtra("map");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        orders = (Button)findViewById(R.id.orderscust);
        name=(TextView)findViewById(R.id.reg_name);
        company=(TextView)findViewById(R.id.reg_comp);
        desig=(TextView)findViewById(R.id.reg_desg);
        tob=(TextView)findViewById(R.id.reg_bustype);
        addr=(TextView)findViewById(R.id.reg_addr);
        cont=(TextView)findViewById(R.id.reg_cont);
        pan=(TextView)findViewById(R.id.reg_pan);
        email=(TextView)findViewById(R.id.reg_email);
        webs=(TextView)findViewById(R.id.reg_webs);
        state=(TextView)findViewById(R.id.reg_state);
        city=(TextView)findViewById(R.id.reg_city);
        logout=(Button)findViewById(R.id.logout);
        sessionManager=new SessionManager(getApplicationContext());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
                dbHelper.InitProfile();
                startActivity(new Intent(Profile.this,LoginActivity.class));
                finish();
            }
        });

        dbHelper = new DBHelper(getApplicationContext());





        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,YourEnquiries.class));
            }
        });

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

        webs.setText(data.get("web").toString());
        name.setText(data.get("name").toString());
        company.setText(data.get("comp").toString());
        desig.setText(data.get("design").toString());
        tob.setText(data.get("tob").toString());
        cont.setText(data.get("cont").toString());
        email.setText(data.get("email").toString());
        addr.setText(data.get("addr").toString());
        city.setText(data.get("city").toString());
        state.setText(data.get("state").toString());
        pan.setText(data.get("pan").toString());








    }

}
