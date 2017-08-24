package com.lions.sparsh23.laltern.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lions.sparsh23.laltern.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Panwar on 04/11/16.
 */
public class Checkout_Addr_Adapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    LayoutInflater layoutInflater = null;

    public Checkout_Addr_Adapter(Context cont, ArrayList<HashMap<String,String>> result)
    {
        context =cont;
        data = result;
        layoutInflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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


    class  Holder
    {
        TextView title, addr, area, dist, state, cont, pin,city;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View view1 = layoutInflater.inflate(R.layout.checkout_addr_item,null);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "HelveticaNeueLt.ttf");
        Holder holder = new Holder();
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
        holder.cont.setTypeface(tf);
        holder.title.setText(data.get(i).get("title"));
        holder.addr.setText(data.get(i).get("addr"));
        holder.area.setText(data.get(i).get("area"));
        holder.dist.setText(data.get(i).get("dist"));
        holder.state.setText(data.get(i).get("state")+" - ");
        holder.pin.setText(data.get(i).get("pin"));
        holder.city.setText(data.get(i).get("city"));
        holder.cont.setText("+91-"+data.get(i).get("cont"));
        return view1;
    }
}
