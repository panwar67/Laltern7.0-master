package com.example.sparsh23.laltern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import com.aviadmini.quickimagepick.PickSource;
import com.aviadmini.quickimagepick.QuickImagePick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Submit_Request_Random extends AppCompatActivity {


    ImageView imageView;
    ImageLoader imageLoader;
    Button submit ;
    DBHelper dbHelper;
    EditText description, quantity, craftreq;
    String DOWN_URL = "http://www.whydoweplay.com/lalten/InsertReqDirect.php";
    SessionManager sessionManager;


    QuickImagePick.Callback callback = new QuickImagePick.Callback() {
        @Override
        public void onImagePicked(@NonNull PickSource pickSource, int i, @NonNull Uri uri) {



            Log.d("uri image", uri.toString());

            Glide.with(getApplicationContext())
                    .load(uri)
                    .fitCenter()
                    .into(imageView);

          // String urlimg = "file://"+uri.getPath();


            //imageLoader.displayImage(urlimg,imageView);
        }

        @Override
        public void onError(@NonNull PickSource pickSource, int i, @NonNull String s) {

        }

        @Override
        public void onCancel(@NonNull PickSource pickSource, int i) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit__request__random);


        sessionManager = new SessionManager(getApplicationContext());

        dbHelper = new DBHelper(getApplicationContext());

        description = (EditText)findViewById(R.id.desreq);
        quantity = (EditText)findViewById(R.id.requestquantity);
        craftreq = (EditText)findViewById(R.id.requestcraft);

        submit = (Button)findViewById(R.id.reqbutton);





        ImageLoaderConfiguration.Builder config1 = new ImageLoaderConfiguration.Builder(getApplicationContext());
        config1.threadPriority(Thread.NORM_PRIORITY - 2);
        config1.denyCacheImageMultipleSizesInMemory();
        config1.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config1.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config1.tasksProcessingOrder(QueueProcessingType.LIFO);
        config1.writeDebugLogs();
        imageLoader= ImageLoader.getInstance();
        imageLoader.init(config1.build());

        imageView = (ImageView)findViewById(R.id.reqproimg);




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String[] types = {QuickImagePick.MIME_TYPE_IMAGE_JPEG, QuickImagePick.MIME_TYPE_IMAGE_WEBP};
                QuickImagePick.setAllowedMimeTypes(getApplicationContext(), types);

                QuickImagePick.pickFromMultipleSources(Submit_Request_Random.this, 1, "All sources", PickSource.CAMERA,  PickSource.GALLERY);






                //Intent intent = new Intent(getApplicationContext(), com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity.class);

                //intent.putExtra(com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity.INTENT_EXTRA_MODE, com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity.MODE_SINGLE);
                //intent.putExtra(com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity.INTENT_EXTRA_LIMIT, 1);
                //intent.putExtra(com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity.INTENT_EXTRA_SHOW_CAMERA, true);

                //startActivityForResult(intent, 67);



            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {



                String uid=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());


                dbHelper.InsertRequestData("ANY",sessionManager.getUserDetails().get("uid"),uid,description.getText().toString(),"https://www.whydoweplay.com/lalten/DirectRequestImages/"+uid+".jpeg","Under Review","WAIT",craftreq.getText().toString(),quantity.getText().toString());


                upload_data("any",sessionManager.getUserDetails().get("uid"),description.getText().toString(),craftreq.getText().toString(),quantity.getText().toString(),uid);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });






    }



    @Override
    protected void onActivityResult(final int pRequestCode, final int pResultCode, final Intent pData) {

        if (!QuickImagePick.handleActivityResult(getApplicationContext(), pRequestCode, pResultCode, pData, this.callback)) {
            super.onActivityResult(pRequestCode, pResultCode, pData);
        }

    }

    public void upload_data(final String prouid, final String buyuid, final String des,  final String craft, final String quantity,final String uid)

    {
        final ProgressDialog loading = ProgressDialog.show(this,"Registering User...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                       // String res = s.replaceAll("\\s+","");
                        //if (res.equals("Uploaded"))



                            Toast.makeText(getApplicationContext(),s.toString(),Toast.LENGTH_SHORT).show();

                        Log.d("submit req", ""+s.toString());



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
                        Toast.makeText(Submit_Request_Random.this, "Error In Connectivity"+volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                GlideBitmapDrawable drawable = (GlideBitmapDrawable) imageView.getDrawable();

               // BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();


                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("prouid",prouid);
                Keyvalue.put("buyuid",buyuid);
                Keyvalue.put("des",des);
                Keyvalue.put("requid",uid);
                Keyvalue.put("path",getStringImage(bitmap));
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


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);


        return encodedImage;
    }





}
