package com.example.sparsh23.laltern;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sparsh23 on 29/07/16.
 */
public class LandingHomeListAdapter extends BaseAdapter {


    Context context;
    private static LayoutInflater inflater=null;

    ImageLoader imageLoader;
    DisplayImageOptions options;

    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

    public LandingHomeListAdapter(Context landingHome, ArrayList<HashMap<String,String>> data){

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
        SliderLayout sliderShow ;


    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.landing_row, null);

        holder.deals=(ImageView) rowView.findViewById(R.id.dealslanding);
       // holder.custom=(ImageView) rowView.findViewById(R.id.customlanding);
        //holder.sliderShow = (SliderLayout) rowView.findViewById(R.id.sliderlandingtrending);

        //holder.title = (TextView)rowView.findViewById(R.id.headerord);



            Log.d("inside list landing", result.get(position).toString());

            imageLoader.displayImage(result.get(position).get("path"), holder.deals);





        return rowView;
    }
}
