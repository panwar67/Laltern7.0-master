package com.example.sparsh23.laltern;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Panwar on 19/10/16.
 */
public class Filter_Checkbox_Adapter extends BaseAdapter {

    Context cont;
    ArrayList<HashMap<String,String>> result;
    private static LayoutInflater inflater=null;

    public Filter_Checkbox_Adapter(Context context, ArrayList<HashMap<String,String>> data)
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
        holder.checkBox = (CheckBox)rowView.findViewById(R.id.filter_item_checkbox);
        holder.option = (TextView)rowView.findViewById(R.id.filter_item_text);
        holder.checkedTextView = (CheckedTextView)rowView.findViewById(R.id.filter_item_textchecked);
        holder.option.setText(result.get(i).toString());
        holder.checkedTextView.setText(result.get(i).get("tag").toString());

        holder.checkedTextView.setChecked(Boolean.parseBoolean(result.get(i).get("status")));

        holder.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked())
                {
                    holder.checkBox.setChecked(false);
                }
                else
                    holder.checkBox.setChecked(true);
            }
        });
        return rowView;
    }
}
