package com.lions.sparsh23.laltern.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lions.sparsh23.laltern.CartActivity;
import com.lions.sparsh23.laltern.DBHelper;
import com.lions.sparsh23.laltern.R;
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
 * Created by Panwar on 8/31/2016.
 */

public class CartItemAdapter extends BaseAdapter {



    CartActivity context;
    DBHelper dbHelper;
    private static LayoutInflater inflater=null;

    ImageLoader imageLoader;
    DisplayImageOptions options;

    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

    public CartItemAdapter(CartActivity landingHome, ArrayList<HashMap<String,String>> data){

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

        dbHelper = new DBHelper(context);



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

        ImageView productimg;

        TextView title, price, quantity, artist,totalprice,size,color;
        Button remove;



    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        double total=0.0,rate,price;

        Holder holder=new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.cart_item, null);

        holder.productimg=(ImageView) rowView.findViewById(R.id.cartproductimg);
        // holder.custom=(ImageView) rowView.findViewById(R.id.customlanding);
        //holder.sliderShow = (SliderLayout) rowView.findViewById(R.id.sliderlandingtrending);
        holder.totalprice = (TextView)rowView.findViewById(R.id.prototalprice);

        holder.remove = (Button)rowView.findViewById(R.id.cartremovepro);

        holder.title = (TextView)rowView.findViewById(R.id.cartprotitle);

        holder.price = (TextView)rowView.findViewById(R.id.cartproprice);

        holder.quantity = (TextView)rowView.findViewById(R.id.cartquantity);
        holder.color = (TextView) rowView.findViewById(R.id.cartprocolor);

       // holder.artist = (TextView) rowView.findViewById(R.id.cartproartist);
        holder.size = (TextView)rowView.findViewById(R.id.cartprosize);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"Raleway-SemiBold.ttf");
        Typeface typeface1 = Typeface.createFromAsset(context.getAssets(),"Montserrat-Light.otf");
        holder.color.setTypeface(typeface1);
        //holder.color.setText();





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

        holder.title.setTypeface(typeface);

        holder.title.setText(""+result.get(position).get("title").toUpperCase());
        //holder.price.setTypeface(null, Typeface.BOLD);
        holder.price.setText(string+" "+result.get(position).get("rate")+"/Unit");
        holder.price.setTypeface(typeface1);
        holder.size.setTypeface(typeface1);
        holder.size.setText("Size: "+result.get(position).get("size"));
        // holder.moq.setTextColor(#ff000000);
        holder.quantity.setText("Qty - "+result.get(position).get("quantity"));
        holder.quantity.setTypeface(typeface1);
        rate = Double.parseDouble(result.get(position).get("rate"));
        total = rate*Integer.parseInt(result.get(position).get("quantity"));
        holder.totalprice.setText(""+string+" "+total);
        holder.color.setText(result.get(position).get("color"));
        imageLoader.displayImage(result.get(position).get("path"), holder.productimg);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dbHelper.RemoveFromCart(result.get(position).get("uid"));
                context.Deletecart(result.get(position).get("cartuid"),result.get(position).get("uid"));

                result.remove(position);

                notifyDataSetChanged();


            }
        });





        return rowView;
    }
}
