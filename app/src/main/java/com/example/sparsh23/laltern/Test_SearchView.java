package com.example.sparsh23.laltern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.HashMap;

public class Test_SearchView extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    DBHelper dbHelper;
    ImageView back;
    private Tracker mTracker;

    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test__search_view);
        listView    =   (ListView)findViewById(R.id.test_search_list);
        searchView = (SearchView)findViewById(R.id.test_search);
       // searchView.setFocusable(true);
       // searchView.onActionViewExpanded();
        showInputMethod(searchView);
        dbHelper = new DBHelper(getApplicationContext());
        data =  dbHelper.Get_Search_Tags();
        Log.d("search_adap_size",""+data.size());
        final Test_Search_Adapter test_search_adapter = new Test_Search_Adapter(getApplicationContext(),data);
        listView.setAdapter(new Test_Search_Adapter(getApplicationContext(),data));
        listView.setVisibility(View.GONE);

        AnalyticsApplication application = (AnalyticsApplication)getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.setScreenName("Search_init");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hideSoftKeyboard(Test_SearchView.this);
                Log.d("test_search_clicked",listView.getItemAtPosition(i).toString());
                HashMap<String,String> aux = new HashMap<String, String>();
                HashMap<String,String> map = new HashMap<String, String>();
                map = (HashMap<String, String>)listView.getItemAtPosition(i);

                aux.put("col",map.get("type"));
                aux.put("value",map.get("tag"));
                HashMap<String,ArrayList<HashMap<String,String>>> filterdata = new HashMap<String, ArrayList<HashMap<String, String>>>();
                filterdata.put("size",dbHelper.GetSizes_Random(map.get("type"),map.get("tag")));
                filterdata.put("color",dbHelper.GetColor_Random(map.get("type"),map.get("tag")));
                filterdata.put("protype",dbHelper.GetProType_Random(map.get("type"),map.get("tag")));

                data = dbHelper.GetProductsFromSearch_test(map.get("type"),map.get("tag"));
                Bundle bundle1 = new Bundle();

                bundle1.putSerializable("data",data);
                bundle1.putSerializable("filter",filterdata);
                bundle1.putSerializable("selection",aux);

               // ItemFragment newhom =  ItemFragment.newInstance(1);

                Intent intent = new Intent(Test_SearchView.this,NavigationMenu.class);


                intent.putExtra("datas_search",bundle1);
                startActivity(intent);
                finish();
               // startActivity(intent);


            }
        });
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);

        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search craft, artisian and products");

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
                data = dbHelper.getdatafromquery(query);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("data",data);
                Intent intent = new Intent(Test_SearchView.this,NavigationMenu.class);
                intent.putExtra("datas",bundle1);
                startActivity(intent);
                finish();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                listView.setVisibility(View.VISIBLE);


                Log.d("chutiya_nhi_kata",""+newText);
              /*  if (TextUtils.isEmpty(newText)) {
                    listView.clearTextFilter();
                } else {
                    listView.setFilterText(newText);
                    test_search_adapter.getFilter().filter(newText);
                }*/
                if(!newText.isEmpty())
                {
                    ArrayList<HashMap<String,String>> map = new ArrayList<HashMap<String, String>>();
                    for (int i=0;i<data.size();i++)
                    {
                        if(data.get(i).get("tag").toLowerCase().contains(newText.toLowerCase()))
                        {
                            map.add(data.get(i));
                        }
                    }
                        listView.setAdapter(new Test_Search_Adapter(getApplicationContext(),map));
                }else
                {
                    ArrayList<HashMap<String,String>> map = new ArrayList<HashMap<String, String>>();
                    listView.setAdapter(new Test_Search_Adapter(getApplicationContext(),map));
                }
                return true;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        //showInputMethod(view);
                    }
                });

        back    =   (ImageView)findViewById(R.id.test_search_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    private void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Test_SearchView.this,NavigationMenu.class));
        finish();
    }
}
