package com.example.sparsh23.laltern;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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
import com.nostra13.universalimageloader.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView carttotal;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> cartpro = new ArrayList<HashMap<String, String>>();
    ListView listView;
    String DOWN_URL2 = "http://www.whydoweplay.com/lalten/DeleteFromCart.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carttotal = (TextView)findViewById(R.id.cartprototal);
        listView = (ListView)findViewById(R.id.cartproducts);
         dbHelper = new DBHelper(getApplicationContext());
        data = dbHelper.GetCartData();




        cartpro = GetcartData(data);

        carttotal.setText("Total Items : "+cartpro.size());

        listView.setAdapter(new CartItemAdapter(CartActivity.this,cartpro));












    }





    public ArrayList<HashMap<String,String>> GetcartData( ArrayList<HashMap<String,String>> prodata)
    {
        ArrayList<HashMap<String,String>> temppro = new ArrayList<HashMap<String, String>>();

        for(int i = 0; i<data.size();i++)
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map = dbHelper.GetProMap(data.get(i).get("prouid"),data.get(i).get("quantity"));
            map.put("cartuid",data.get(i).get("cartuid"));
            temppro.add(map);
        }
        return temppro;
    }




    public boolean Deletecart(final String cartuid)
    {



        Log.d("cartuid",cartuid);
        // final ProgressDialog loading = ProgressDialog.show(this,"Getting orders...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {




                        carttotal.setText("Total Items : "+data.size());



                        Toast.makeText(getApplicationContext(),""+s.toString(),Toast.LENGTH_SHORT).show();






                        //Disimissing the progress dialog

                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        //loading.dismiss();

                        Log.d("voley error",volleyError.toString());
                        //Showing toast
                        Toast.makeText(CartActivity.this, "Error In Connectivity four", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();

                Keyvalue.put("uid",cartuid);



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

        //   return  true;
    }




}
