package com.example.sparsh23.laltern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SubmitRequest extends AppCompatActivity {

    String DOWN_URL = "http://www.whydoweplay.com/lalten/InsertReq.php";
    ImageView imageView;
    SessionManager sessionManager;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    EditText craft, quantity;
    private Tracker mTracker;


    Button submit;
    DBHelper dbHelper;
    HashMap<String, String> hashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_request);
        dbHelper = new DBHelper(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());
        imageView = (ImageView)findViewById(R.id.reqproimg);
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
        AnalyticsApplication application = (AnalyticsApplication)getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.setScreenName("Request_from_product");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        craft = (EditText)findViewById(R.id.requestcraft);
        quantity = (EditText)findViewById(R.id.requestquantity);
        submit = (Button)findViewById(R.id.submitreq);

        Intent intent = getIntent();
         hashMap = (HashMap<String, String>)intent.getSerializableExtra("map");
        imageLoader.displayImage(hashMap.get("path"),imageView);
        final EditText editText = (EditText)findViewById(R.id.desreq);
        final HashMap<String,String> map = sessionManager.getUserDetails();
        dbHelper.GetProfile(sessionManager.getUserDetails().get("uid")).get("uid");

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mTracker.setScreenName("Request_submitted");
                mTracker.send(new HitBuilders.EventBuilder().build());

                String des = editText.getText().toString();
                String prouid = hashMap.get("uid");
                String useruid = dbHelper.GetProfile(sessionManager.getUserDetails().get("uid")).get("uid");
                Log.d("Request data ", ""+des+" pro "+prouid+"user "+useruid);

                upload_data(prouid,sessionManager.getUserDetails().get("uid"),des,hashMap.get("path"), craft.getText().toString(),quantity.getText().toString());
         }
        });
    }



    public void upload_data(final String prouid, final String buyuid, final String des, final String path, final String craft, final String quantity)

    {
        final ProgressDialog loading = ProgressDialog.show(this,"Registering User...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String res = s.replaceAll("\\s+","");
                        if (res.equals("Uploaded"))



                        Toast.makeText(getApplicationContext(),s.toString(),Toast.LENGTH_SHORT).show();



                        loading.dismiss();

                        finish();



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();


                        //Showing toast
                        Toast.makeText(SubmitRequest.this, "Error In Connectivity"+volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String



                String uid=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("prouid",prouid);
                Keyvalue.put("buyuid",buyuid);
                Keyvalue.put("des",des);
                Keyvalue.put("requid",uid);
                Keyvalue.put("path",path);
                Keyvalue.put("craft",craft);
                Keyvalue.put("quantity",quantity);



                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        //startActivity(new Intent(SubmitRequest.this,ProductView.class).putExtra("promap",hashMap));
        finish();


    }
}
