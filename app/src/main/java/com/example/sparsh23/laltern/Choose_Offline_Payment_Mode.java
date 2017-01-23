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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Choose_Offline_Payment_Mode extends AppCompatActivity {

    Button place_order;
    ImageView back;
    String DOWN_URL2 = "http://www.4liontechosolutions.com/sendmail_lal10.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__offline__payment__mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        back = (ImageView) findViewById(R.id.back_offline_payment);
        setSupportActionBar(toolbar);
        final Intent intent = getIntent();

        sessionManager = new SessionManager(getApplicationContext());
        final HashMap<String, String> data = (HashMap<String, String>)intent.getSerializableExtra("checkout");
        final HashMap<String,String> add_data = (HashMap<String, String>)intent.getSerializableExtra("checkout_add");
        Log.d("inside_checkout",data.toString());
        place_order = (Button) findViewById(R.id.place_order_offline);
        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(Generate_Order(data.get("order_id"),data.get("price"),data,add_data))
               {

               }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }


    public boolean Generate_Order(final String orduid, final String grand_total, final HashMap<String,String> data, final HashMap<String,String> add_data)
    {
        final ProgressDialog loading = ProgressDialog.show(this,"Generating Invoice...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL2,
                new Response.Listener<String>()
                {


                    @Override
                    public void onResponse(String s)
                    {
                        loading.dismiss();
                        Intent intent1 = new Intent(Choose_Offline_Payment_Mode.this, Order_Successful.class);
                        intent1.putExtra("data", data);
                        intent1.putExtra("checkout_add",add_data);
                        startActivity(intent1);
                        finish();


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        Log.d("voley error",volleyError.toString());
                        //Showing toast
                        Toast.makeText(Choose_Offline_Payment_Mode.this, "Error In Connectivity four", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("orderid",orduid);
                Keyvalue.put("name",sessionManager.getUserDetails().get("name"));
                Keyvalue.put("email",sessionManager.getUserDetails().get("email"));
                Keyvalue.put("price",grand_total);
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
