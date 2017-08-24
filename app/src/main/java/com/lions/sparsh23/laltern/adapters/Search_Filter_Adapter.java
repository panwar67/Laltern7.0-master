package com.lions.sparsh23.laltern.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.lions.sparsh23.laltern.R;

import java.util.ArrayList;

/**
 * Created by Panwar on 21/10/16.
 */
public class Search_Filter_Adapter extends BaseAdapter {

    private static LayoutInflater inflater=null;
    Context cont;
    ArrayList<String> result;

    public Search_Filter_Adapter(Context context, ArrayList<String> data)
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

        CheckBox checkBox;
        TextView option;
        CheckedTextView checkedTextView;


    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.filter_checkbox_item, null);
        holder.checkedTextView = (CheckedTextView)rowView.findViewById(R.id.filter_item_textchecked);
        holder.checkedTextView.setText(result.get(i));


        return rowView;
    }




    }

