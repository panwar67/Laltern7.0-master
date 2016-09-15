package com.example.sparsh23.laltern;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

/**
 * Created by Sparsh23 on 24/07/16.
 */

public class CustomOrderAdapter extends BaseAdapter {

    Context context;

    ImageLoader imageLoader;
    DisplayImageOptions options;

    ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    public CustomOrderAdapter(Context mainActivity, ArrayList<HashMap<String,String>> imagesdata) {
        // TODO Auto-generated constructor stub
        result=imagesdata;
        context=mainActivity;

        //imageLoader.destroy();
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


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
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.order_row, null);
        holder.des=(TextView) rowView.findViewById(R.id.reqdes);
        holder.status = (TextView) rowView.findViewById(R.id.reqstatus);
        holder.img=(ImageView) rowView.findViewById(R.id.reqproductimg);

        holder.craft = (TextView)rowView.findViewById(R.id.requestcraft);
        holder.quantity = (TextView)rowView.findViewById(R.id.reqquantity);

        holder.des.setText(result.get(position).get("des"));
        holder.status.setText(result.get(position).get("status"));
        holder.quantity.setText(result.get(position).get("quantity"));

        imageLoader.displayImage(result.get(position).get("path"), holder.img);

        return rowView;
    }

}

