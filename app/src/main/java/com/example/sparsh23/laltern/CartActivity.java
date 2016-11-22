package com.example.sparsh23.laltern;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView carttotal;
    ImageView back;
    Button checkout;
    double totals, tax, sub;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> cartpro = new ArrayList<HashMap<String, String>>();
    ExpandableHeightGridView listView;
    TextView subtotal, taxtotal, grandtotal;
    String DOWN_URL2 = "http://www.whydoweplay.com/lalten/DeleteFromCart.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carttotal = (TextView)findViewById(R.id.cartprototal);
        back = (ImageView)findViewById(R.id.cartback);
        checkout = (Button)findViewById(R.id.checkout);

        listView = (ExpandableHeightGridView)findViewById(R.id.cartproducts);
         dbHelper = new DBHelper(getApplicationContext());
        data = dbHelper.GetCartData();

        subtotal = (TextView)findViewById(R.id.cartsubtotal);
        taxtotal = (TextView)findViewById(R.id.carttaxtotal);
        grandtotal = (TextView)findViewById(R.id.cartgrandtotal);

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),"Raleway-SemiBold.ttf");

        subtotal.setTypeface(typeface);
        taxtotal.setTypeface(typeface);
        grandtotal.setTypeface(typeface);

        cartpro = GetcartData();
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(data.size()>0) {



                    startActivity(new Intent(CartActivity.this,
                            Checkout_Page.class).putExtra("total",totals).putExtra("sub",sub).putExtra("tax",tax));
                    finish();
                }

                if(data.size()==0)
                {


                    Toast.makeText(getApplicationContext(),"Cart is empty",Toast.LENGTH_SHORT);
                }



            }
        });


        Populate_Cart(cartpro);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    public ArrayList<HashMap<String,String>> GetcartData()
    {
        ArrayList<HashMap<String,String>> temppro = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i<data.size();i++)
        {
            HashMap<String,String> map = new HashMap<String, String>();
            map = dbHelper.GetProMap(data.get(i).get("prouid"),data.get(i).get("quantity"),data.get(i).get("size"));
            map.put("cartuid",data.get(i).get("cartuid"));
            temppro.add(map);
        }

        return temppro;

    }


    public boolean Calculate_Total_Price()
    {
        double total=0;
        double taxes=0;
        for(int i=0;i<cartpro.size();i++)
        {
            int qty = Integer.parseInt(cartpro.get(i).get("quantity"));
            double rate = Float.parseFloat(cartpro.get(i).get("price"));
            double aux_total = qty*rate;
            total = aux_total+total;

            sub = total;

        }
        subtotal.setText("Sub Total  :   "+total);
        taxes = (total*13.5)/100;
        tax=taxes;
        taxtotal.setText("Total Taxes :     "+taxes);
        double total_grand = total+taxes;
        totals = total_grand;
        grandtotal.setText("Total :     "+total_grand);
        return true;
    }




    public boolean Deletecart(final String cartuid)
    {



        Log.d("cartuid",cartuid);
        // final ProgressDialog loading = ProgressDialog.show(this,"Getting orders...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {




                        int total = data.size()-1;
                        carttotal.setText("Total Items : "+total);



                        Toast.makeText(getApplicationContext(),""+s.toString(),Toast.LENGTH_SHORT).show();
                        Calculate_Total_Price();






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

    public boolean Populate_Cart(ArrayList<HashMap<String,String>> data)
    {

        CartItemAdapter cartItemAdapter = new CartItemAdapter(CartActivity.this,data);
        listView.setAdapter(cartItemAdapter);
        listView.setExpanded(true);
        listView.setNumColumns(1);
        cartItemAdapter.notifyDataSetChanged();

        carttotal.setText("Total Items : "+cartpro.size());

        Calculate_Total_Price();




        return  true;
    }




}
