package com.lions.sparsh23.laltern;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductView extends AppCompatActivity implements AboutArtistFrag.OnFragmentInteractionListener, AboutProductFrag.OnFragmentInteractionListener{

    TextView title , quan, price, artistname;
    RatingBar authen, prices, overall;
    DBHelper dbHelper;
    Button button;
    TextView bar;
    //private Tracker mTracker;

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

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    onBackPressed();

                return false;
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



