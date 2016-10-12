package com.example.sparsh23.laltern;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sparsh23.laltern.ItemFragment.OnListFragmentInteractionListener;
import com.example.sparsh23.laltern.dummy.DummyContent.DummyItem;
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
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    ImageLoader imageLoader;
    DisplayImageOptions options;
    Context context;
    int typeitem;
    private final ArrayList<HashMap<String,String>> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(ArrayList<HashMap<String,String>> items, OnListFragmentInteractionListener listener, Context cont, int col) {
        mValues = items;
        mListener = listener;
        context = cont;
        typeitem = col;

        options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY).resetViewBeforeLoading(true).build();
        ImageLoaderConfiguration.Builder config1 = new ImageLoaderConfiguration.Builder(cont);
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if(typeitem ==1){


             view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item, parent, false);



        }
        if(typeitem ==2){


             view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.griditemlayout, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       // holder.mItem = mValues.get(position);
       // holder.mIdView.setText();
        holder.mContentView.setText(mValues.get(position).get("title"));
        holder.gridprice.setText(""+mValues.get(position).get("price"));
        holder.moq.setText("M.O.Q - "+mValues.get(position).get("quantity"));
        holder.artist.setText("By - "+mValues.get(position).get("artuid"));


        holder.ratingBar.setRating(Float.parseFloat(mValues.get(position).get("rating")));
        imageLoader.displayImage(mValues.get(position).get("path"), holder.gridproduct);



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                   // mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final TextView artist, moq;
        public final ImageView gridproduct;
        public final RatingBar ratingBar;

        public final TextView gridprice;

        public ViewHolder(View view) {
            super(view);
            mView = view;



            gridprice = (TextView)view.findViewById(R.id.pricegrid);
            mContentView = (TextView) view.findViewById(R.id.titlegrid);
            gridproduct = (ImageView)view.findViewById(R.id.gridimg1);
            artist = (TextView)view.findViewById(R.id.artgrid);
            moq = (TextView)view.findViewById(R.id.moqgrid);
            ratingBar = (RatingBar)view.findViewById(R.id.productratingview);


        }

         }
}
