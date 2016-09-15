package com.example.sparsh23.laltern;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Upload extends AppCompatActivity {



    String DOWN_URL1 = "http://www.whydoweplay.com/lalten/InsertQuery.php";

    HashMap<String,String> map = new HashMap<String, String>();
    EditText subject, des;
    Button submit;
    DBHelper dbHelper;
    ImageView profile;
    ImageView stream;
    ImageView search;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Intent intent = getIntent();
        map = (HashMap<String, String>)intent.getSerializableExtra("map");
        sessionManager = new SessionManager(getApplicationContext());
        dbHelper = new DBHelper(getApplicationContext());



        subject = (EditText)findViewById(R.id.subjectque);
        des = (EditText)findViewById(R.id.queque);
        submit = (Button)findViewById(R.id.que_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadQuery(dbHelper.GetProfile(sessionManager.getUserDetails().get("uid")).get("uid"),des.getText().toString(),subject.getText().toString());


            }
        });

      /*  profile=(ImageView)findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Upload.this,Profile.class).putExtra("map",map));
                finish();
            }
        });

        stream=(ImageView)findViewById(R.id.feed);
        stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Upload.this, Stream.class).putExtra("map",map));
                finish();
            }
        });
        search=(ImageView)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Upload.this,Search.class).putExtra("map",map));
                finish();
            }
        });

        */

        }

    public boolean UploadQuery(final String buyuid, final String des, final String sub)

    {
        final ProgressDialog loading = ProgressDialog.show(this,"Getting Profile Data...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s!=null)
                        {



                            loading.dismiss();
                            Toast.makeText(getApplicationContext(),s.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Upload.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String uid=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("buyuid",buyuid);
                Keyvalue.put("des",des);
                Keyvalue.put("sub",sub);
                Keyvalue.put("enquid",uid);




                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue.add(stringRequest);






        return true;
    }






}
