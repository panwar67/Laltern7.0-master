package com.example.sparsh23.laltern;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import java.util.Map;

public class ProductView extends Activity {

    TextView title , des, quan, price, artistname, craft;
    RatingBar authen, prices, overall;
    DBHelper dbHelper;
    Button button;
    TextView bar;
   // FloatingActionsMenu floatingActionsMenu;
    String DOWN_URL = "http://www.whydoweplay.com/lalten/Addtocart.php";
    int counter = 0;
    ExpandableLinearLayout expandableLinearLayout;
    ExpandableRelativeLayout expandableRelativeLayout ;
     HashMap<String, String> artdata = new HashMap<String, String>();
    SessionManager sessionManager;
    com.github.clans.fab.FloatingActionButton fabcart;

    com.github.clans.fab.FloatingActionButton fabreq;

    //  FloatingActionButton fabcart;
    CrystalSeekbar seekBar;

    ImageView viewcart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

       // button= (Button)findViewById(R.id.submitreq);
       authen = (RatingBar)findViewById(R.id.authenrate);
        prices = (RatingBar)findViewById(R.id.pricerate);
        overall = (RatingBar)findViewById(R.id.overallrating);
        seekBar = (CrystalSeekbar)findViewById(R.id.quantity);
        authen.setMax(5);
        prices.setMax(5);
        overall.setMax(5);

        bar = (TextView)findViewById(R.id.quantitymoq);
        sessionManager = new SessionManager(getApplicationContext());



        viewcart = (ImageView)findViewById(R.id.viewcartpro);

        viewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductView.this,CartActivity.class));
            }
        });



        Intent intent = getIntent();
        final HashMap<String,String> data = (HashMap<String,String>)intent.getSerializableExtra("promap");



        seekBar.setMinStartValue(Float.parseFloat(data.get("quantity")));
        seekBar.setMinValue(Float.parseFloat(data.get("quantity")));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            seekBar.setBarColor(getColor(R.color.green));
        }
        ImageView imageView = (ImageView)findViewById(R.id.backpro);


        seekBar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {

                bar.setText(value.toString());
            }
        });



        fabcart = (FloatingActionButton) findViewById(R.id.fabcart);
        fabreq = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.fabreq);

        fabreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProductView.this,SubmitRequest.class);
                intent1.putExtra("map",data);
                startActivity(intent1);
            }
        });
      //  fabcart = new FloatingActionButton(getApplicationContext());



        //fabcart = (FloatingActionButton) findViewById(R.id.fabcart);



        fabcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (dbHelper.IsProductUnique(data.get("uid")))
                {




                String uid=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

                dbHelper.InsertCartData(uid,data.get("uid"),sessionManager.getUserDetails().get("uid"), String.valueOf(bar.getText()));
                upload_data(uid,data.get("uid"),sessionManager.getUserDetails().get("uid"),String.valueOf(bar.getText()));

                }
                else {

                    Toast.makeText(getApplicationContext(),"Already In Cart",Toast.LENGTH_SHORT).show();

                }
                }
        });
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



        SliderLayout sliderShow = (SliderLayout) findViewById(R.id.slider);

        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);





       // ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.artistexpand);
        artdata = dbHelper.getArtisian(data.get("artuid"));

      //  expandableListView.setAdapter(new ExpandableArtistAdapter(data,"Show Artist",getApplicationContext()));


        Log.d("art_data_authen", ""+artdata.get("authentic"));


        authen.setRating(Float.parseFloat(artdata.get("authentic")));
        prices.setRating(Float.parseFloat(artdata.get("price")));
        overall.setRating(Float.parseFloat(artdata.get("rating")));
        artistname.setText(artdata.get("name").toString());



        int noimg = Integer.parseInt(data.get("noimages"));

        Log.d("No Images", ""+noimg);


        if(noimg>0){

            sliderShow.addSlider(new TextSliderView(this).image( data.get("path")));


            for(int i=1;i<noimg;i++){


                String n =  "http://www.whydoweplay.com/lalten/Images/"+data.get("uid")+"_"+i+".jpeg";

                Log.d("path", n);

                sliderShow.addSlider(new TextSliderView(this).image(n));


            }


        }


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

        Typeface tf = Typeface.createFromAsset(getAssets(),
                "Aller_Rg.ttf");


        title = (TextView)findViewById(R.id.titlepro);
        des = (TextView)findViewById(R.id.descriptionpartpro);
        quan = (TextView)findViewById(R.id.quantitypro);
        price = (TextView)findViewById(R.id.pricepro);
        craft = (TextView)findViewById(R.id.typepro);

        title.setTypeface(tf);

        TextView textView = (TextView) findViewById(R.id.showart);

        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.visible);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProductView.this,ArtisianPageShow.class);
                intent1.putExtra("artmap",artdata);
                Toast.makeText(getApplicationContext(),"pressed",Toast.LENGTH_SHORT).show();
                startActivity(intent1);
            }
        });
        linearLayout.setVisibility(View.GONE);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                counter++;

                if(counter==1){
                    linearLayout.setVisibility(View.VISIBLE);


                }

                 if (counter%2==0)
                {

                    linearLayout.setVisibility(View.GONE);
                }

                 if(counter%2==1){

                    linearLayout.setVisibility(View.VISIBLE);

                }

                ;// prices, overall;
            }
        });

        Log.d("craft used",""+data.get("craft"));
//
        price.setText( "PRICE : "+string+""+data.get("price"));
        des.setText(data.get("des"));
        quan.setText("M.O.Q : "+data.get("quantity"));
        title.setText(data.get("title").toUpperCase());
        craft.setText("used craft "+data.get("craft"));
        Log.d("craft used",""+data.get("craft"));
        Toast.makeText(getApplicationContext(),""+data.get("craft"),Toast.LENGTH_SHORT).show();

/*        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ProductView.this,SubmitRequest.class);
                intent1.putExtra("map",data);
                startActivity(intent1);


            }
        });*/















    }

    @Override
    public void onBackPressed() {
       // startActivity(new Intent(ProductView.this,NavigationMenu.class));
        finish();
        super.onBackPressed();
    }

    public void upload_data(final String cartuid, final String prouid, final String useruid, final String quantity)
    {
        final ProgressDialog loading = ProgressDialog.show(this,"Adding to cart...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {





                        Toast.makeText(getApplicationContext(),s.toString(),Toast.LENGTH_LONG).show();

                        Log.d("response",s.toString());


                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(ProductView.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("cartuid",cartuid);
                Keyvalue.put("prouid",prouid);
                Keyvalue.put("useruid",useruid);
                Keyvalue.put("quantity",quantity);
                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
