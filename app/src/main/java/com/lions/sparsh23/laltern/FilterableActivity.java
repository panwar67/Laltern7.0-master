package com.lions.sparsh23.laltern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.lions.sparsh23.laltern.adapters.Search_Filter_Adapter;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterableActivity extends AppCompatActivity {


    HashMap<String,String> selections = new HashMap<String, String>();
    ArrayList<String> selected_craft = new ArrayList<String>();
    ArrayList<String> selected_pro_type  =    new ArrayList<String>();
    ToggleButton price, artist, priceL500, priceL1500, priceA2000, priceL5000;
    Spinner craftspin, protypespin;
    ArrayList<HashMap<String,ArrayList<String>>> filterdata = new ArrayList<HashMap<String, ArrayList<String>>>();
    ImageView back;
    Button button;
    DBHelper dbHelper;
    ArrayList<String> catitems = new ArrayList<String>();
    ArrayList<HashMap<String,String>> datafinal = new ArrayList<HashMap<String, String>>();
    ExpandableHeightGridView craftlist, protypelist;
    //private Tracker mTracker;


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

        //spinnerdata = dbHelper.GetSearchFilter();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(selected_craft.isEmpty()&&selected_pro_type.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Select from both",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(FilterableActivity.this,NavigationMenu.class);
                    datafinal = dbHelper.Get_Search_Home_Filter_Products(selected_craft,selected_pro_type);
                    bundle.putSerializable("data",datafinal);
                    intent.putExtra("datas",bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });

        catitems = dbHelper.Craft_Search_Page();

        craftlist.setAdapter(new Search_Filter_Adapter(getApplicationContext(),catitems));
        craftlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                CheckedTextView check = (CheckedTextView)view.findViewById(R.id.filter_item_textchecked);
                if(check.isChecked())
                {
                    check.setChecked(false);
                    selected_craft.remove(craftlist.getItemAtPosition(i));
                    Log.d("change_size",""+selected_craft.size());
                    Log.d("deselected_item",""+craftlist.getItemAtPosition(i));
                }
                else {
                    check.setChecked(true);
                    selected_craft.add(craftlist.getItemAtPosition(i).toString());
                    Log.d("change_size",""+selected_craft.size());
                    Log.d("selected_item",""+craftlist.getItemAtPosition(i));
                }

            }
        });



        protypelist.setAdapter(new Search_Filter_Adapter(getApplicationContext(),dbHelper.Pro_Type_Search_Page()));

        protypelist.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView checkedTextView = (CheckedTextView)view.findViewById(R.id.filter_item_textchecked);
                checkedTextView.toggle();
                Log.d("protype_checkbox_filter",""+checkedTextView.isChecked());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FilterableActivity.this,NavigationMenu.class));
    }
}
