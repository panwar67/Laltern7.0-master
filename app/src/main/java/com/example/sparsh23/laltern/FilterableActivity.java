package com.example.sparsh23.laltern;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterableActivity extends AppCompatActivity {


    HashMap<String,String> selections = new HashMap<String, String>();
    ToggleButton price, artist, priceL500, priceL1500, priceA2000, priceL5000;
    Spinner craftspin, protypespin;
    ArrayList<HashMap<String,ArrayList<String>>> filterdata = new ArrayList<HashMap<String, ArrayList<String>>>();
    ImageView back;
    Button button;
    DBHelper dbHelper;
    ArrayList<String> catitems = new ArrayList<String>();
    ArrayList<HashMap<String,String>> datafinal = new ArrayList<HashMap<String, String>>();

    ArrayList< HashMap<String,ArrayList<String>>> spinnerdata = new ArrayList<HashMap<String, ArrayList<String>>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(getApplicationContext());

        button = (Button)findViewById(R.id.filterhomeapply);
        button.setVisibility(View.GONE);
        price = (ToggleButton)findViewById(R.id.productrate);
        artist = (ToggleButton)findViewById(R.id.artistrate);
        priceL500 = (ToggleButton)findViewById(R.id.below500);
        priceL1500 = (ToggleButton)findViewById(R.id.below1500);
        priceA2000 = (ToggleButton)findViewById(R.id.above2000);
        priceL5000 = (ToggleButton)findViewById(R.id.below5000);
        back = (ImageView)findViewById(R.id.backfil);
        craftspin = (Spinner)findViewById(R.id.craftfilter);
        protypespin = (Spinner)findViewById(R.id.typefilter);

        spinnerdata = dbHelper.GetSearchFilter();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                Intent intent = new Intent(FilterableActivity.this,NavigationMenu.class);
                selections.put("craft",craftspin.getSelectedItem().toString());
                selections.put("protype",protypespin.getSelectedItem().toString());
                datafinal = dbHelper.GetSearchFilteredData(selections);
                bundle.putSerializable("data",datafinal);
                intent.putExtra("datas",bundle);
                startActivity(intent);
                finish();


            }
        });

        for (int i = 0;i<spinnerdata.size();i++)
        {
            catitems.add(String.valueOf(spinnerdata.get(i).entrySet().iterator().next().getKey()));
            Log.d("craft spin item",""+spinnerdata.get(i).entrySet().iterator().next().getKey());


        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,catitems);
        craftspin.setAdapter(arrayAdapter);
        craftspin.setSelection(0);
        craftspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                //ArrayList<String> subcatslist = new ArrayList<String>();
                for(int i = 0;i<spinnerdata.size();i++)
                {

                    Log.d("spinner size",""+spinnerdata.size());

                    if(spinnerdata.get(i).entrySet().iterator().next().getKey().equals(catitems.get(position)))
                    {


                        protypespin.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,spinnerdata.get(i).get(catitems.get(position))));
                        protypespin.setSelection(0);


                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });










        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(artist.isChecked()){



                    button.setVisibility(View.VISIBLE);


                    artist.setChecked(false);

                }
                if(price.isChecked())
                {
                    selections.put("order", "PRICE");
                }
            }
        });

        artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(price.isChecked()){
                    button.setVisibility(View.VISIBLE);


                    price.setChecked(false);

                }
                if (artist.isChecked())
                {
                    selections.put("order","PRICE");
                }


            }
        });

        priceL500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setVisibility(View.VISIBLE);
                if(priceL1500.isChecked())
                {



                    priceL1500.setChecked(false);
                }

                if(priceL5000.isChecked())
                {

                    priceL5000.setChecked(false);
                }
                if(priceA2000.isChecked())
                {

                    priceA2000.setChecked(false);
                }
                if(priceL500.isChecked())
                {
                    selections.put("less","500");

                }

            }
        });

        priceL1500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setVisibility(View.VISIBLE);
                if(priceL500.isChecked()){

                    priceL500.setChecked(false);
                }

                if(priceL5000.isChecked()){

                    priceL5000.setChecked(false);
                }
                if(priceA2000.isChecked()){

                    priceA2000.setChecked(false);
                }
                if(priceL1500.isChecked())
                {
                    selections.put("less","1500");
                }

            }
        });
        priceL5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setVisibility(View.VISIBLE);
                if(priceL1500.isChecked()){

                    priceL1500.setChecked(false);
                }

                if(priceL500.isChecked()){

                    priceL500.setChecked(false);
                }
                if(priceA2000.isChecked()){

                    priceA2000.setChecked(false);
                }
                if (priceL5000.isChecked())
                {
                    selections.put("less","5000");
                }

            }
        });
        priceA2000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setVisibility(View.VISIBLE);
                if(priceL1500.isChecked()){

                    priceL1500.setChecked(false);
                }

                if(priceL5000.isChecked()){

                    priceL5000.setChecked(false);
                }
                if(priceL500.isChecked()){

                    priceL500.setChecked(false);
                }
                if (priceA2000.isChecked())
                {
                    selections.put("less","2500");
                }

            }
        });


        if(!priceL5000.isChecked()&&!price.isChecked()&&!priceL500.isChecked()&&!priceL1500.isChecked()&&!priceA2000.isChecked()&&!artist.isChecked())
        {
            button.setVisibility(View.GONE);
        }







    }

}
