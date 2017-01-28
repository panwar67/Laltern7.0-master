package com.example.sparsh23.laltern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    String DOWN_URL6 = "http://www.whydoweplay.com/lalten/GetAddr.php";
    String DOWN_URL7 = "http://www.whydoweplay.com/lalten/Get_Orders.php";
    String DOWN_URL8 = "http://www.whydoweplay.com/lalten/Login_User.php";

    SessionManager sessionManager;
    HashMap<String,ArrayList<HashMap<String,String>>> masterdataa = new HashMap<String, ArrayList<HashMap<String,String>>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update);
        dbHelper = new DBHelper(getApplicationContext());


       // String path = "android.resource://" + getPackageName() + "/" + R.raw.lal_vid;


        sessionManager = new SessionManager(getApplicationContext());

       // LongOperation longOperation = new LongOperation();
       // longOperation.execute();




     //   dbHelper.InitSearchData();

       // GetAllData();


        Log_In_User(sessionManager.getUserDetails().get("email"),sessionManager.getUserDetails().get("uid"));
        setOrders(sessionManager.getUserDetails().get(SessionManager.KEY_UID));
        Log.d("Userid for orders",""+sessionManager.getUserDetails().get(SessionManager.KEY_UID));


        ArtisianSetup();
        Order_Setup(sessionManager.getUserDetails().get("uid"));

        SearchFilterSetup();
        setUpStream();
        InsertAddr(sessionManager.getUserDetails().get(SessionManager.KEY_UID));

        CountDownTimer timer;

        timer = new CountDownTimer(4000, 20) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                try{

                    startActivity(new Intent(Update.this,NavigationMenu.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();

                }catch(Exception e){
                    Log.e("Error", "Error: " + e.toString());
                }
            }
        };

        if(InsertCart(sessionManager.getUserDetails().get("uid")))
        {

            timer.start();

        }


        //ProfileSetup(sessionManager.getUserDetails().get("email"),sessionManager.getUserDetails().get("pass"));


    }




    public boolean Log_In_User(final String email, final String uid)
    {

        StringRequest stringRequest4 = new StringRequest(Request.Method.POST, DOWN_URL8,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {



                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        //  loading.dismiss();

                        //Showing toast
                        Toast.makeText(Update.this, "Error In Connectivity ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("email",email);
                Keyvalue.put("uid",uid);



                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue4 = Volley.newRequestQueue(this);
        stringRequest4.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue4.add(stringRequest4);

        return true;
    }

    public boolean ProfileSetup(final String email, final String pass)

    {
//        final ProgressDialog loading = ProgressDialog.show(this,"Getting Profile Data...","Please wait...",false,false);
        StringRequest stringRequest4 = new StringRequest(Request.Method.POST, DOWN_URL1,
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
        RequestQueue requestQueue4 = Volley.newRequestQueue(this);
        stringRequest4.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue4.add(stringRequest4);






        return true;
    }

    public boolean setUpStream()

    {
        final HashMap<String,ArrayList<HashMap<String,String>>> masterdata = new HashMap<String, ArrayList<HashMap<String,String>>>();


        final        ArrayList<HashMap<String,String>> imgdata = new ArrayList<HashMap<String, String>>();
    final     ArrayList<HashMap<String,String>> filterdata = new ArrayList<HashMap<String,String>>();
      final  ArrayList<HashMap<String,String>> SearchData = new ArrayList<HashMap<String, String>>();

        //Showing the progress dialog
      //  final ProgressDialog loading = ProgressDialog.show(this,"Getting Image Data...","Please wait...",false,false);
        StringRequest stringRequest5 = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                 //       loading.dismiss();

                        //       loading.dismiss();
                        if (s!=null)
                        {



                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("ImageData");

                                dbHelper.InitSearchData();
                                dbHelper.InitFilter();



                                dbHelper.InitImg();
                                for(int i=0;i<data.length();i++)
                                {
                                    HashMap<String,String> map = new HashMap<String, String>();
                                    HashMap<String,String> filmap = new HashMap<String, String>();
                                    HashMap<String,String> searchmapown = new HashMap<String, String>();
                                    HashMap<String,String> searchmapcraft = new HashMap<String, String>();
                                    HashMap<String,String> searchmapprotype = new HashMap<String, String>();
                                    HashMap<String,String> searchmaptitle = new HashMap<String, String>();
                                    JSONObject details = data.getJSONObject(i);
                                    String uid = details.getString("UID");
                                    String des = details.getString("DESCRIPTION");
                                    String path = details.getString("PATH");
                                    String own = details.getString("OWNER");
                                    String own_name = details.getString("OWN_NAME");
                                    searchmapown.put("tag",own_name);
                                    searchmapown.put("suggest","In artist");
                                    searchmapown.put("type","OWN_NAME");
                                    SearchData.add(searchmapown);
                                    //searchmap.put("")
                                    //     dbHelper.InsertSearchTag(own," In Artist", "OWNER");

                                    String price = details.getString("PRICE");
                                    String quantity = details.getString("QUANTITY");
                                    String title = details.getString("TITLE");
                                    //   dbHelper.InsertSearchTag(title," In Products", "TITLE");
                                    searchmaptitle.put("tag",title);
                                    searchmaptitle.put("suggest","In Products");
                                    searchmaptitle.put("type","TITLE");
                                    SearchData.add(searchmaptitle);
                                    String noimage = details.getString("NOIMAGE");
                                    String type = details.getString("TYPE");
                                    String protype = details.getString("PROTYPE");
                                    searchmapprotype.put("tag",protype);
                                    searchmapprotype.put("suggest","In Product Type");
                                    searchmapprotype.put("type","PROTYPE");
                                    SearchData.add(searchmapprotype);
                                    // dbHelper.InsertSearchTag(protype," In Product Type", "PROTYPE");
                                    String category = details.getString("CATEGORY");
                                    String color = details.getString("COLOR");
                                    String size = details.getString("SIZE");
                                    String subcat = details.getString("SUBCAT");
                                    String meta = details.getString("META");
                                    String craft = details.getString("CRAFT");
                                    String rating = details.getString("RATING");
                                    // dbHelper.InsertSearchTag(craft,"In Craft","CRAFT");
                                    searchmapcraft.put("tag",craft);
                                    searchmapcraft.put("suggest","In Crafts");
                                    searchmapcraft.put("type","CRAFT");
                                    SearchData.add(searchmapcraft);
                                    //  dbHelper.InsertImageData(uid,des,own,path,price,quantity,title,noimage,type,category,subcat,meta,craft,protype,rating,color,size,details.getString("REVPRICE"),details.getString("REVQUANTITY"));
                                    //dbHelper.InsertFilterData(category,subcat,color,protype,size);
                                    map.put("uid",uid);
                                    map.put("des",des);
                                    map.put("path",path);
                                    map.put("own",own);
                                    map.put("own_name",own_name);
                                    map.put("price",price);
                                    map.put("quantity",quantity);
                                    map.put("title",title);
                                    map.put("noimg",noimage);
                                    map.put("type",type);
                                    map.put("protype",protype);
                                    map.put("category",category);
                                    map.put("color",color);
                                    map.put("size",size);
                                    map.put("subcat",subcat);
                                    map.put("meta",meta);
                                    map.put("craft",craft);
                                    map.put("rating",rating);
                                    map.put("revprice",details.getString("REVPRICE"));
                                    map.put("revquantity",details.getString("REVQUANTITY"));
                                    //,details.getString("REVQUANTITY")
                                    map.put("sizeavail",details.getString("SIZEAVAIL"));
                                    map.put("stock",details.getString("STOCK"));


                                    imgdata.add(map);

                                    filmap.put("category",category);
                                    filmap.put("subcat",subcat);
                                    filmap.put("color",color);
                                    filmap.put("protype",protype);
                                    filmap.put("size",size);

                                    filterdata.add(filmap);

                                }
                                Log.d("Image data", s);


                                dbHelper.InsertSearchTag(SearchData);
                                dbHelper.InsertImageData(imgdata);
                                dbHelper.InsertFilterData(filterdata);
                                Log.d("data_img_size",""+imgdata.size());

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
                        Toast.makeText(Update.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Update.this,No_Internet_Connection.class));
                        finish();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();


                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue5 = Volley.newRequestQueue(this);
        stringRequest5.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue5.add(stringRequest5);
        return true;
    }


    public boolean setOrders(final String buyerid)
    {
        final ArrayList<HashMap<String,String>> buyreq = new ArrayList<HashMap<String, String>>();

        // final ProgressDialog loading = ProgressDialog.show(this,"Getting orders...","Please wait...",false,false);
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, DOWN_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        //loading.dismiss();

                        dbHelper.InitOrd();

                        if (s!=null)
                        {

                            //loading.dismiss();


                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("BuyerRequest");
                                dbHelper.InitOrd();

                                for(int i=0;i<data.length();i++)
                                {

                                    HashMap<String,String> map = new HashMap<String, String>();
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
//                                masterdata.put("buy_request",buyreq);




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
        RequestQueue requestQueue3 = Volley.newRequestQueue(this);
        stringRequest3.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue3.add(stringRequest3);






        return true;

     //   return  true;
    }


    public boolean InsertAddr(final String buyerid)
    {
        final ArrayList<HashMap<String,String>> cartdata = new ArrayList<HashMap<String, String>>();

        // final ProgressDialog loading = ProgressDialog.show(this,"Getting orders...","Please wait...",false,false);
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, DOWN_URL6,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        //loading.dismiss();

                        if (s!=null)
                        {

                            dbHelper.InitAddr();
                            // HashMap<String,String> map = new HashMap<String, String>();



                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("Addr");
                                // dbHelper.InitOrd();
                                //dbHelper.InitCart();

                                for(int i=0;i<data.length();i++)
                                {

                                    HashMap<String,String> map = new HashMap<String, String>();
                                    JSONObject details = data.getJSONObject(i);

                                  //  dbHelper.InsertCartData(requid,prouid,buyuid,quantity,size);

                                    dbHelper.InsertAddr(details.getString("NAME"),details.getString("ADDR"),details.getString("AREA"),
                                    details.getString("CITY"),details.getString("DIST"),details.getString("STATE"),
                                            details.getString("PINCODE"),details.getString("CONT"),details.getString("COUNTRY"));
                                    /*map.put("cartuid",requid);
                                    map.put("prouid",prouid);
                                    map.put("quantity",quantity);
                                    map.put("useruid",buyuid);

                                    cartdata.add(map);

                                    Log.d("check","for data");
                                    //masterdata.put("cart_data",cartdata);
                                   // Log.d("master_data_cart",""+masterdata.size());
                                    GetAllDataReloaded("cart_data",cartdata);
                                    */
                                }

                                Log.d("Address_Fetched", s);
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



                        Toast.makeText(Update.this, "Error In Connectivity four", Toast.LENGTH_SHORT).show();



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
        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        stringRequest2.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue2.add(stringRequest2);






        return true;

        //   return  true;
    }


    public boolean InsertCart(final String buyerid)
    {

        dbHelper.InitCart();
        final ArrayList<HashMap<String,String>> cartdata = new ArrayList<HashMap<String, String>>();

        // final ProgressDialog loading = ProgressDialog.show(this,"Getting orders...","Please wait...",false,false);
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, DOWN_URL4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        //loading.dismiss();

                        if (s!=null)
                        {


                            // HashMap<String,String> map = new HashMap<String, String>();



                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("CartData");
                               // dbHelper.InitOrd();


                                for(int i=0;i<data.length();i++)
                                {

                                    HashMap<String,String> map = new HashMap<String, String>();
                                    JSONObject details = data.getJSONObject(i);
                                    String requid = details.getString("CARTUID");
                                    String prouid = details.getString("PROUID");
                                    String quantity = details.getString("QUANTITY");
                                    String buyuid = details.getString("USERUID");
                                    String size = details.getString("SIZE");
                                    dbHelper.InsertCartData(requid,prouid,buyuid,quantity,size);

                                    /*map.put("cartuid",requid);
                                    map.put("prouid",prouid);
                                    map.put("quantity",quantity);
                                    map.put("useruid",buyuid);

                                    cartdata.add(map);

                                    Log.d("check","for data");
                                    //masterdata.put("cart_data",cartdata);
                                   // Log.d("master_data_cart",""+masterdata.size());
                                    GetAllDataReloaded("cart_data",cartdata);
                                    */
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



                        Toast.makeText(Update.this, "Error In Connectivity four", Toast.LENGTH_SHORT).show();



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
        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        stringRequest2.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue2.add(stringRequest2);






        return true;

        //   return  true;
    }






    public boolean ArtisianSetup()

    {

        final        ArrayList<HashMap<String,String>> artistdata = new ArrayList<HashMap<String, String>>();

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
                                    HashMap<String, String> map = new HashMap<String, String>();
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

                                    /*map.put("uid",uid);
                                    map.put("name",name);
                                    map.put("craft",craft);
                                    map.put("tob",tob);
                                    map.put("awards",awards);
                                    map.put("state",state);
                                    map.put("pic",pic);
                                    map.put("noimg",noimg);
                                    map.put("des",description);
                                    map.put("authen",authenticity);
                                    map.put("price",price);
                                    map.put("ratings",ratings);
                                    artistdata.add(map);
                                    */

                                }
                                Log.d("artisit fetched", s);

                               // masterdata.put("artist_data",artistdata);
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
        final        ArrayList<HashMap<String,String>> filtersearch = new ArrayList<HashMap<String, String>>();

        // final ProgressDia
        // log loading = ProgressDialog.show(this,"Getting Artisian Data...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL5,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {




                        if (s!=null)
                        {



                            dbHelper.InitSearchFilter();


                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("FilterSearch");

                                // dbHelper.InitArtisian();

                                for(int i=0;i<data.length();i++)
                                {
                                    HashMap<String,String> map = new HashMap<String, String>();
                                    JSONObject details = data.getJSONObject(i);

                                    String craft = details.getString("CRAFT");
                                    String protype = details.getString("PROTYPE");

                                    dbHelper.InsertSearchFilter(craft,protype);

                                   /* map.put("craft",craft);
                                    map.put("protype",protype);
                                    filtersearch.add(map);

                                   // Log.d("check","for data");
                                   */

                                }
                                Log.d("search filter fetched", s);
                               // masterdata.put("filter_search",filtersearch);
                                //GetAllDataReloaded("filter_search",filtersearch);


                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                        }                        else
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


    public boolean Order_Setup(final String uid)
    {
        // final ProgressDia
        // log loading = ProgressDialog.show(this,"Getting Artisian Data...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL7,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        if (s!=null)
                        {
                            dbHelper.InitOrders();
                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("Order_Received");

                                // dbHelper.InitArtisian();

                                for(int i=0;i<data.length();i++)
                                {
                                    HashMap<String,String> map = new HashMap<String, String>();
                                    JSONObject details = data.getJSONObject(i);
                                    dbHelper.Insert_Orders(details.getString("ORD_UID"),details.getString("PAY_UID"),details.getString("PRO_UID"),details.getString("ORD_ADD"),details.getString("USER_UID"),details.getString("DATE_TIME"),details.getString("USER_NAME"),details.getString("PAY_MODE"),details.getString("STATUS"),details.getString("TOTAL"));


                                }
                                Log.d("orders_fetched", s);
                                // masterdata.put("filter_search",filtersearch);
                                //GetAllDataReloaded("filter_search",filtersearch);


                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                        }
                        else
                        {

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
                        Toast.makeText(Update.this, "Error In Connectivity 6", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("uid",uid);






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


    public void Update_Database( HashMap<String,ArrayList<HashMap<String,String>>> data)
    {
        if (data!=null)
        {
            ArrayList<HashMap<String,String>> imgdata = new ArrayList<HashMap<String, String>>();
            ArrayList<HashMap<String,String>> filter_search_data = new ArrayList<HashMap<String, String>>();
            ArrayList<HashMap<String,String>> cart_data = new ArrayList<HashMap<String, String>>();
            ArrayList<HashMap<String,String>> request_data = new ArrayList<HashMap<String, String>>();
            ArrayList<HashMap<String,String>> artist_data = new ArrayList<HashMap<String, String>>();
            ArrayList<HashMap<String,String>> filter_data = new ArrayList<HashMap<String, String>>();
            ArrayList<HashMap<String,String>> Search_data = new ArrayList<HashMap<String, String>>();
            imgdata= data.get("img_data");
            filter_data = data.get("filter_data");
            cart_data = data.get("cart_data");
            request_data = data.get("buy_request");
            artist_data = data.get("artist_data");
            filter_search_data = data.get("filter_search");
            Search_data = data.get("search_data");

            if(Search_data!=null)
            {
                dbHelper.InsertSearchTag(Search_data);

                    }



            if(imgdata!=null)
            {
                dbHelper.InsertImageData(imgdata);

            }

            if(filter_data!=null)
            {

            dbHelper.InsertFilterData(filter_data);

            }


            if(request_data!=null)
            {

                for (int i=0;i<request_data.size();i++)
                {

                    dbHelper.InsertRequestData(request_data.get(i).get("prouid"),request_data.get(i).get("buyuid"),
                            request_data.get(i).get("requid"),request_data.get(i).get("des"),request_data.get(i).get("path"),request_data.get(i).get("status"),request_data.get(i).get("reply"),request_data.get(i).get("craft"),request_data.get(i).get("quantity"));
                }


            }

            if(cart_data!=null)
            {

                Log.d("cartsize_getall",""+cart_data.size());
                for (int i=0;i<cart_data.size();i++)
                {
                   // dbHelper.InsertCartData(cart_data.get(i).get("cartuid"),cart_data.get(i).get("prouid"),cart_data.get(i).get("useruid"),cart_data.get(i).get("quantity"));
                }
            }

            if(filter_search_data!=null)
            {
                for (int i=0;i<filter_search_data.size();i++)
                {
                    dbHelper.InsertSearchFilter(filter_search_data.get(i).get("craft"),filter_search_data.get(i).get("protype"));

                }
            }
            if(artist_data!=null)
            {
                for (int i=0;i<artist_data.size();i++)
                {
                        dbHelper.InsertArtisian(artist_data.get(i).get("authen"),artist_data.get(i).get("awards"),
                        artist_data.get(i).get("craft"),artist_data.get(i).get("des"),artist_data.get(i).get("name"),
                        artist_data.get(i).get("noimg"),artist_data.get(i).get("pic"),
                        artist_data.get(i).get("price"),artist_data.get(i).get("ratings"),
                        artist_data.get(i).get("state"),artist_data.get(i).get("tob"),artist_data.get(i).get("uid"));
                    Log.d("check_noimg",""+artist_data.get(i).get("noimg").toString());
                }
            }
        }
    }



    public HashMap<String, ArrayList<HashMap<String, String>>> GetAllDataReloaded(String tag, ArrayList<HashMap<String,String>> data)
    {
        if(tag!=null&data!=null) {


            masterdataa.put(tag, data);
            Log.d("size_reloaded", "" + masterdataa.size());
        }
        //Update_Database(masterdataa);

        return masterdataa;
    }



}
