package com.lions.sparsh23.laltern.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lions.sparsh23.laltern.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Panwar on 04/11/16.
 */
public class Address_ExpandableList_Adapter extends BaseAdapter {
    String headers;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    Context context;
    ImageLoader imageLoader;
    DisplayImageOptions options;


    public Address_ExpandableList_Adapter(ArrayList<HashMap<String,String>> Child,  Context contxt) {
        context = contxt;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = Child;
       // headers = Headers;

    }




    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder=new Holder();
        View view1 = inflater.inflate(R.layout.checkout_addr_item,null);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "SourceSansPro-Regular.otf");
       // Holder holder = new Holder();
        holder.title = (TextView)view1.findViewById(R.id.addr_title);
        holder.addr = (TextView)view1.findViewById(R.id.addr);
        holder.area = (TextView)view1.findViewById(R.id.area);
        holder.city = (TextView)view1.findViewById(R.id.city);
        holder.dist = (TextView)view1.findViewById(R.id.dist);
        holder.state = (TextView)view1.findViewById(R.id.addr_state);
        holder.pin = (TextView)view1.findViewById(R.id.addr_pin);
        holder.cont = (TextView)view1.findViewById(R.id.addr_cont);

        holder.title.setTypeface(tf);
        holder.addr.setTypeface(tf);
        holder.area.setTypeface(tf);
        holder.city.setTypeface(tf);
        holder.dist.setTypeface(tf);
        holder.state.setTypeface(tf);
        holder.pin.setTypeface(tf);

        holder.title.setText(data.get(i).get("title"));
        holder.addr.setText(data.get(i).get("addr"));
        holder.area.setText(data.get(i).get("area"));
        holder.dist.setText(data.get(i).get("dist"));
        holder.state.setText(data.get(i).get("state"));
        holder.state.setText(data.get(i).get("state"));
        holder.city.setText(data.get(i).get("city"));
        holder.cont.setText(data.get(i).get("cont"));
        return view1;



    }

    class  Holder
    {
        TextView title, addr, area, dist, state, cont, pin,city;

    }

    /*@Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //Holder holder=new Holder();
        View view1 = inflater.inflate(R.layout.checkout_addr_item,null);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "Raleway-Regular.ttf");
        Holder holder = new Holder();
        holder.title = (TextView)view1.findViewById(R.id.addr_title);
        holder.addr = (TextView)view1.findViewById(R.id.addr);
        holder.area = (TextView)view1.findViewById(R.id.area);
        holder.city = (TextView)view1.findViewById(R.id.city);
        holder.dist = (TextView)view1.findViewById(R.id.dist);
        holder.state = (TextView)view1.findViewById(R.id.addr_state);
        holder.pin = (TextView)view1.findViewById(R.id.addr_pin);

        holder.title.setTypeface(tf);
        holder.addr.setTypeface(tf);
        holder.area.setTypeface(tf);
        holder.city.setTypeface(tf);
        holder.dist.setTypeface(tf);
        holder.state.setTypeface(tf);
        holder.pin.setTypeface(tf);

        holder.title.setText(data.get(childPosition).get("title"));
        holder.addr.setText(data.get(childPosition).get("addr"));
        holder.area.setText(data.get(childPosition).get("area"));
        holder.dist.setText(data.get(childPosition).get("dist"));
        holder.state.setText(data.get(childPosition).get("state"));
        holder.state.setText(data.get(childPosition).get("pin"));
        holder.city.setText(data.get(childPosition).get("city"));
        return view1;
    }*/

    /*@Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }*/
}

