package com.example.sparsh23.laltern;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Panwar on 23/10/16.
 */
public class Available_Sizes_Adapter extends BaseAdapter {

    private static LayoutInflater inflater=null;
    Context cont;
    ArrayList<String> result;

    public Available_Sizes_Adapter(Context context, ArrayList<String> data)
    {
        cont=context;
        result = data;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int i) {
        return result.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder
    {
        TextView option;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.sizes_avail, null);
        holder.option = (TextView)rowView.findViewById(R.id.avail_sizes_text);
        holder.option.setText(result.get(i));


        return rowView;
    }



}
