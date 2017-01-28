package com.example.sparsh23.laltern;

//import android.app.Fragment;
import android.animation.ObjectAnimator;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sparsh23.laltern.dummy.DummyContent;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.lucasr.twowayview.TwoWayView;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import layout.Buyer_Policies;

public class NavigationMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ItemFragment.OnListFragmentInteractionListener,
        categoryFragment.OnListFragmentInteractionListener,
        FilterFragment.OnFragmentInteractionListener, FilterNoSearchFragment.OnFragmentInteractionListener,
        ItemFragment_Search.OnListFragmentInteractionListener, FilterNoSearchFragment_Search.OnFragmentInteractionListener
        ,Contact_Us.OnFragmentInteractionListener,About_Us.OnFragmentInteractionListener,Policies_List.OnFragmentInteractionListener,Buyer_Policies.OnFragmentInteractionListener    {


   // LandingHome landinghome;
    DBHelper dbHelper;
    private Tracker mTracker;

    ExpandableListView expandableListView;
    ArrayList<String> listDataHeader = new ArrayList<String>();
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
     HashMap<String,List<String>> listDataChild = new HashMap<String, List<String>>();
    ListView listView;

    ImageView jewel, homedecor, hometext, saree, painting,access, others,apparel, cart, request, miscell;
    CircleImageView profile;
    TextView home, aboutus, contactus, policies;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    SessionManager sessionManager;




    categoryFragment categoryFragment;
    //newHome newhom;
    HorizontalScrollView horizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
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
        //sessionManager = new SessionManager(getApplicationContext());

        AnalyticsApplication application = (AnalyticsApplication)getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.setScreenName("Home_Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        sessionManager = new SessionManager(getApplicationContext());


        horizontalScrollView = (HorizontalScrollView)findViewById(R.id.horizontallistauto);
        final ScrollView scrollView = (ScrollView)findViewById(R.id.scrollviewnav);



        //sendScroll();

        horizontalScrollView.post(new Runnable() {
            public void run() {
                horizontalScrollView.smoothScrollTo(0, horizontalScrollView.getRight());
                Log.d("scroll amount",""+horizontalScrollView.getMaxScrollAmount());
            }
        });

        horizontalScrollView.post(new Runnable() {
            public void run() {


                horizontalScrollView.fullScroll(horizontalScrollView.FOCUS_RIGHT);
            }
        });

        ObjectAnimator animator=ObjectAnimator.ofInt(horizontalScrollView, "scrollX", 7 );
        animator.setDuration(800);
        animator.start();

        autoSmoothScroll();

        prepareListData();

        Typeface tf = Typeface.createFromAsset(getAssets(),
                "Raleway-SemiBold.ttf");
        TextView tv = (TextView) findViewById(R.id.trend);
        TextView textView = (TextView)findViewById(R.id.topproducts);
        TextView tv1 = (TextView) findViewById(R.id.craft);
        TextView tv2 = (TextView) findViewById(R.id.artist);
        TextView tv3 = (TextView)findViewById(R.id.testicals);
       // TextView tv3 = (TextView)findViewById(R.id.toptext);


        tv.setTypeface(tf);
        tv1.setTypeface(tf);
        tv2.setTypeface(tf);
        textView.setTypeface(tf);
        tv3.setTypeface(tf);
      //  tv3.setTypeface(tf);
        //listView = (ListView)findViewById(R.id.listviewmenu);
        dbHelper = new DBHelper(getApplicationContext());

        //data = dbHelper.getimageData();

        request = (ImageView)findViewById(R.id.viewrequest);
        profile = (CircleImageView) findViewById(R.id.viewprofile);
        TwoWayView lvTest = (TwoWayView)findViewById(R.id.horizontallist);
        TwoWayView topproducts = (TwoWayView)findViewById(R.id.horizontallisttop);

       // TwoWayView lvTest2 = (TwoWayView) findViewById(R.id.horizontallist2);
       // GridViewWithHeaderAndFooter lvTest1 = (GridViewWithHeaderAndFooter)findViewById(R.id.horizontallist1);
       // TwoWayView lvTest1 = (TwoWayView)findViewById(R.id.horizontallist1);

        lvTest.refreshDrawableState();
        imageLoader.displayImage(sessionManager.getUserDetails().get("dp"),profile);
        Log.d("dp_profile",""+sessionManager.getUserDetails().get("dp"));
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTracker.setScreenName("Profile_view");
                mTracker.send(new HitBuilders.EventBuilder().build());
                sessionManager = new SessionManager(getApplicationContext());

                startActivity(new Intent(NavigationMenu.this,Profile.class));

            }
        });


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(NavigationMenu.this,Submit_Request_Random.class));

            }
        });






        cart = (ImageView)findViewById(R.id.viewcart);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavigationMenu.this,CartActivity.class));
            }
        });

        

        ArrayList<HashMap<String,String>> trendingmap = new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String,String>> top_products = new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String,String>> testicals_list = new ArrayList<HashMap<String, String>>();

        testicals_list = dbHelper.getimageDatatype("testimonials");
        trendingmap = dbHelper.getimageDatatype("trending");
        top_products = dbHelper.getimageDatatype("top");



       lvTest.setAdapter(new TrendingProAdapter(NavigationMenu.this,trendingmap));
        topproducts.setAdapter(new Top_Pro_Adapter(NavigationMenu.this,top_products));
        final ArrayList<HashMap<String, String>> finalTop_products = top_products;
        topproducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              Toast.makeText(getApplicationContext(),""+i+""+ finalTop_products.get(i).get("uid")+" "+finalTop_products.get(i).get("artuid"),Toast.LENGTH_SHORT).show();

              Intent intent = new Intent(NavigationMenu.this,ProductView.class);
              intent.putExtra("promap", finalTop_products.get(i));
              startActivity(intent);
          }
      });
        //,category)
       // lvTest1.setAdapter(new GridSubcatAdapter(getApplicationContext(),dbHelper.getimageDatatype("craft")));
       // lvTest2.setAdapter(new LandingHomeListAdapter(getApplicationContext(),dbHelper.getimageDatatype("artist")));
        ExpandableHeightGridView testicals = (ExpandableHeightGridView)findViewById(R.id.expandtesticals);
        testicals.setExpanded(true);
        testicals.setNumColumns(1);
        testicals.setAdapter(new LandingHomeListAdapter(getApplicationContext(),testicals_list));
        final ArrayList<HashMap<String, String>> finalTesticals_list = testicals_list;
        testicals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.testimonial_pop, null);
                TextView test_text = (TextView)alertLayout.findViewById(R.id.testimonial_text);
               Typeface typeface = Typeface.createFromAsset(getAssets(),
                       "DroidSansFallback.ttf");
                test_text.setTypeface(typeface);
                test_text.setText(""+finalTesticals_list.get(i).get("des"));
                AlertDialog.Builder alert = new AlertDialog.Builder(NavigationMenu.this,R.style.MyDialogTheme);
                alert.setTitle("Testimonial");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(true);
              //  AlertDialog alertdialog2 = .create();
                //alertdialog2.show();
                alert.create();
                alert.show();
            }
        });
      final   ExpandableHeightGridView gridView = (ExpandableHeightGridView)findViewById(R.id.expandgrid);
        ExpandableHeightGridView gridView_suicide = new ExpandableHeightGridView(this);
        gridView_suicide = (ExpandableHeightGridView)findViewById(R.id.expandgrid_suicide);
        gridView_suicide.setExpanded(true);
        gridView_suicide.setNumColumns(2);
        ArrayList<HashMap<String,String>> data_suicide = new ArrayList<HashMap<String, String>>();
        data_suicide.add(dbHelper.getimageDatatype("craft").get(1));
        gridView_suicide.setAdapter(new GridSubcatAdapter(getApplicationContext(),data_suicide));
        //gridView_suicide.setVisibility(View.GONE);
        //gridView =
        final ExpandableHeightGridView gridView1 = (ExpandableHeightGridView)findViewById(R.id.expandgridone);
        //ExpandableHeightGridView gridView1 = (ExpandableHeightGridView)findViewById(R.id.expandgridone);
        gridView1.setAdapter(new LandingHomeListAdapter(getApplicationContext(),dbHelper.getimageDatatype("artist")));
        gridView1.setNumColumns(1);
        gridView1.setExpanded(true);
        gridView.setNumColumns(2);
        ArrayList<HashMap<String,String>> craft_data = new ArrayList<HashMap<String, String>>();
        craft_data = dbHelper.getimageDatatype("craft");
        gridView.setAdapter(new GridSubcatAdapter(getApplicationContext(),craft_data));
        gridView.setExpanded(true);

       // final ExpandableHeightGridView finalGridView = gridView;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> map = new HashMap<String, String>();
                map = (HashMap<String,String>) gridView.getItemAtPosition(i);
                ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
                data = dbHelper.getimageDatatype(map.get("meta"));
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",data);
                ItemFragment newhom1 = ItemFragment.newInstance(1);
                FragmentManager transaction = getSupportFragmentManager();
                newhom1.setArguments(bundle);
                // fra.beginTransaction().replace()
                android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, newhom1);
                //transaction.beginTransaction().replace()
                frag.addToBackStack(null);
                frag.commit();
            }
        });

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NavigationMenu.this,New_Scrolling.class);
                HashMap<String,String> map = new HashMap<String, String>();
                map = (HashMap<String, String>) gridView1.getItemAtPosition(i);
                intent.putExtra("uid",map.get("uid"));
                Log.d("uid_meta",""+map.get("uid"));
                startActivity(intent);
            }
        });

        final EditText searchView = (EditText) findViewById(R.id.searchviewrealid);

        final ArrayList<HashMap<String, String>> finalTrendingmap = trendingmap;
        lvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),""+position+""+finalTrendingmap.get(position).get("uid")+" "+finalTrendingmap.get(position).get("artuid"),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(NavigationMenu.this,ProductView.class);
                intent.putExtra("promap", finalTrendingmap.get(position));
                startActivity(intent);
            }
        });

        //searchView.setQueryHint("Search for crafts, products and artists");

        if (searchView != null) {


            //scrollView.fullScroll(View.FOCUS_DOWN);
            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            //scrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                    startActivity(new Intent(NavigationMenu.this,Test_SearchView.class));
                    finish();
                }
            });
        }




        searchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (searchView.getRight() - searchView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {


                        startActivity(new Intent(NavigationMenu.this,FilterableActivity.class));
                        finish();

                        return true;
                    }
                }
                return false;


            }
        });


        // listView.setAdapter(itemsAdapter);


        //setActionBar(toolbar);

        //getActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //new ActionBarDrawerToggle()

        drawer.setDrawerListener(toggle);
        toggle.syncState();



        expandableListView = (ExpandableListView)findViewById(R.id.expandedlist);


        expandableListView.setGroupIndicator(null);
        expandableListView.setDivider(null);
        expandableListView.setDividerHeight(0);



//        expandableListView.addHeaderView();




        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.getMenu().getItem(0).setChecked(true);
//        onNavigationItemSelected(navigationView.getMenu().getItem(0));


        Intent intent = getIntent();

        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("datas");

        if(bundle!=null) {


            ItemFragment newhom1 = ItemFragment.newInstance(1);


            FragmentManager transaction = getSupportFragmentManager();


            newhom1.setArguments(bundle);
            // fra.beginTransaction().replace()
            android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, newhom1);
            //transaction.beginTransaction().replace()
            frag.addToBackStack(null);
            frag.commit();
        }

        Bundle bundle1 = new Bundle();
        bundle1 = intent.getBundleExtra("datas_search");

        if(bundle1!=null) {


            ItemFragment_Search newhom1 = ItemFragment_Search.newInstance(1);


            FragmentManager transaction = getSupportFragmentManager();


            newhom1.setArguments(bundle1);
            // fra.beginTransaction().replace()
            android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, newhom1);
            //transaction.beginTransaction().replace()
            frag.addToBackStack(null);
            frag.commit();
        }








        Log.d("child size" ,""+listDataChild.size());
        com.example.sparsh23.laltern.ExpandableListAdapter expandableListAdapter = new com.example.sparsh23.laltern.ExpandableListAdapter(listDataChild,listDataHeader,getApplicationContext());

        expandableListView.setAdapter(expandableListAdapter);

        View header = getLayoutInflater().inflate(R.layout.stream_row, expandableListView, false);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }

            }
        });
     //   expandableListView.addHeaderView(header, null, false);


        expandableListView.addFooterView(header,null,false);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String cat = listDataHeader.get(groupPosition);
                String subcat = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);

                Toast.makeText(getApplicationContext(),""+cat+"  "+subcat,Toast.LENGTH_SHORT).show();


                ArrayList<HashMap<String,String>> map = new ArrayList<HashMap<String, String>>();
                HashMap<String,String> aux = new HashMap<String, String>();
                aux.put("category",cat);
                aux.put("subcat",subcat);
                HashMap<String,ArrayList<HashMap<String,String>>> filterdata = new HashMap<String, ArrayList<HashMap<String, String>>>();
                filterdata.put("size",dbHelper.GetSizes(cat,subcat));
                filterdata.put("color",dbHelper.GetColor(cat,subcat));
                filterdata.put("protype",dbHelper.GetProType(cat,subcat));






                data = dbHelper.GetSubCategoryImageData(aux);
                Bundle bundle1 = new Bundle();

                bundle1.putSerializable("data",data);
                bundle1.putSerializable("filter",filterdata);
                bundle1.putSerializable("selection",aux);

               ItemFragment newhom =  ItemFragment.newInstance(1);


                FragmentManager transaction = getSupportFragmentManager();


                newhom.setArguments(bundle1);
                // fra.beginTransaction().replace()
                android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, newhom);
                //transaction.beginTransaction().replace()
                frag.addToBackStack(null);
                frag.commit();

                // break;
                drawer.closeDrawer(GravityCompat.START);







                return false;
            }
        });




        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            // Keep track of previous expanded parent
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                // Collapse previous parent if expanded.
                if ((previousGroup != -1) && (groupPosition != previousGroup)) {
                    expandableListView.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }
        });






        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                Toast.makeText(getApplicationContext(),""+groupPosition,Toast.LENGTH_SHORT).show();



                    if (groupPosition == 0) {

                        FragmentManager fm = NavigationMenu.this.getSupportFragmentManager();
                        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }


                        // break;
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    }

                else if (groupPosition==10)
                    {
                        Contact_Us fragment = new Contact_Us().newInstance(" "," ");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.navrep, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);

                    }
                else if (groupPosition==11)
                    {
                        About_Us fragment = new About_Us().newInstance(" "," ");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.navrep, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);

                    }

                    else if(groupPosition==12)
                    {

                        Policies_List fragment = new Policies_List().newInstance(" "," ");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.navrep, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);

                    }


                    return false;
                }

        });


         jewel = (ImageView)findViewById(R.id.JwlSuperCat);


        if (jewel!=null) {

            jewel.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    FragmentManager transaction = getSupportFragmentManager();

                    categoryFragment = categoryFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "jewel");
                    bundle.putString("cat","Jewellery");
                    categoryFragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, categoryFragment);
                    //transaction.beginTransaction().replace()
                    frag.addToBackStack(null);
                    frag.commit();


                }
            });
        }
        homedecor = (ImageView)findViewById(R.id.homecatSuperCat);

        homedecor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager transaction = getSupportFragmentManager();

                categoryFragment = categoryFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("type", "homedecor");
                bundle.putString("cat","Home Decor");
                categoryFragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, categoryFragment);
                //transaction.beginTransaction().replace()
                frag.addToBackStack(null);
                frag.commit();

            }
        });

        hometext = (ImageView)findViewById(R.id.hometextsupercat);


        if(hometext!=null){
        hometext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = getSupportFragmentManager();

                categoryFragment = categoryFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("type", "hometextile");
                bundle.putString("cat","Home Textile");
                categoryFragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, categoryFragment);
                //transaction.beginTransaction().replace()
                frag.addToBackStack(null);
                frag.commit();
            }
        });
        }
        saree = (ImageView)findViewById(R.id.sareeSuperCat);

        saree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = getSupportFragmentManager();

                categoryFragment = categoryFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("type", "saree");
                bundle.putString("cat","Sarees");
                categoryFragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, categoryFragment);
                //transaction.beginTransaction().replace()
                frag.addToBackStack(null);
                frag.commit();
            }
        });


        painting = (ImageView)findViewById(R.id.paintingsSuperCat);

        painting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = getSupportFragmentManager();

                categoryFragment = categoryFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("type", "painting");
                bundle.putString("cat","Paintings");
                categoryFragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, categoryFragment);
                //transaction.beginTransaction().replace()
                frag.addToBackStack(null);
                frag.commit();
            }
        });
                access = (ImageView)findViewById(R.id.accessoriesSuperCat);

        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = getSupportFragmentManager();

                categoryFragment = categoryFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("type", "accessories");
                bundle.putString("cat","Accessories");
                categoryFragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, categoryFragment);
                //transaction.beginTransaction().replace()
                frag.addToBackStack(null);
                frag.commit();
            }
        });
        others = (ImageView)findViewById(R.id.othersSuperCat);

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager transaction = getSupportFragmentManager();

                categoryFragment = categoryFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("type", "others");
                bundle.putString("cat","Others");
                categoryFragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, categoryFragment);
                //transaction.beginTransaction().replace()
                frag.addToBackStack(null);
                frag.commit();
            }
        });

        apparel = (ImageView)findViewById(R.id.apparelSuperCat);
        apparel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager transaction = getSupportFragmentManager();

                categoryFragment = categoryFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("type", "apparel");
                bundle.putString("cat","Apparel");
                categoryFragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, categoryFragment);
                //transaction.beginTransaction().replace()
                frag.addToBackStack(null);
                frag.commit();


            }
        });

      //  miscell =   (ImageView)findViewById(R.id.miscelSuperCat);

        ImageView missel = (ImageView)findViewById(R.id.miscelSuperCat);
        missel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager transaction = getSupportFragmentManager();

                categoryFragment = categoryFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("type", "mislane");
                bundle.putString("cat","Miscellaneous");
                categoryFragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, categoryFragment);
                //transaction.beginTransaction().replace()
                frag.addToBackStack(null);
                frag.commit();


            }
        });









    }

    public void autoSmoothScroll() {

    //    final HorizontalScrollView hsv = (HorizontalScrollView) view.findViewById(R.id.horiscroll);
        horizontalScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //hsv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                horizontalScrollView.smoothScrollBy(500, 0);
            }
        },100);
    }



    private void prepareListData() {


        // Adding data header

        listDataHeader.add("Home");
        listDataHeader.add("Jewellery");

        listDataHeader.add("Accessories");

        listDataHeader.add("Sarees");

        listDataHeader.add("Apparel");

        listDataHeader.add("Home Textile");

        listDataHeader.add("Home Decor");

        listDataHeader.add("Paintings");

        listDataHeader.add("Others");
        listDataHeader.add("Miscellaneous");
        listDataHeader.add("Contact us");
        listDataHeader.add("About us");
        listDataHeader.add("Policies");





        // Adding child data
        List<String> heading1 = new ArrayList<String>();
        heading1.add("Terracotta");
        heading1.add("Cane");
        heading1.add("Jute");
        heading1.add("Dokra");
        heading1.add("Wooden");


        List<String> heading2 = new ArrayList<String>();
        heading2.add("Footwear");
        heading2.add("Bags");
        heading2.add("Bamboo Craft");
        heading2.add("Ceramic Accessories");
        heading2.add("Wooden Accessories");
        heading2.add("Jute Craft");


        List<String> heading3 = new ArrayList<String>();
        heading3.add("Kalamkari");
        heading3.add("Bandhez");
        heading3.add("Bagh");
        heading3.add("Ajrakh");
        heading3.add("Chikankari");
        heading3.add("Bhagalpur Linen");
        heading3.add("Chanderi/Maheshwari");
        heading3.add("Banarasi");
        heading3.add("Eri/Assam Silk");


        List<String> heading4 = new ArrayList<String>();

        heading4.add("Running Fabrics");
        heading4.add("Jackets");
        heading4.add("Shawls");
        heading4.add("Dupatta");
        heading4.add("Stole");


        List<String> heading5 = new ArrayList<String>();
        heading5.add("Dhurry/Carpet");
        heading5.add("Bedsheet");
        heading5.add("Quilts");

        List<String> heading6 = new ArrayList<String>();
        heading6.add("Lamps");
        heading6.add("Iron Craft");
        heading6.add("Wooden Craft");
        heading6.add("Stone Craft");
        heading6.add("Ceramic Craft");
        heading6.add("Cane Craft");
        heading6.add("Wooden Toys");
        heading6.add("Wall Hangings");

        heading6.add("Bone and Horn");
        heading6.add("Papier Mache");

        List<String> heading7 = new ArrayList<String>();
        heading7.add("Murals and Paintings");
        heading7.add("Madhubani");
        heading7.add("Gond");
        heading7.add("Sanjhi");
        heading7.add("Mud Paintings");


        List<String> heading8 = new ArrayList<String>();
        heading8.add("Contemporary Jewellery");
        heading8.add("Waste Paper Products");
        heading8.add("Educational Toys");

        List<String> heading10 = new ArrayList<String>();
        heading10.add("Grass Craft");
        heading10.add("Copper Bells");
        heading10.add("Metal Inlay Craft");



        List<String> heading9 = new ArrayList<String>();






        listDataChild.put(listDataHeader.get(0),heading9);
        listDataChild.put(listDataHeader.get(1), heading1);// Header, Child data
        listDataChild.put(listDataHeader.get(2), heading2);
        listDataChild.put(listDataHeader.get(3),heading3);
        listDataChild.put(listDataHeader.get(4),heading4);
        listDataChild.put(listDataHeader.get(5),heading5);
        listDataChild.put(listDataHeader.get(6),heading6);
        listDataChild.put(listDataHeader.get(7),heading7);
        listDataChild.put(listDataHeader.get(8),heading8);
        listDataChild.put(listDataHeader.get(9),heading10);
        listDataChild.put(listDataHeader.get(10),heading9);
        listDataChild.put(listDataHeader.get(11),heading9);
        listDataChild.put(listDataHeader.get(12),heading9);



    }






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private void sendScroll(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {Thread.sleep(100);} catch (InterruptedException e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        horizontalScrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }



}
