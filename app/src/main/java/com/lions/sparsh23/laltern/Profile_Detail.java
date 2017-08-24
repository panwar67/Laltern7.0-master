package com.lions.sparsh23.laltern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lions.sparsh23.laltern.adapters.Address_ExpandableList_Adapter;
import com.lions.sparsh23.laltern.adapters.Order_Adapter;
import com.lions.sparsh23.laltern.adapters.RequestExpandableAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile_Detail extends AppCompatActivity {

    String type;
    Button add;
    ExpandableHeightGridView listView;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    DBHelper dbHelper;
    SessionManager sessionManager;
    String DOWN_URL7 = "http://www.whydoweplay.com/lalten/Get_All_Orders.php";
    String DOWN_URL8 = "http://www.whydoweplay.com/lalten/GetReq.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__detail);
        dbHelper = new DBHelper(getApplicationContext());

        listView = (ExpandableHeightGridView) findViewById(R.id.profile_detail_list);
        listView.setExpanded(true);
        listView.setNumColumns(1);

        Intent intent = getIntent();
        add =   (Button)findViewById(R.id.add_profile_detail);
        add.setVisibility(View.VISIBLE);

        type = intent.getStringExtra("type");
        sessionManager = new SessionManager(getApplicationContext());

        //  data = (ArrayList<HashMap<String, String>>) intent.getSerializableExtra("data");



        if(type.equals("My Addresses"))
        {
            listView.setAdapter(new Address_ExpandableList_Adapter(dbHelper.GetAddresses(),getApplicationContext()));

        }
        if(type.equals("My Requests"))
        {
            add.setVisibility(View.VISIBLE);
            Get_Requests(sessionManager.getUserDetails().get("uid"));
            //listView.setAdapter(new RequestExpandableAdapter(dbHelper.GetOrders(sessionManager.getUserDetails().get("uid")),getApplicationContext()) );
        }

        if(type.equals("My Orders"))
        {

            Get_Order(sessionManager.getUserDetails().get("uid"));
            final ArrayList<HashMap<String, String>> finalData = data;
            Log.d("size_order_out",""+finalData.size());




        }

         add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(type.equals("My Addresses"))
                {
  //                  listView.setAdapter(new Address_ExpandableList_Adapter(dbHelper.GetAddresses(),getApplicationContext()));
                    startActivity(new Intent(Profile_Detail.this,Add_New_Address.class));


                }
                if(type.equals("My Requests"))
                {
                    startActivity(new Intent(Profile_Detail.this,Submit_Request_Random.class));

//                    listView.setAdapter(new RequestExpandableAdapter(dbHelper.GetOrders(sessionManager.getUserDetails().get("uid")),getApplicationContext()) );
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
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
                            try {
                                JSONObject profile = new JSONObject(s);
                                final JSONArray datas = profile.getJSONArray("Order_Received");
                                // dbHelper.InitArtisian();
                                for(int i=0;i<datas.length();i++)
                                {
                                    HashMap<String,String> map = new HashMap<String, String>();
                                    JSONObject details = datas.getJSONObject(i);
                                    map.put("orderid",details.getString("ORD_UID"));
                                    map.put("price",details.getString("TOTAL"));
                                    map.put("date",details.getString("DATE_TIME"));
                                    map.put("status",details.getString("STATUS"));
                                    Arr_data.add(map);
                                    Log.d("array_list_size",""+Arr_data.size());
                                    //dbHelper.Insert_Orders(details.getString("ORD_UID"),details.getString("PAY_UID"),details.getString("PRO_UID"),details.getString("ORD_ADD"),details.getString("USER_UID"),details.getString("DATE_TIME"),details.getString("USER_NAME"),details.getString("PAY_MODE"),details.getString("STATUS"),details.getString("TOTAL"));
                                }
                                data = Arr_data;
                                add.setVisibility(View.GONE);
                                listView.setAdapter(new Order_Adapter(Arr_data,getApplicationContext()));
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                        if(type.equals("My Orders"))
                                        {
                                            Intent intent1 = new Intent(Profile_Detail.this, Order_Details.class);
                                            intent1.putExtra("order_id", data.get(i).get("orderid"));
                                            startActivity(intent1);
                                        }
                                    }
                                });

                                Log.d("orders_fetched", s);
                                // masterdata.put("filter_search",filtersearch);
                                //GetAllDataReloaded("filter_search",filtersearch);

                                loading.dismiss();

                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                                loading.dismiss();

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
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Profile_Detail.this, "Error In Connectivity 6", Toast.LENGTH_LONG).show();
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


    public ArrayList<HashMap<String,String>> Get_Requests(final String useruid)
    {
        final ArrayList<HashMap<String,String>> Arr_data = new ArrayList<HashMap<String, String>>();

        final ProgressDialog loading = ProgressDialog.show(this,"Getting Orders...","Please wait...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL8,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s)
                    {
                        if (s!=null)
                        {
                            try {
                                JSONObject profile = new JSONObject(s);
                                final JSONArray datas = profile.getJSONArray("BuyerRequest");
                                // dbHelper.InitArtisian();
                                for(int i=0;i<datas.length();i++)
                                {
                                    HashMap<String,String> map = new HashMap<String, String>();
                                    JSONObject details = datas.getJSONObject(i);
                                    map.put("des",details.getString("DESCRIPTION"));
                                    map.put("quantity",details.getString("QUANTITY"));
                                    map.put("craft",details.getString("CRAFT"));
                                    map.put("status",details.getString("STATUS"));
                                    map.put("path",details.getString("PATH"));
                                    Arr_data.add(map);
                                    Log.d("array_list_size_req",""+Arr_data.size());
                                    //dbHelper.Insert_Orders(details.getString("ORD_UID"),details.getString("PAY_UID"),details.getString("PRO_UID"),details.getString("ORD_ADD"),details.getString("USER_UID"),details.getString("DATE_TIME"),details.getString("USER_NAME"),details.getString("PAY_MODE"),details.getString("STATUS"),details.getString("TOTAL"));
                                }
                                data = Arr_data;
                                add.setVisibility(View.GONE);
                                listView.setAdapter(new RequestExpandableAdapter(Arr_data,getApplicationContext()));


                                Log.d("orders_fetched", s);
                                // masterdata.put("filter_search",filtersearch);
                                //GetAllDataReloaded("filter_search",filtersearch);

                                loading.dismiss();

                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                                loading.dismiss();
                                Toast.makeText(getApplicationContext(),"Error occured, please try again",Toast.LENGTH_SHORT).show();

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
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Profile_Detail.this, "Error In Connectivity ", Toast.LENGTH_LONG).show();
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
