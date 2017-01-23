package com.example.sparsh23.laltern;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class Profile_Detail extends AppCompatActivity {

    String type;
    Button add;
    ExpandableHeightGridView listView;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    DBHelper dbHelper;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__detail);
        dbHelper = new DBHelper(getApplicationContext());

        listView = (ExpandableHeightGridView) findViewById(R.id.profile_detail_list);
        listView.setExpanded(true);
        listView.setNumColumns(1);

        Intent intent = getIntent();
        add =   (Button)findViewById(R.id.add_profile_detail);

        type = intent.getStringExtra("type");
        sessionManager = new SessionManager(getApplicationContext());

        //  data = (ArrayList<HashMap<String, String>>) intent.getSerializableExtra("data");



        if(type.equals("My Addresses"))
        {
            listView.setAdapter(new Address_ExpandableList_Adapter(dbHelper.GetAddresses(),getApplicationContext()));

        }
        if(type.equals("My Requests"))
        {
            listView.setAdapter(new RequestExpandableAdapter(dbHelper.GetOrders(sessionManager.getUserDetails().get("uid")),getApplicationContext()) );
        }

         add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(type.equals("My Addresses"))
                {
  //                  listView.setAdapter(new Address_ExpandableList_Adapter(dbHelper.GetAddresses(),getApplicationContext()));
                    startActivity(new Intent(Profile_Detail.this,Add_New_Address.class));


                }
                if(type.equals("My Requests"))
                {
                    startActivity(new Intent(Profile_Detail.this,Submit_Request_Random.class));

//                    listView.setAdapter(new RequestExpandableAdapter(dbHelper.GetOrders(sessionManager.getUserDetails().get("uid")),getApplicationContext()) );
                }


            }
        });







    }
}
