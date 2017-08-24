package com.lions.sparsh23.laltern;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.HashMap;

public class Profile extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    ImageView dp, logout1, back, request;
    DBHelper dbHelper;

    private FirebaseAuth mAuth;
    CallbackManager callbackManager;

    HashMap<String, String> map = new HashMap<String, String>();
    HashMap<String, String> data = new HashMap<String, String>();
    TextView name, company, desig, tob, addr, cont, pan, email, webs, state, city, requests;
    Button logout, orders;
    SessionManager sessionManager;
    ExpandableListView expandableListView, address, order, wishlist;
    ExpandableHeightGridView real_addr_list;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        FacebookSdk.sdkInitialize(getApplicationContext());
        options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY).resetViewBeforeLoading(true).build();
        ImageLoaderConfiguration.Builder config1 = new ImageLoaderConfiguration.Builder(getApplicationContext());
        config1.defaultDisplayImageOptions(options);
        config1.threadPriority(Thread.NORM_PRIORITY - 2);
        config1.denyCacheImageMultipleSizesInMemory();
        config1.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config1.diskCacheSize(100 * 1024 * 1024); // 50 MiB
        config1.tasksProcessingOrder(QueueProcessingType.LIFO);
        config1.writeDebugLogs();



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        imageLoader = ImageLoader.getInstance();
//        imageLoader.destroy();
        imageLoader.init(config1.build());


        sessionManager = new SessionManager(getApplicationContext());
        dbHelper = new DBHelper(getApplicationContext());

        dp = (ImageView) findViewById(R.id.imageView1);
        back = (ImageView) findViewById(R.id.backprofile);
        //request = (ImageView)findViewById(R.id.requestprofile);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        logout1 = (ImageView) findViewById(R.id.logout);

        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginManager.getInstance().logOut();
                signOut();
                sessionManager.logoutUser();
                finish();
            }
        });

        imageLoader.displayImage(sessionManager.getUserDetails().get("dp"), dp);

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "" + sessionManager.getUserDetails().get("dp"), Toast.LENGTH_SHORT).show();
            }
        });




        // expandableListView.setAdapter(new RequestExpandableAdapter(map,"My Requests",getApplicationContext()));
        //order.setAdapter(new RequestExpandableAdapter(map,"My Orders",getApplicationContext()));
        //address.setAdapter(new Address_ExpandableList_Adapter(dbHelper.GetAddresses(),"My Addresses",getApplicationContext()));

        final TextView textView = (TextView) findViewById(R.id.toggleaddr);


        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "SourceSansPro-Regular.otf");

        textView.setTypeface(typeface);
        textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            startActivity(new Intent(Profile.this, Profile_Detail.class).putExtra("type", textView.getText().toString()));

                                        }
                                    }
        );

        final TextView request = (TextView) findViewById(R.id.togglerequest);

        request.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startActivity(new Intent(Profile.this, Profile_Detail.class).putExtra("type", request.getText().toString()));
                return false;
            }
        });


        final TextView order = (TextView) findViewById(R.id.toggleorders);
        order.setTypeface(typeface);


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,Profile_Detail.class).putExtra("type",order.getText().toString()));
            }
        });

        request.setTypeface(typeface);
        Log.d("address_size", "" + dbHelper.GetAddresses().size());
        name = (TextView) findViewById(R.id.nameprofile);

        name.setText(sessionManager.getUserDetails().get("name"));
        name.setTypeface(typeface);
        email = (TextView) findViewById(R.id.emailpro);
        email.setTypeface(typeface);
       // cont.setTypeface(typeface);


        email.setText(sessionManager.getUserDetails().get("email"));


        //cont.setText(dbHelper.GetProfile(sessionManager.getUserDetails().get("uid")).get("cont"));
     /*   stream=(ImageView)findViewById(R.id.feed);
        stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Stream.class).putExtra("map",map));
                finish();
            }
        });
        upload=(ImageView)findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Upload.class).putExtra("map",map) );
                finish();
            }
        });
        search=(ImageView)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Search.class).putExtra("map",map));
                finish();
            }
        });

        */


        //  data  = dbHelper.GetProfile();


    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //  updateUI(null);
                    }
                });


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Profile.this,NavigationMenu.class));
        finish();
    }
}
