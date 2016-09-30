package com.example.sparsh23.laltern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Panwar on 28/09/16.
 */


public class RequestExpandableAdapter extends BaseExpandableListAdapter {
    String headers;
    ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    Context context;
    ImageLoader imageLoader;
    DisplayImageOptions options;


    public RequestExpandableAdapter(ArrayList<HashMap<String,String>> Child, String Headers, Context contxt) {
        context = contxt;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        result = Child;
        headers = Headers;
        options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY).resetViewBeforeLoading(true).build();
        ImageLoaderConfiguration.Builder config1 = new ImageLoaderConfiguration.Builder(context);
        config1.defaultDisplayImageOptions(options);
        config1.threadPriority(Thread.NORM_PRIORITY - 2);
        config1.denyCacheImageMultipleSizesInMemory();
        config1.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config1.diskCacheSize(100 * 1024 * 1024); // 50 MiB
        config1.tasksProcessingOrder(QueueProcessingType.LIFO);
        config1.writeDebugLogs();
        imageLoader = ImageLoader.getInstance();
//        imageLoader.destroy();
        imageLoader.init(config1.build());
    }

    @Override
    public int getGroupCount() {

       return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        int n = child.get(headers.get(groupPosition)).size();

        return result.size();

    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return headers;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return result.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 1;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View rowView;
        rowView = inflater.inflate(R.layout.list_header, null);

        TextView group = (TextView)rowView.findViewById(R.id.list_header_text);
        group.setText(headers.toString());


        return rowView;
    }

    public class Holder
    {

        ImageView img;
        TextView des;
        TextView status;
        TextView title;
        TextView quantity;
        TextView craft;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.order_row, null);
        holder.des=(TextView) rowView.findViewById(R.id.reqdes);
        holder.status = (TextView) rowView.findViewById(R.id.reqstatus);
        holder.img=(ImageView) rowView.findViewById(R.id.reqproductimg);

        holder.craft = (TextView)rowView.findViewById(R.id.requestcraft);
        holder.quantity = (TextView)rowView.findViewById(R.id.reqquantity);

        holder.des.setText(result.get(childPosition).get("des"));
        holder.status.setText(result.get(childPosition).get("status"));
        holder.quantity.setText(result.get(childPosition).get("quantity"));

        Log.d("inside adapter_des",result.get(childPosition).get("des").toString());

        imageLoader.displayImage(result.get(childPosition).get("path"), holder.img);

        return rowView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
