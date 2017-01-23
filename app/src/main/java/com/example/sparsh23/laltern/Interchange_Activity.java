package com.example.sparsh23.laltern;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;

public class Interchange_Activity extends AppCompatActivity {

    HashMap<String,String> map = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_interchange_);

        Intent intent = getIntent();
        map = (HashMap<String, String>) intent.getSerializableExtra("promap");
        Intent intent1 = new Intent(Interchange_Activity.this,ProductView.class);
        intent1.putExtra("promap",map);
        startActivity(intent1);
        finish();

    }
}
