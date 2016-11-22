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
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    ExpandableHeightGridView craftlist, protypelist;

    ArrayList< HashMap<String,ArrayList<String>>> spinnerdata = new ArrayList<HashMap<String, ArrayList<String>>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(getApplicationContext());

        button = (Button)findViewById(R.id.filterhomeapply);

        craftlist = (ExpandableHeightGridView)findViewById(R.id.craftlist);
        craftlist.setExpanded(true);
        craftlist.setNumColumns(1);
        protypelist = (ExpandableHeightGridView)findViewById(R.id.protypelist);
        protypelist.setExpanded(true);
        protypelist.setNumColumns(1);
        protypelist.setChoiceMode(ExpandableHeightGridView.CHOICE_MODE_MULTIPLE);

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
            public void onClick(View v)
            {
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

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_style,catitems);


        craftlist.setAdapter(arrayAdapter);


        craftlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                for(int j = 0;j<spinnerdata.size();j++)
                {

                    Log.d("spinner size",""+spinnerdata.size());

                    if(spinnerdata.get(j).entrySet().iterator().next().getKey().equals(catitems.get(i)))
                    {


                        protypelist.setAdapter(new Search_Filter_Adapter(getApplicationContext(),spinnerdata.get(j).get(catitems.get(i))));
//                        protypespin.setAdapter(new ArrayAdapter(getApplicationContext(),R.layout.spinner_style,spinnerdata.get(i).get(catitems.get(position))));
                //        protypespin.setSelection(0);


                    }

                }


            }
        });


        craftlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //protypelist.setAdapter(new Filter_Checkbox_Adapter(getApplicationContext(),spinnerdata.get(i).get(catitems.get(i))));
               // protypespin.setSelection(0);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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


                        protypespin.setAdapter(new ArrayAdapter(getApplicationContext(),R.layout.spinner_style,spinnerdata.get(i).get(catitems.get(position))));
                        protypespin.setSelection(0);


                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        protypelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView checkedTextView = (CheckedTextView)view.findViewById(R.id.filter_item_textchecked);
                checkedTextView.toggle();
                Log.d("protype_checkbox_filter",""+checkedTextView.isChecked());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });










    }

}
