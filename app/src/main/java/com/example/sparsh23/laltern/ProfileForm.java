package com.example.sparsh23.laltern;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileForm extends AppCompatActivity {

    String DOWN_URL="http://www.whydoweplay.com/lalten/reguser.php";
    EditText name,company,desg,bustype,addrs,cont,pan,email,webs,state,city,uid,pass,cnfrmpass;
    SessionManager sessionManager;


    public void initvalue()
    {
        name=(EditText)findViewById(R.id.name);
        company=(EditText)findViewById(R.id.company);
        desg=(EditText)findViewById(R.id.desg);
        bustype=(EditText)findViewById(R.id.bustype);
        addrs=(EditText)findViewById(R.id.addrs);
        cont=(EditText)findViewById(R.id.cont);
        pan=(EditText)findViewById(R.id.pan);
        email=(EditText)findViewById(R.id.email);
        webs=(EditText)findViewById(R.id.webs);
        state=(EditText)findViewById(R.id.state);
        city=(EditText)findViewById(R.id.city);
        pass=(EditText)findViewById(R.id.pass);
        cnfrmpass=(EditText)findViewById(R.id.cnfrmpass);

    }

     public boolean valid()
     {
         if(name.getText().toString().isEmpty()||company.getText().toString().isEmpty()||desg.getText().toString().isEmpty()||bustype.getText().toString().isEmpty()||addrs.getText().toString().isEmpty()||cont.getText().toString().isEmpty()||pan.getText().toString().isEmpty()||email.getText().toString().isEmpty()||webs.getText().toString().isEmpty()||state.getText().toString().isEmpty()
                 ||city.getText().toString().isEmpty()||pass.getText().toString().isEmpty()||cnfrmpass.getText().toString().isEmpty())
         {
             Toast.makeText(getApplicationContext(),"All Fields are mandatory",Toast.LENGTH_SHORT).show();
         }
         else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
         {
             email.setError("Invalid E-mail Address");
         }
         else if(!pass.getText().toString().equals(pass.getText().toString()))
         {
             pass.setError("Password doesn't match");
         }
         else
         {

             upload_data(name.getText().toString(),company.getText().toString(),webs.getText().toString(),desg.getText().toString(),bustype.getText().toString(),
                     addrs.getText().toString(),cont.getText().toString(),pan.getText().toString(),email.getText().toString(),state.getText().toString(),
                     city.getText().toString(),pass.getText().toString(),new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
         }
         return false;
     }

    public void upload_data(final String name, final String company, final String webs, final String desg, final String bustype, final String addrs, final String cont, final String pan,
                            final String email, final String state, final String city, final String pass, final String uid)
    {
        final ProgressDialog loading = ProgressDialog.show(this,"Registering User...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        String res = s.replaceAll("\\s+","");


                        Toast.makeText(getApplicationContext(),s.toString(),Toast.LENGTH_LONG).show();

                        Log.d("response",s.toString());





                            if(res.equals("Uploaded"))

                            {


                                sessionManager.createLoginSession(email,pass,uid);
                                startActivity(new Intent(ProfileForm.this,Update.class));
                                finish();



                            }else if(res.equals("failed")){

                                Toast.makeText(getApplicationContext(),"Email id already exists",Toast.LENGTH_LONG).show();

                            }





                      loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(ProfileForm.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("name",name);
                Keyvalue.put("company",company);
                Keyvalue.put("desig",desg);
                Keyvalue.put("bustype",bustype);
                Keyvalue.put("addr",addrs);
                Keyvalue.put("cont",cont);
                Keyvalue.put("pan",pan);
                Keyvalue.put("email",email);
                Keyvalue.put("webs",webs);
                Keyvalue.put("state",state);
                Keyvalue.put("city",city);
                Keyvalue.put("pass",pass);


                String uid=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_form);
        initvalue();
        sessionManager = new SessionManager(getApplicationContext());

        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                valid();

            }
        });
    }



}
