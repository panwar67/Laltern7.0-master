package com.example.sparsh23.laltern;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.clans.fab.FloatingActionButton;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductView extends AppCompatActivity implements AboutArtistFrag.OnFragmentInteractionListener, AboutProductFrag.OnFragmentInteractionListener{

    TextView title , quan, price, artistname;
    RatingBar authen, prices, overall;
    DBHelper dbHelper;
    Button button;
    TextView bar;
     HashMap<String,String> data;
   // FloatingActionsMenu floatingActionsMenu;
    String DOWN_URL = "http://www.whydoweplay.com/lalten/Addtocart.php";
    int counter = 0;
    ExpandableLinearLayout expandableLinearLayout;
    ExpandableRelativeLayout expandableRelativeLayout ;
     HashMap<String, String> artdata = new HashMap<String, String>();
    SessionManager sessionManager;
    com.github.clans.fab.FloatingActionButton fabcart;
    com.github.clans.fab.FloatingActionButton fabreq;
    CrystalSeekbar seekBar;
    ImageView viewcart;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

       // button= (Button)findViewById(R.id.submitreq);







        viewcart = (ImageView)findViewById(R.id.viewcartpro);

        viewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductView.this,CartActivity.class));
            }
        });



        Intent intent = getIntent();
         data = (HashMap<String,String>)intent.getSerializableExtra("promap");
        ImageView imageView = (ImageView)findViewById(R.id.backpro);





        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

       // expandableLinearLayout = (ExpandableLinearLayout)findViewById(R.id.expandableLayout);

       // expandableLinearLayout.toggle();
       // expandableLinearLayout.expand();




        artistname = (TextView)findViewById(R.id.artistname);


        dbHelper = new DBHelper(getApplicationContext());






       // ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.artistexpand);
        artdata = dbHelper.getArtisian(data.get("artuid"));

      //  expandableListView.setAdapter(new ExpandableArtistAdapter(data,"Show Artist",getApplicationContext()));


        Log.d("art_data_authen", ""+artdata.get("authentic"));


//        authen.setRating(Float.parseFloat(artdata.get("authentic")));
  //      prices.setRating(Float.parseFloat(artdata.get("price")));
    //    overall.setRating(Float.parseFloat(artdata.get("rating")));
      //  artistname.setText(artdata.get("name").toString());




        Log.d("craft used",""+data.get("craft"));
//

/*        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ProductView.this,SubmitRequest.class);
                intent1.putExtra("map",data);
                startActivity(intent1);


            }
        });*/


        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);














    }



    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new AboutProductFrag().newInstance(data,""), "Product");
        adapter.addFragment(new AboutArtistFrag().newInstance(artdata,""), "Artist");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
       // startActivity(new Intent(ProductView.this,NavigationMenu.class));
        finish();
        super.onBackPressed();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}



