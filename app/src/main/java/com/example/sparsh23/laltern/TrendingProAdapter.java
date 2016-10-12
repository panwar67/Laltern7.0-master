package com.example.sparsh23.laltern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sparsh23 on 05/08/16.
 */
public class TrendingProAdapter extends BaseAdapter {



    Context context;
    private static LayoutInflater inflater=null;

    ImageLoader imageLoader;
    DisplayImageOptions options;

    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

    public TrendingProAdapter(Context landingHome, ArrayList<HashMap<String,String>> data){

        result=data;
        context = landingHome;

        //imageLoader.destroy();
        inflater = (LayoutInflater)context.
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


//        Log.d("Inside adapter size",""+getCount());
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public class Holder
    {

        ImageView deals,sponser, custom;
        TextView title, price, moq;
        SliderLayout sliderShow ;
        RatingBar ratingBar;


    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.horizontalslider, null);

        holder.deals=(ImageView) rowView.findViewById(R.id.trendingproimg);
        // holder.custom=(ImageView) rowView.findViewById(R.id.customlanding);
        //holder.sliderShow = (SliderLayout) rowView.findViewById(R.id.sliderlandingtrending);

        holder.title = (TextView)rowView.findViewById(R.id.trendingprotit);

        holder.price = (TextView)rowView.findViewById(R.id.trendingproprice);

        holder.moq = (TextView)rowView.findViewById(R.id.promoqtrending);

        holder.ratingBar = (RatingBar)rowView.findViewById(R.id.productratingtrending);




        String string = "\u20B9";
        byte[] utf8 = null;
        try {
            utf8 = string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assert utf8 != null;
        try {
            string = new String(utf8, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d(" list trendingproduct", result.get(position).toString());


        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"Raleway-Regular.ttf");
        Typeface typeface1 = Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");

        holder.title.setTypeface(typeface);
        holder.title.setText(""+result.get(position).get("title"));
        holder.price.setTypeface(typeface1,Typeface.BOLD);
        holder.price.setText(string+" "+result.get(position).get("price"));
       // holder.moq.setTextColor(#ff000000);

        holder.moq.setText("MOQ - "+result.get(position).get("quantity"));
        holder.moq.setTypeface(typeface1,Typeface.BOLD);
        holder.ratingBar.setRating(Float.parseFloat(result.get(position).get("rating")));

        imageLoader.displayImage(result.get(position).get("path"), holder.deals);





        return rowView;
    }
}


