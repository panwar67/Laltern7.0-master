package com.example.sparsh23.laltern;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order_Details extends AppCompatActivity {


    String DOWN_URL7 = "http://www.whydoweplay.com/lalten/Get_Order_Details.php";
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__details);

        String order_id = getIntent().getStringExtra("order_id");
        Log.d("order_id_intent",""+order_id);
        listView = (ListView)findViewById(R.id.order_detail_list);
        data = Get_Order(order_id);


    }

    public ArrayList<HashMap<String,String>> Get_Order(final String useruid)
    {
        final ArrayList<HashMap<String,String>> Arr_data = new ArrayList<HashMap<String, String>>();

        final ProgressDialog loading = ProgressDialog.show(this,"Getting Orders...","Please wait...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL7,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s)
                    {
                        if (s!=null)
                        {
                             // dbHelper.InitOrders();
                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("Order_Details");
                                // dbHelper.InitArtisian();
                                for(int i=0;i<data.length();i++)
                                {
                                    HashMap<String,String> map = new HashMap<String, String>();
                                    JSONObject details = data.getJSONObject(i);
                                    map.put("ord_id",details.getString("ORD"));
                                    map.put("pro_uid",details.getString("PRO"));
                                    map.put("title",details.getString("TITLE"));
                                    map.put("path",details.getString("PATH"));
                                    map.put("rate",details.getString("RATE"));
                                    map.put("price",details.getString("PRICE"));
                                    map.put("quantity",details.getString("QUANTITY"));
                                    map.put("user_uid",details.getString("USERUID"));
                                    map.put("status",details.getString("STATUS"));
                                    map.put("size",details.getString("SIZE"));
                                    Arr_data.add(map);

                                    //dbHelper.Insert_Orders(details.getString("ORD_UID"),details.getString("PAY_UID"),details.getString("PRO_UID"),details.getString("ORD_ADD"),details.getString("USER_UID"),details.getString("DATE_TIME"),details.getString("USER_NAME"),details.getString("PAY_MODE"),details.getString("STATUS"),details.getString("TOTAL"));
                                }
                                Log.d("orders_fetched", s);
                                // masterdata.put("filter_search",filtersearch);
                                //GetAllDataReloaded("filter_search",filtersearch);

                                loading.dismiss();

                                listView.setAdapter(new Order_Item_Adapter(getApplicationContext(),Arr_data));



                            }
                            catch (JSONException e)
                            {
                                loading.dismiss();

                                e.printStackTrace();

                            }


                        }
                        else
                        {
                            loading.dismiss();

                            Toast.makeText(getApplicationContext(),"Error Occured in Orders",Toast.LENGTH_SHORT).show();
                        }
                        // loading.dismiss();





                        //Disimissing the progress dialog

                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        // loading.dismiss();

                        //Showing toast
                        Toast.makeText(Order_Details.this, "Error In Connectivity 6", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("uid",useruid);






                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue.add(stringRequest);





        return Arr_data;



    }



}
