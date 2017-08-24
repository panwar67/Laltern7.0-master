package com.lions.sparsh23.laltern;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Add_New_Address extends AppCompatActivity {


    String DOWN_URL = "http://www.whydoweplay.com/lalten/InsertAddr.php";
    EditText title, addr, area, city, dist, state, pin, cont, country;
    DBHelper dbHelper;
    SessionManager sessionManager;
    Button save, back, add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__new__address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(getApplicationContext());

        dbHelper = new DBHelper(getApplicationContext());
        save = (Button)findViewById(R.id.add_new_addr);
        title = (EditText)findViewById(R.id.enter_addr_title);
        addr = (EditText)findViewById(R.id.enter_addr);
        area = (EditText)findViewById(R.id.enter_addr_area);
        city = (EditText)findViewById(R.id.enter_addr_city);
        dist = (EditText)findViewById(R.id.enter_addr_dist);
        pin = (EditText)findViewById(R.id.enter_addr_pin);
        state = (EditText)findViewById(R.id.enter_addr_state);
        cont = (EditText)findViewById(R.id.enter_addr_cont);
        country = (EditText)findViewById(R.id.enter_addr_country);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.InsertAddr(title.getText().toString(),addr.getText().toString(),
                        area.getText().toString(),city.getText().toString(),
                        dist.getText().toString(),state.getText().toString(),
                        pin.getText().toString(),cont.getText().toString(),country.getText().toString());
                upload_data(title.getText().toString(),addr.getText().toString(),
                        area.getText().toString(),city.getText().toString(),
                        dist.getText().toString(),
                        state.getText().toString(),
                        pin.getText().toString(),cont.getText().toString(),
                        sessionManager.getUserDetails().get("uid"),country.getText().toString());
               // updatespinner();

            }
        });

        back = (Button)findViewById(R.id.back_add);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });




    }



    public void upload_data(final String title, final String addr, final String area, final String city,final String dist, final String state, final String pin, final  String cont, final String useruid, final String country )
    {
        final ProgressDialog loading = ProgressDialog.show(Add_New_Address.this,"Adding Addresses...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(getApplicationContext(),s.toString(),Toast.LENGTH_LONG).show();
                        Log.d("response",s.toString());
                        onBackPressed();
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        //Showing toast
                        Toast.makeText(getApplicationContext(), "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String uid=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("name",title);
                Keyvalue.put("addr",addr);
                Keyvalue.put("area",area);
                Keyvalue.put("city",city);
                Keyvalue.put("dist",dist);
                Keyvalue.put("state",state);
                Keyvalue.put("pin",pin);
                Keyvalue.put("cont",cont);
                Keyvalue.put("useruid",useruid);
                Keyvalue.put("uid",uid);
                Keyvalue.put("country",country);

                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
