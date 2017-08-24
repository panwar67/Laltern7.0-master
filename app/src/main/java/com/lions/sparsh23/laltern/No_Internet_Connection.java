package com.lions.sparsh23.laltern;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class No_Internet_Connection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no__internet__connection);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.retry_layout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(No_Internet_Connection.this,LoginActivity.class));
                finish();
            }
        });

    }
}
