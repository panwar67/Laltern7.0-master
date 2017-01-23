package com.example.sparsh23.laltern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.facebook.GraphRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order_Successful extends AppCompatActivity {

    ListView listView;
    Button shopping;
    String DOWN_URL2 = "http://www.whydoweplay.com/lalten/Receive_Order.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__successful);
        listView = (ListView)findViewById(R.id.order_address);
        shopping = (Button)findViewById(R.id.continueshopping);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Order_Successful.this,NavigationMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        Intent intent = getIntent();
        HashMap<String,String> map = new HashMap<String, String>();
        HashMap<String,String> checkout_data = new HashMap<String, String>();
        map = (HashMap<String, String>) intent.getSerializableExtra("data");
        checkout_data = (HashMap<String, String>)intent.getSerializableExtra("checkout_add");

        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
        data.add(checkout_data);
        Generate_Order(map.get("order_id"),"offline",map.get("product_list"),map.get("useruid"),map.get("useradd"),map.get("price"));

        listView.setAdapter(new Checkout_Addr_Adapter(getApplicationContext(),data));




    }

    public void Generate_Order(final String orduid, final String payuid, final String prouid, final String useruid, final String ordadd, final String grandtotal)
    {
        final ProgressDialog loading = ProgressDialog.show(this,"Generating Invoice...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        Log.d("voley error",volleyError.toString());
                        //Showing toast
                        Toast.makeText(Order_Successful.this, "Error In Connectivity four", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("orduid",orduid);
                Keyvalue.put("payuid",payuid);
                Keyvalue.put("prouid",prouid);
                Keyvalue.put("useruid",useruid);
                Keyvalue.put("ordadd",ordadd);
                Keyvalue.put("grandtotal",grandtotal);
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
