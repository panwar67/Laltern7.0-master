package com.example.sparsh23.laltern;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
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
        View divier;
        rowView = inflater.inflate(R.layout.list_header, null);

        divier = rowView.findViewById(R.id.dividerview);

        TextView category = (TextView)rowView.findViewById(R.id.list_header_text);
        ImageView iconheader = (ImageView)rowView.findViewById(R.id.list_header_icon);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.list_header_icon2);
        iconheader.setMaxWidth(24);
        iconheader.setMaxHeight(24);
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "Roboto-Regular.ttf");
        category.setTypeface(tf);

        category.setText(headers.get(groupPosition));
        if(headers.get(groupPosition).equals("Home"))
        {

            iconheader.setImageResource(R.drawable.vector_drawable_home);

            divier.setBackgroundResource(R.color.maintheme);



        }

        if (headers.get(groupPosition).equals("Chat")){



        }
        if (headers.get(groupPosition).equals("Contact us"))
        {

            iconheader.setImageResource(R.drawable.vector_drawable_contact);

           // divier.setBackgroundResource(R.color.red);
        }
        if(headers.get(groupPosition).equals("Policies"))
        {
            iconheader.setImageResource(R.drawable.vector_drawable_policy);

          //  imageView.setImageResource(R.drawable.vector_drawable_plus);

           // divier.setBackgroundResource(R.color.red);
        }
        if (headers.get(groupPosition).equals("About us")){
            iconheader.setImageResource(R.drawable.vector_drawable_check);

           // divier.setBackgroundResource(R.color.red);
        }
        if (headers.get(groupPosition).equals("Shop From Us")){
            iconheader.setImageResource(R.color.maintheme);
        }
        if(headers.get(groupPosition).equals("Jewellery"))

        {


            iconheader.setImageResource(R.drawable.vector_drawable_jewel__);

            imageView.setImageResource(R.drawable.vector_drawable_plus);
        }

        if(headers.get(groupPosition).equals("Accessories"))
        {
            iconheader.setImageResource(R.drawable.vector_drawable_access);

            imageView.setImageResource(R.drawable.vector_drawable_plus);
        }

        if(headers.get(groupPosition).equals("Sarees"))
        {
            iconheader.setImageResource(R.drawable.vector_drawable_saree_);

            imageView.setImageResource(R.drawable.vector_drawable_plus);
        }

        if(headers.get(groupPosition).equals("Apparel"))
        {
            iconheader.setImageResource(R.drawable.vector_drawable_apparelapparel);

            imageView.setImageResource(R.drawable.vector_drawable_plus);
        }

        if(headers.get(groupPosition).equals("Home Textile"))
        {
            iconheader.setImageResource(R.drawable.vector_drawable_home_tex);

            imageView.setImageResource(R.drawable.vector_drawable_plus);
        }

        if(headers.get(groupPosition).equals("Home Decor"))
        {
            iconheader.setImageResource(R.drawable.vector_drawable_home_decor);

            imageView.setImageResource(R.drawable.vector_drawable_plus);
        }

        if(headers.get(groupPosition).equals("Paintings"))
        {
            iconheader.setImageResource(R.drawable.vector_drawable_painting);

            imageView.setImageResource(R.drawable.vector_drawable_plus);
        }

        if(headers.get(groupPosition).equals("Others"))
        {
            iconheader.setImageResource(R.drawable.vector_drawable_others);

            imageView.setImageResource(R.drawable.vector_drawable_plus);

           // divier.setBackgroundResource(R.color.maintheme);
        }


        if(headers.get(groupPosition).equals("Miscellaneous"))
        {
            iconheader.setImageResource(R.drawable.ic_style_black_24dp);

            imageView.setImageResource(R.drawable.vector_drawable_plus);

            divier.setBackgroundResource(R.color.maintheme);
        }






        return rowView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        View rowView;
        rowView = inflater.inflate(R.layout.list_child, null);
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "Montserrat-Regular.otf");

        TextView category = (TextView)rowView.findViewById(R.id.list_child_text);
        category.setTypeface(tf);
       // ImageView iconchild = (ImageView)rowView.findViewById(R.id.list_child_icon);
        category.setText("    "+child.get(headers.get(groupPosition)).get(childPosition));
        //iconchild.setImageResource(R.drawable.chakra);

        return rowView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
