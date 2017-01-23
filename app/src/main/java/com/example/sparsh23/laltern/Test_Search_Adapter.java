package com.example.sparsh23.laltern;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Panwar on 25/11/16.
 */
public class Test_Search_Adapter extends BaseAdapter  implements Filterable{

    ArrayList<HashMap<String,String>>     searchArrayList
            = new ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    Context cont;


    ArrayList<HashMap<String,String>> orig = new ArrayList<HashMap<String, String>>();
   // searchArrayList

    public Test_Search_Adapter(Context context, ArrayList<HashMap<String,String>> result)
    {

        cont = context;
        searchArrayList = result;
        inflater = (LayoutInflater)cont.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return searchArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return searchArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class Holder
    {
        TextView tag;
        TextView suggest;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = inflater.inflate(R.layout.test_search_item, null);
        Holder holder = new Holder();
        holder.tag = (TextView)view1.findViewById(R.id.test_search_tag);
        holder.suggest = (TextView)view1.findViewById(R.id.test_search_type);
        Typeface typeface1 = Typeface.createFromAsset(cont.getAssets(),"Montserrat-Light.otf");


        if(!searchArrayList.get(i).get("tag").equals(""))
        {

            holder.tag.setText(searchArrayList.get(i).get("tag").toLowerCase());
            holder.suggest.setText(searchArrayList.get(i).get("suggest"));

        }
        return view1;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<HashMap<String,String>> results = new ArrayList<HashMap<String, String>>();
                if (orig == null)
                    orig = searchArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (int i=0;i<orig.size();i++) {
                            if (orig.get(i).get("tag").toLowerCase().contains(constraint.toString().toLowerCase()))
                                results.add(orig.get(i));

                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                searchArrayList = (ArrayList<HashMap<String,String>>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
}
