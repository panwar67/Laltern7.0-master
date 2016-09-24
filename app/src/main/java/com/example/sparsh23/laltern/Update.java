package com.example.sparsh23.laltern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {

    DBHelper dbHelper;
    String DOWN_URL = "http://www.whydoweplay.com/lalten/GetImages.php";
    String DOWN_URL1 = "http://www.whydoweplay.com/lalten/GetUser.php";
    String DOWN_URL2="http://www.whydoweplay.com/lalten/GetReq.php";
    String DOWN_URL3="http://www.whydoweplay.com/lalten/GetArtisian.php";
    String DOWN_URL4 = "http://www.whydoweplay.com/lalten/Getcart.php";
    String DOWN_URL5 = "http://www.whydoweplay.com/lalten/GetSearchFilter.php";

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        dbHelper = new DBHelper(getApplicationContext());

        sessionManager = new SessionManager(getApplicationContext());

        dbHelper.InitSearchData();

        LongOperation.execute(new Runnable() {
            @Override
            public void run() {
                LongOperation longOperation = new LongOperation();
                longOperation.execute();
            }
        });
    }





    public boolean ProfileSetup(final String email, final String pass)

    {
//        final ProgressDialog loading = ProgressDialog.show(this,"Getting Profile Data...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


  //                      loading.dismiss();

                        if (s!=null)
                        {


                            dbHelper.InitProfile();

                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("Lalternusr");

                                //dbHelper.InitImg();
                                for(int i=0;i<data.length();i++)
                                {
                                    JSONObject details = data.getJSONObject(i);

                                    String uid = details.getString("UID");
                                    String name = details.getString("NAME");
                                    String comp = details.getString("COMPANY");
                                    String design = details.getString("DESIG");
                                    String tob = details.getString("BUSTYP");
                                    String pan = details.getString("PAN");
                                    String cont = details.getString("CONT");
                                    String email = details.getString("EMAIL");
                                    String addr = details.getString("ADDR");
                                    String city = details.getString("CITY");
                                    String state = details.getString("STATE");
                                    String web = details.getString("WEBS");





                                    dbHelper.InsertProfileData(uid,name,comp,design,tob,cont,email,addr,city,state,pan,web);


                                }
                                Log.d("Profile fetched", s);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }





                        }





                        //Disimissing the progress dialog

                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                      //  loading.dismiss();

                        //Showing toast
                        Toast.makeText(Update.this, "Error In Connectivity two", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("email",email);
                Keyvalue.put("pass",pass);



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

    public boolean setUpStream()

    {

        //Showing the progress dialog
      //  final ProgressDialog loading = ProgressDialog.show(this,"Getting Image Data...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                 //       loading.dismiss();
                        if (s!=null)
                        {



                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("ImageData");

                                dbHelper.InitSearchData();

                                dbHelper.InitImg();
                                for(int i=0;i<data.length();i++)
                                {
                                    JSONObject details = data.getJSONObject(i);

                                    String uid = details.getString("UID");
                                    String des = details.getString("DESCRIPTION");
                                    String path = details.getString("PATH");
                                    String own = details.getString("OWNER");
                                    dbHelper.InsertSearchTag(own," In Artist", "OWNER");
                                    String price = details.getString("PRICE");
                                    String quantity = details.getString("QUANTITY");
                                    String title = details.getString("TITLE");
                                    dbHelper.InsertSearchTag(title," In Products", "TITLE");
                                    String noimage = details.getString("NOIMAGE");
                                    String type = details.getString("TYPE");
                                    String protype = details.getString("PROTYPE");
                                    dbHelper.InsertSearchTag(protype," In Product Type", "PROTYPE");
                                    String category = details.getString("CATEGORY");
                                    String color = details.getString("COLOR");
                                    String size = details.getString("SIZE");




                                    String subcat = details.getString("SUBCAT");

                                    

                                    String meta = details.getString("META");
                                    String craft = details.getString("CRAFT");
                                    String rating = details.getString("RATING");
                                    dbHelper.InsertSearchTag(craft,"In Craft","CRAFT");



                                    dbHelper.InsertImageData(uid,des,own,path,price,quantity,title,noimage,type,category,subcat,meta,craft,protype,rating,color,size);

                                    dbHelper.InsertFilterData(category,subcat,color,protype,size);
                                }
                                Log.d("Image data", s);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }









                        }





                        //Disimissing the progress dialog

                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        //loading.dismiss();

                        //Showing toast
                        Toast.makeText(Update.this, "Error In Connectivity three", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();


                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue.add(stringRequest);
        return false;
    }


    public boolean setOrders(final String buyerid)
    {
       // final ProgressDialog loading = ProgressDialog.show(this,"Getting orders...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        //loading.dismiss();

                        dbHelper.InitOrd();

                        if (s!=null)
                        {



                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("BuyerRequest");
                                dbHelper.InitOrd();

                                for(int i=0;i<data.length();i++)
                                {
                                    JSONObject details = data.getJSONObject(i);



                                    String requid = details.getString("REQUID");
                                    String prouid = details.getString("PROUID");
                                    String quantity = details.getString("QUANTITY");
                                    String crafts = details.getString("CRAFT");
                                    String buyuid = details.getString("BUYUID");
                                    String description = details.getString("DESCRIPTION");
                                    String status = details.getString("STATUS");
                                    String path = details.getString("PATH");
                                    String reply = details.getString("REPLY");







                                    dbHelper.InsertRequestData(prouid,buyuid,requid,description,path,status,reply,crafts,quantity);


                                }
                                Log.d("Profile fetched", s);
                               // loading.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }





                        }





                        //Disimissing the progress dialog

                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        //loading.dismiss();

                        //Showing toast
                        Toast.makeText(Update.this, "Error In Connectivity four", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();

                Keyvalue.put("uid",buyerid);



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



    public boolean InsertCart(final String buyerid)
    {
        // final ProgressDialog loading = ProgressDialog.show(this,"Getting orders...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        //loading.dismiss();

                        if (s!=null)
                        {

                            dbHelper.InitCart();


                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("CartData");
                                dbHelper.InitOrd();

                                for(int i=0;i<data.length();i++)
                                {
                                    JSONObject details = data.getJSONObject(i);



                                    String requid = details.getString("CARTUID");
                                    String prouid = details.getString("PROUID");
                                    String quantity = details.getString("QUANTITY");
                                    String buyuid = details.getString("USERUID");







                                    dbHelper.InsertCartData(requid,prouid,buyuid,quantity);


                                }
                                Log.d("Profile fetched", s);
                                // loading.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            startActivity(new Intent( Update.this,NavigationMenu.class));
                            finish();





                        }





                        //Disimissing the progress dialog

                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        //loading.dismiss();

                        //Showing toast
                        Toast.makeText(Update.this, "Error In Connectivity four", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();

                Keyvalue.put("uid",buyerid);



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






    public boolean ArtisianSetup()

    {
       // final ProgressDialog loading = ProgressDialog.show(this,"Getting Artisian Data...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {




                        if (s!=null)
                        {



                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("ArtisianData");

                                dbHelper.InitArtisian();

                                for(int i=0;i<data.length();i++)
                                {
                                    JSONObject details = data.getJSONObject(i);

                                    String uid = details.getString("ART_UID");
                                    String name = details.getString("NAME");
                                    String craft = details.getString("CRAFT");
                                    String tob = details.getString("TOB");
                                    String awards = details.getString("AWARDS");
                                    String state = details.getString("STATE");
                                    String pic = details.getString("PICTURES");
                                    String noimg = details.getString("NOIMG");
                                    String description = details.getString("DESCRIPTION");
                                    String authenticity = details.getString("AUTHENTICITY");
                                    String price = details.getString("PRICE");
                                    String ratings = details.getString("RATING");



                                    dbHelper.InsertArtisian(authenticity,awards,craft,description,name,noimg,pic,price,ratings,state,tob,uid);






                                }
                                Log.d("artisit fetched", s);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }





                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Update.this, "Error In Connectivity five", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();


                Keyvalue.put("uid","uid");



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


    public boolean SearchFilterSetup()
    {
        // final ProgressDialog loading = ProgressDialog.show(this,"Getting Artisian Data...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL5,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {




                        if (s!=null)
                        {



                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("FilterSearch");

                               // dbHelper.InitArtisian();
                                dbHelper.InitFilter();

                                for(int i=0;i<data.length();i++)
                                {
                                    JSONObject details = data.getJSONObject(i);

                                    String craft = details.getString("CRAFT");
                                    String protype = details.getString("PROTYPE");

                                    dbHelper.InsertSearchFilter(craft,protype);

                                }
                                Log.d("search filter fetched", s);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }





                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Update.this, "Error In Connectivity 6", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();






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


    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            ProfileSetup(sessionManager.getUserDetails().get("email"),sessionManager.getUserDetails().get("pass"));
            setOrders(sessionManager.getUserDetails().get(SessionManager.KEY_UID));
           // Log.d("Userid for orders",""+sessionManager.getUserDetails().get(SessionManager.KEY_UID));
            ArtisianSetup();


            SearchFilterSetup();
            setUpStream();

            InsertCart(sessionManager.getUserDetails().get("uid"));



            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equals("Executed"))
            {



            }

        }

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            
        }
    }



}
