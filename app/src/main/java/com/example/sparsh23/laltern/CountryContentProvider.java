package com.example.sparsh23.laltern;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Sparsh23 on 10/08/16.
 */public class CountryContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.sparsh23.laltern.CountryContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/SearchData" );


    private static final int SUGGESTIONS_COUNTRY = 1;
    private static final int SEARCH_COUNTRY = 2;
    private static final int GET_COUNTRY = 3;
    DBHelper dbHelper;

    UriMatcher mUriMatcher = buildUriMatcher();

    private UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Suggestion items of Search Dialog is provided by this uri
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY,SUGGESTIONS_COUNTRY);



        // This URI is invoked, when user presses "Go" in the Keyboard of Search Dialog
        // Listview items of SearchableActivity is provided by this uri
        // See android:searchSuggestIntentData="content://in.wptrafficanalyzer.searchdialogdemo.provider/countries" of searchable.xml
        uriMatcher.addURI(AUTHORITY, "SearchData", SEARCH_COUNTRY);

        // This URI is invoked, when user selects a suggestion from search dialog or an item from the listview
        // Country details for CountryActivity is provided by this uri
        // See, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID in CountryDB.java
        uriMatcher.addURI(AUTHORITY, "SearchData/#", GET_COUNTRY);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {

       dbHelper = new DBHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {


        Log.d("inside selection part2",""+uri.getLastPathSegment());
//        Log.d("inside selectionarg",""+selectionArgs[0]);


        Cursor c = dbHelper.getImageData(new String[]{uri.getLastPathSegment()});


        return c;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
