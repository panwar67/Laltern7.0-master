package com.lions.sparsh23.laltern.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lions.sparsh23.laltern.R;

import java.util.HashMap;

/**
 * Created by Panwar on 8/27/2016.
 */

public class ExpandableArtistAdapter extends BaseExpandableListAdapter {
    String headers = new String();
    HashMap<String, String> child = new HashMap<>();
    private static LayoutInflater inflater=null;
    Context context;


    public ExpandableArtistAdapter(HashMap<String, String> Child, String Headers, Context contxt) {


        context = contxt;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        child = Child;
        headers = Headers;


    }

    @Override
    public int getGroupCount() {


        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        int n = child.get(headers.get(groupPosition)).size();

        return 1;

    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return headers;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return child;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View rowView;
        rowView = inflater.inflate(R.layout.list_header, null);






        return rowView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        View rowView;
        rowView = inflater.inflate(R.layout.expandableartist, null);

        TextView category = (TextView)rowView.findViewById(R.id.list_header_text);
        RatingBar  authen = (RatingBar)rowView.findViewById(R.id.authenrate);
        RatingBar prices = (RatingBar)rowView.findViewById(R.id.pricerate);
        RatingBar overall = (RatingBar)rowView.findViewById(R.id.overallrating);
        authen.setMax(5);
        prices.setMax(5);
        overall.setMax(5);




        authen.setRating(Float.parseFloat(child.get("authentic")));
        prices.setRating(Float.parseFloat(child.get("price")));
        overall.setRating(Float.parseFloat(child.get("rating")));
        category.setText(child.get("name").toString());


        return rowView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
