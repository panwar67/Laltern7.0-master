package com.example.sparsh23.laltern;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Panwar on 30/01/17.
 */
public class Order_Adapter extends BaseAdapter {

    String headers;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    Context context;
    ImageLoader imageLoader;
    DisplayImageOptions options;


    public Order_Adapter(ArrayList<HashMap<String,String>> Child,  Context contxt) {
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
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        Holder holder=new Holder();
        View view1 = inflater.inflate(R.layout.order_item,null);
        // Holder holder = new Holder();
        holder.orderid = (TextView)view1.findViewById(R.id.orderid_adapter);
        holder.date = (TextView)view1.findViewById(R.id.date_adapter);
        holder.price = (TextView)view1.findViewById(R.id.price_adapter);
        holder.status = (TextView)view1.findViewById(R.id.status_order);
        holder.orderid.setText(data.get(i).get("orderid"));
        holder.date.setText(data.get(i).get("date"));
        Log.d("inside_adapter_order",""+data.get(i).get("date"));
        holder.price.setText(data.get(i).get("price"));
        holder.status.setText(data.get(i).get("status"));
        return view1;

    }

    class  Holder
    {
        TextView orderid, date, price, status;

    }


    /*@Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }*/
}
