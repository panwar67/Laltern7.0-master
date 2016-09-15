package com.example.sparsh23.laltern;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sparsh23 on 06/08/16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    ArrayList<String> headers = new ArrayList<String>();
    HashMap<String, List<String>> child = new HashMap<String, List<String>>();
    private static LayoutInflater inflater=null;
    Context context;


    public ExpandableListAdapter(HashMap<String, List<String>> Child, ArrayList<String> Headers, Context contxt) {


        context = contxt;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        child = Child;
        headers = Headers;


    }

    @Override
    public int getGroupCount() {


        return headers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        int n = child.get(headers.get(groupPosition)).size();

        return child.get(headers.get(groupPosition)).size();

    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return this.headers.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return child.get(headers.get(groupPosition)).get(childPosition);
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

        TextView category = (TextView)rowView.findViewById(R.id.list_header_text);
        ImageView iconheader = (ImageView)rowView.findViewById(R.id.list_header_icon);

        category.setText(headers.get(groupPosition));
        if(headers.get(groupPosition).equals("Home"))
        {

            iconheader.setImageResource(R.drawable.home);


        }

        if (headers.get(groupPosition).equals("Chat")){

            iconheader.setImageResource(R.drawable.chat);
        }
        if (headers.get(groupPosition).equals("Contact Us"))
        {

            iconheader.setImageResource(R.drawable.contactusnav);
        }
        if(headers.get(groupPosition).equals("Policies"))
        {
            iconheader.setImageResource(R.drawable.policies);
        }
        if (headers.get(groupPosition).equals("About Us")){
            iconheader.setImageResource(R.drawable.info);
        }
        if (headers.get(groupPosition).equals("Shop From Us")){
            iconheader.setImageResource(R.drawable.shoppingicon);
        }
        if(headers.get(groupPosition).equals("jewellery"))
        {
            iconheader.setImageResource(R.drawable.expand);
        }

        if(headers.get(groupPosition).equals("Accessories"))
        {
            iconheader.setImageResource(R.drawable.expand);
        }

        if(headers.get(groupPosition).equals("Sarees"))
        {
            iconheader.setImageResource(R.drawable.expand);
        }

        if(headers.get(groupPosition).equals("Apparel"))
        {
            iconheader.setImageResource(R.drawable.expand);
        }

        if(headers.get(groupPosition).equals("Home Textile"))
        {
            iconheader.setImageResource(R.drawable.expand);
        }

        if(headers.get(groupPosition).equals("Home Decor"))
        {
            iconheader.setImageResource(R.drawable.expand);
        }

        if(headers.get(groupPosition).equals("Paintings"))
        {
            iconheader.setImageResource(R.drawable.expand);
        }

        if(headers.get(groupPosition).equals("Others"))
        {
            iconheader.setImageResource(R.drawable.expand);
        }






        return rowView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        View rowView;
        rowView = inflater.inflate(R.layout.list_child, null);

        TextView category = (TextView)rowView.findViewById(R.id.list_child_text);
        ImageView iconchild = (ImageView)rowView.findViewById(R.id.list_child_icon);
        category.setText(child.get(headers.get(groupPosition)).get(childPosition));
        iconchild.setImageResource(R.drawable.child);

        return rowView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
