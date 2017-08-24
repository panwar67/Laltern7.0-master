package com.lions.sparsh23.laltern;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.lions.sparsh23.laltern.adapters.CustomCursorAdapter;
import com.lions.sparsh23.laltern.dummy.DummyContent;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchableActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ItemFragment.OnListFragmentInteractionListener {

    ListView mLVCountries;
    SimpleCursorAdapter mCursorAdapter;

    CustomCursorAdapter customCursorAdapter;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_searchable);

        //SearchView searchView = (SearchView)findViewById(R.id.idsearch);
        //searchView.requestFocusFromTouch();
        //searchView.setFocusable(true);


        dbHelper = new DBHelper(getApplicationContext());


        /*searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                Toast.makeText(getApplicationContext()," "+position,Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                return false;
            }
        });
        */





        // Getting reference to Country List

       // mLVCountries = (ListView)findViewById(R.id.lv_countries);

        // Setting item click listener
       // mLVCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // Intent countryIntent = new Intent(getApplicationContext(), CountryActivity.class);

                // Creating a uri to fetch country details corresponding to selected listview item
               // Uri data = Uri.withAppendedPath(CountryContentProvider.CONTENT_URI, String.valueOf(id));


        //        Log.d("item selected",String.valueOf(id));
          //      Toast.makeText(getApplicationContext(),""+String.valueOf(id),Toast.LENGTH_SHORT).show();
                // Setting uri to the data on the intent
                //countryIntent.setData(data);

                // Open the activity
                //startActivity(countryIntent);
            //}
        //});


       // mCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.gridsubitem.gridsubitem, null, new String[] { SearchManager.SUGGEST_COLUMN_TEXT_1,SearchManager.SUGGEST_COLUMN_TEXT_2}, new int[] { R.id.texts1, R.id.texts2}, 0);


        //customCursorAdapter = new CustomCursorAdapter(getBaseContext(), R.gridsubitem.gridsubitem, null, new String[] { "TAG","SUGGEST"}, new int[] { R.id.texts1, R.id.texts2}, 0);
        // Setting the cursor adapter for the country listview
        //mLVCountries.setAdapter(customCursorAdapter);







        // Defining CursorAdapter for the ListView

        // Getting the intent that invoked this activity
        Intent intent = getIntent();

        // If this activity is invoked by selecting an item from Suggestion of Search dialog or
        // from listview of SearchActivity
        if(intent.getAction().equals(Intent.ACTION_VIEW)){



            Log.d("when selected", intent.getData().toString());


            Bundle bundle = new Bundle();
            ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
            data  = dbHelper.GetProductsFromSearch(intent.getData().getLastPathSegment());

            bundle.putSerializable("data",data);
            Intent intent1 = new Intent(SearchableActivity.this,NavigationMenu.class);
            intent1.putExtra("datas",bundle);
            //countryIntent.setData(intent.getData());
           startActivity(intent1);
            finish();
        }else if(intent.getAction().equals(Intent.ACTION_SEARCH)){ // If this activity is invoked, when user presses "Go" in the Keyboard of Search Dialog
            String query = intent.getStringExtra(SearchManager.QUERY);

            ArrayList<HashMap<String,String>> map = new ArrayList<HashMap<String, String>>();
            map = dbHelper.getdatafromquery(query);
            Bundle bundle = new Bundle();


            Intent intent1 = new Intent(SearchableActivity.this,NavigationMenu.class);

            bundle.putSerializable("data",map);

            intent1.putExtra("datas",bundle);
            startActivity(intent1);

        }

    }

    private void doSearch(String query){
        Bundle data = new Bundle();
        data.putString("query", query);

        // Invoking onCreateLoader() in non-ui thread
       // getSupportLoaderManager().initLoader(1, data, this);
    }


    /** This method is invoked by initLoader() */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle data) {
        Uri uri = CountryContentProvider.CONTENT_URI;
        Log.d("querydebug",""+data.getString("query"));
        return new CursorLoader(getBaseContext(), uri, null, null , new String[]{data.getString("query")}, null);
    }

    /** This method is executed in ui thread, after onCreateLoader() */
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
        customCursorAdapter.swapCursor(c);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}