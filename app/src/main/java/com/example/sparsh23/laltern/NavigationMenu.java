package com.example.sparsh23.laltern;

//import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
//import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sparsh23.laltern.dummy.DummyContent;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NavigationMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,   ItemFragment.OnListFragmentInteractionListener, categoryFragment.OnListFragmentInteractionListener, newHome.OnFragmentInteractionListener, SubCatItemsFragment.OnListFragmentInteractionListener, FilterFragment.OnFragmentInteractionListener, FilterNoSearchFragment.OnFragmentInteractionListener{


   // LandingHome landinghome;
    DBHelper dbHelper;

    ExpandableListView expandableListView;
    ArrayList<String> listDataHeader = new ArrayList<String>();
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
     HashMap<String,List<String>> listDataChild = new HashMap<String, List<String>>();
    ListView listView;

    ImageView jewel, homedecor, hometext, saree, painting,access, others,apparel, cart, request, profile;



    categoryFragment categoryFragment;
    newHome newhom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prepareListData();
        //listView = (ListView)findViewById(R.id.listviewmenu);
        dbHelper = new DBHelper(getApplicationContext());

        //data = dbHelper.getimageData();

        request = (ImageView)findViewById(R.id.viewrequest);
        profile = (ImageView)findViewById(R.id.viewprofile);
        TwoWayView lvTest = (TwoWayView)findViewById(R.id.horizontallist);
        TwoWayView lvTest2 = (TwoWayView) findViewById(R.id.horizontallist2);
        TwoWayView lvTest1 = (TwoWayView)findViewById(R.id.horizontallist1);

        lvTest.refreshDrawableState();



        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(NavigationMenu.this,ProfileDrawer.class));

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
        trendingmap = dbHelper.getimageDatatype("trending");

        lvTest.setAdapter(new TrendingProAdapter(NavigationMenu.this,trendingmap));
        lvTest1.setAdapter(new LandingHomeListAdapter(getApplicationContext(),dbHelper.getimageDatatype("craft")));
        lvTest2.setAdapter(new LandingHomeListAdapter(getApplicationContext(),dbHelper.getimageDatatype("artist")));
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
            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearchRequested();
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







        Log.d("child size" ,""+listDataChild.size());
        com.example.sparsh23.laltern.ExpandableListAdapter expandableListAdapter = new com.example.sparsh23.laltern.ExpandableListAdapter(listDataChild,listDataHeader,getApplicationContext());

        expandableListView.setAdapter(expandableListAdapter);

        View header = getLayoutInflater().inflate(R.layout.nav_header_navigation_menu, expandableListView, false);

        expandableListView.addHeaderView(header, null, false);




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



                data = dbHelper.GetSubCategoryImageData(aux);
                Bundle bundle1 = new Bundle();

                bundle1.putSerializable("data",data);
                bundle1.putSerializable("filter",aux);

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
                categoryFragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, categoryFragment);
                //transaction.beginTransaction().replace()
                frag.addToBackStack(null);
                frag.commit();


            }
        });









    }

    private void prepareListData() {


        listDataHeader.add("Home");
        listDataHeader.add("Shop From Us");
        // Adding data header
        listDataHeader.add("jewellery");

        listDataHeader.add("Accessories");

        listDataHeader.add("Sarees");

        listDataHeader.add("Apparel");

        listDataHeader.add("Home Textile");

        listDataHeader.add("Home Decor");

        listDataHeader.add("Paintings");

        listDataHeader.add("Others");

        listDataHeader.add("About Us");
        listDataHeader.add("Contact Us");
        listDataHeader.add("Policies");
        listDataHeader.add("Chat");




        // Adding child data
        List<String> heading1 = new ArrayList<String>();
        heading1.add("Terracotta");
        heading1.add("silver");
        heading1.add("Metal");
        heading1.add("Cane");
        heading1.add("Contemporary");
        heading1.add("Jute");
        heading1.add("Dokra");
        heading1.add("Wooden");


        List<String> heading2 = new ArrayList<String>();
        heading2.add("Footwear");
        heading2.add("Bag and Belts");
        heading2.add("Tribal");

        List<String> heading3 = new ArrayList<String>();



                heading3.add("Printed");
        heading3.add("Woven");
        heading3.add("Embroidery");


        List<String> heading4 = new ArrayList<String>();
        heading4.add("Kurta");
        heading4.add("Shawls");
        heading4.add("Stole");
        heading4.add("Dupatta");
        heading4.add("Fabrics");
        heading4.add("Pants and Skirts");
        heading4.add("Jackets");
        heading4.add("Tops and Dresses");


        List<String> heading5 = new ArrayList<String>();
                                heading5.add("Cushion Covers");
                heading5.add("Rugs and Dhurries");
        heading5.add("Quilts");
        heading5.add("Bed Linen");
                heading5.add("Table Linen");

        List<String> heading6 = new ArrayList<String>();
        heading6.add("Lamps");
        heading6.add("Wooden Decor");
        heading6.add("Marble Decor");
        heading6.add("Stone Decor");
        heading6.add("Ceramic");
        heading6.add("Bone and Horn Decor");
        heading6.add("Paper and Horn Decor");
        heading6.add("Paper Mache");
        heading6.add("Decor MISC");

        List<String> heading7 = new ArrayList<String>();
        heading7.add("Murals and Paintings");
        heading7.add("Madhubani");
        heading7.add("Gond");
        heading7.add("Sanjhi");
        heading7.add("Mud Paintings");


        List<String> heading8 = new ArrayList<String>();
        heading8.add("Miscellaneous Crafts");
        heading8.add("Waste paper products");

        List<String> heading9 = new ArrayList<String>();






        listDataChild.put("Home", heading9 );
        listDataChild.put("Shop From Us", heading9);
        listDataChild.put(listDataHeader.get(2), heading1);// Header, Child data
        listDataChild.put(listDataHeader.get(3), heading2);
        listDataChild.put(listDataHeader.get(4),heading3);
        listDataChild.put(listDataHeader.get(5),heading4);
        listDataChild.put(listDataHeader.get(6),heading5);
        listDataChild.put(listDataHeader.get(7),heading6);
        listDataChild.put(listDataHeader.get(8),heading7);
        listDataChild.put(listDataHeader.get(9),heading8);
        listDataChild.put(listDataHeader.get(10),heading9);
        listDataChild.put(listDataHeader.get(11),heading9);
        listDataChild.put(listDataHeader.get(12),heading9);
        listDataChild.put("About Us",heading9);
        listDataChild.put("Contact Us", heading9);
        listDataChild.put("Policies",heading9);
        listDataChild.put("Chat",heading9);



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


        android.app.FragmentManager fra = getFragmentManager();
        FragmentManager transaction = getSupportFragmentManager();


        if (id == R.id.nav_camera) {




            newhom =  newhom.newInstance("a","a");



           // fra.beginTransaction().replace()
            android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, newhom);
            //transaction.beginTransaction().replace()
            frag.addToBackStack(null);
            frag.commit();
           // break;



            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            categoryFragment = categoryFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("type","jewel");
            categoryFragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, categoryFragment);
            //transaction.beginTransaction().replace()
            frag.addToBackStack(null);
            frag.commit();

        } else if (id == R.id.nav_slideshow)
        {




        } else if (id == R.id.nav_manage)
        {

            newhom = newHome.newInstance("","");
            android.support.v4.app.FragmentTransaction frag = transaction.beginTransaction().replace(R.id.navrep, newhom);
            //transaction.beginTransaction().replace()
            frag.addToBackStack(null);
            frag.commit();


        } else if (id == R.id.nav_share)
        {

        } else if (id == R.id.nav_send)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }



}
