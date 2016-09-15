package com.example.sparsh23.laltern;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class YourEnquiries extends AppCompatActivity {

    ArrayList<HashMap<String,String>> map = new ArrayList<HashMap<String, String>>();
    ListView listView;
    DBHelper dbHelper;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_enquiries);

        sessionManager = new SessionManager(getApplicationContext());
        dbHelper = new DBHelper(getApplicationContext());
        map = dbHelper.GetOrders(sessionManager.getUserDetails().get("uid"));
        listView = (ListView)findViewById(R.id.orderslist);

        listView.setAdapter(new CustomOrderAdapter(this,map));











    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
