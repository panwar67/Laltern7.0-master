package com.lions.sparsh23.laltern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.lions.sparsh23.laltern.dummy.DummyContent;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.HashMap;

public class BannerArtist extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    ImageView webView;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    String meta;
    DBHelper dbHelper;
    ImageView sort;
    ToggleButton toggle;
    RecyclerView recyclerView;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    private static LayoutInflater inflater=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_artist);
        dbHelper = new DBHelper(getApplicationContext());

        webView = (ImageView)findViewById(R.id.artistwebview);
        inflater = (LayoutInflater)getApplicationContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY).resetViewBeforeLoading(true).build();
        ImageLoaderConfiguration.Builder config1 = new ImageLoaderConfiguration.Builder(getApplicationContext());
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

        sort = (ImageView)findViewById(R.id.searchpagesort);
        toggle = (ToggleButton)findViewById(R.id.togglelayoutitem);
        recyclerView = (RecyclerView)findViewById(R.id.list);
        //webView.getSettings().getJavaScriptCanOpenWindowsAutomatically();
       // webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setLoadWithOverviewMode(true);
        ///webView.getSettings().setUseWideViewPort(true);
       /// webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //  webView.clearCache(true);
        //webView.clearView();
        //webView.reload();
        //   webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        //webView.setScrollbarFadingEnabled(false);
        //webView.loadUrl("about:blank");
       // webView.loadUrl("http://www.whydoweplay.com/lalten/adi.html");
        //webView.setWebChromeClient(new WebChromeClient());
        Intent intent = getIntent();
        meta = intent.getStringExtra("uid");
        String n =  "http://www.whydoweplay.com/lalten/Images/"+meta+".png";
        imageLoader.displayImage(n,webView);


        data = dbHelper.getimageDataMeta(meta);

        Bundle bundle1 = new Bundle();

        bundle1.putSerializable("data",data);
     //   bundle1.putSerializable("filter",filterdata);
       // bundle1.putSerializable("selection",aux);

        ItemFragment newhom =  ItemFragment.newInstance(1);


        FragmentManager transaction = getSupportFragmentManager();


        newhom.setArguments(bundle1);
        // fra.beginTransaction().replace()
        android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.artrep, newhom);
        //transaction.beginTransaction().replace()
        frag.addToBackStack(null);
        frag.commit();




    }


    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
