package com.example.sparsh23.laltern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import com.nostra13.universalimageloader.utils.L;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ProofOfPayment;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Checkout_Page extends AppCompatActivity  implements PaymentResultListener {

    Spinner spinner;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> pro_data = new ArrayList<HashMap<String, String>>();
    DBHelper dbHelper;
    LinearLayout AddressLayout, offlinecheckout;
    Button save, addmore;
    TextView sub, tax, total;
    ImageView back;
    ImageButton card, paypal_checkout;
    JSONArray Cart_Data = new JSONArray();



    private Tracker mTracker;

    String grand_total;
    EditText title, addr, area, city, dist, state, pin, cont, country;
    SessionManager sessionManager;
    String DOWN_URL = "http://www.whydoweplay.com/lalten/InsertAddr.php";
    String DOWN_URL2 = "http://www.4liontechosolutions.com/Receive_Order.php";
    String DOWN_URL3 = "http://www.whydoweplay.com/lalten/Generating_Invoice.php";
    String DOWN_URL4 = "http://www.whydoweplay.com/lalten/catchrazor.php";
    private boolean _paypalLibraryInit = false;
   private static PayPalConfiguration   config = new PayPalConfiguration()
    // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
    // or live (ENVIRONMENT_PRODUCTION)
    .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
    .clientId("AS9HodPhOD6z1mWZJh7cxvVQe1VIpYE5ehvEFqbkh8k_xyHw5Uha1kXCuTBbYXiLzu2T0UmWDcaUyp-T");

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout__page);


       // Init_Paypal();

        sessionManager = new SessionManager(getApplicationContext());
        card = (ImageButton)findViewById(R.id.card_payment);
        AddressLayout = (LinearLayout)findViewById(R.id.addr_new);
        offlinecheckout = (LinearLayout)findViewById(R.id.offline_checkout);
        spinner = (Spinner)findViewById(R.id.checkout_addr);
        dbHelper = new DBHelper(getApplicationContext());
        save = (Button)findViewById(R.id.add_new_addr);
        title = (EditText)findViewById(R.id.enter_addr_title);
        addr = (EditText)findViewById(R.id.enter_addr);
        area = (EditText)findViewById(R.id.enter_addr_area);
        city = (EditText)findViewById(R.id.enter_addr_city);
        dist = (EditText)findViewById(R.id.enter_addr_dist);
        pin = (EditText)findViewById(R.id.enter_addr_pin);
        sub = (TextView)findViewById(R.id.cartsubtotal);
        tax = (TextView)findViewById(R.id.carttaxtotal);
        total = (TextView)findViewById(R.id.cartgrandtotal);
        back    =   (ImageView)findViewById(R.id.checkoutback);
        country =   (EditText)findViewById(R.id.enter_addr_country);
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                onBackPressed();
                return false;
            }
        });

        Cart_Data = dbHelper.GetCartDataJson();

        AnalyticsApplication application = (AnalyticsApplication)getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.setScreenName("On_checkout");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Intent intent = getIntent();

        sub.setText( "Sub Total : "+intent.getDoubleExtra("sub",0));
        total.setText("Taxes : "+intent.getDoubleExtra("tax",0));
        tax.setText("Grand Total : "+intent.getDoubleExtra("total",0));
        final String total = ""+intent.getDoubleExtra("total",0);
        grand_total = total;
        pro_data = (ArrayList<HashMap<String,String>>) intent.getSerializableExtra("prolist");

        state = (EditText)findViewById(R.id.enter_addr_state);
        cont = (EditText)findViewById(R.id.enter_addr_cont);
        addmore = (Button)findViewById(R.id.add_more_addr);
        data = dbHelper.GetAddresses();


        if (data.size()==0)
        {
            AddressLayout.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.INVISIBLE);
        }
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner.getCount()!=0) {
                    startPayment();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Add Shipping Address",Toast.LENGTH_LONG).show();
                }
                }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTracker.setScreenName("new_add_checkout");
                mTracker.send(new HitBuilders.EventBuilder().build());
                dbHelper.InsertAddr(title.getText().toString(),addr.getText().toString(),area.getText().toString(),city.getText().toString(),dist.getText().toString(),state.getText().toString(),pin.getText().toString(),cont.getText().toString(),country.getText().toString());
                        upload_data(title.getText().toString(),addr.getText().toString(), area.getText().toString(),city.getText().toString(),dist.getText().toString(), state.getText().toString(),pin.getText().toString(),cont.getText().toString(),sessionManager.getUserDetails().get("uid"),country.getText().toString());
                    updatespinner();
            }
        });
        spinner.setAdapter(new Checkout_Addr_Adapter(getApplicationContext(),data));
        addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressLayout.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
                addmore.setVisibility(View.GONE);
            }
        });

        paypal_checkout = (ImageButton)findViewById(R.id.paypal_checkout);
        paypal_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(spinner.getCount()!=0) {
                    getPayment();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Add Shipping Address",Toast.LENGTH_LONG).show();
                }

            }
        });

               Intent intent_pay = new Intent(this, PayPalService.class);

        intent_pay.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent_pay);

        offlinecheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(spinner.getCount()!=0) {


                    HashMap<String, String> map = new HashMap<String, String>();
                    //map = ;
                    map.put("price", total);
                   // map.put("order_id", uid);
                    map.put("product_list", Cart_Data.toString());
                    map.put("useradd", spinner.getSelectedItem().toString());
                    map.put("useruid", sessionManager.getUserDetails().get("uid"));
                    map.put("username",sessionManager.getUserDetails().get("name"));
                    map.put("user_email",sessionManager.getUserDetails().get("email"));

                    Intent intent1 = new Intent(Checkout_Page.this, Choose_Offline_Payment_Mode.class);
                    intent1.putExtra("checkout",map);
                    intent1.putExtra("checkout_add",(HashMap<String, String>) spinner.getSelectedItem());
                    startActivity(intent1);

                }else
                {
                    Toast.makeText(getApplicationContext(),"Please Add Shipping Address",Toast.LENGTH_LONG).show();

                }
            }
        });




    }

    private void getPayment() {
        //Getting the amount from editText
        //paymentAmount = editTextAmount.getText().toString();

        //Creating a paypalpayment
        com.paypal.android.sdk.payments.PayPalPayment payment =  new PayPalPayment(new BigDecimal(String.valueOf("1")), "USD", "Testing fee", com.paypal.android.sdk.payments.PayPalPayment.PAYMENT_INTENT_SALE);
        //com.paypal.android.sdk.payments.PayPalPayment
        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, 76);
    }

    public void Init_Paypal()
    {
         /*pp = PayPal.getInstance();





        // If the library is already initialized, then we don't need to
        // initialize it again.

            // This is the main initialization call that takes in your Context,
            // the Application ID, and the server you would like to connect to.
            pp = PayPal.initWithAppID(this, "Ac-BZVBz92hMs5VFLt266Z4w9WqJgR1i1zAjQPO6iSW4TN_NifUw9inxlp3iX94UsOggJHk3ESoXf60o",
                    PayPal.ENV_LIVE);

            // -- These are required settings.
            pp.setLanguage("en_IN"); // Sets the language for the library.
            // --

            // -- These are a few of the optional settings.
            // Sets the fees payer. If there are fees for the transaction, this
            // person will pay for them. Possible values are FEEPAYER_SENDER,
            // FEEPAYER_PRIMARYRECEIVER, FEEPAYER_EACHRECEIVER, and
            // FEEPAYER_SECONDARYONLY.
           // pp.setFeesPayer(PayPal.FEEPAYER_PRIMARYRECEIVER);
            // Set to true if the transaction will require shipping.
            pp.setShippingEnabled(false);
            // Dynamic Amount Calculation allows you to set tax and shipping
            // amounts based on the user's shipping address. Shipping must be
            // enabled for Dynamic Amount Calculation. This also requires you to
            // create a class that implements PaymentAdjuster and Serializable.
            pp.setDynamicAmountCalculationEnabled(false);
            // --
            _paypalLibraryInit = true;

     //  CheckoutButton checkoutButton = pp.getCheckoutButton(this,PayPal.BUTTON_294x45, CheckoutButton.TEXT_PAY);
       // LinearLayout.LayoutParams params = new
         //       LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
           //     LinearLayout.LayoutParams.WRAP_CONTENT);

        //checkoutButton.setLayoutParams(params);

        //LinearLayout linearLayout = (LinearLayout)findViewById(R.id.faltu_lay);
        //linearLayout.addView(checkoutButton);

        //checkoutButton.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View view) {
            //    Pay_By_PayPal();
            //}
        //});

        */

    }

    public void Pay_By_PayPal()
    {
      /*  String uid=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        PayPalPayment payPalPayment = new PayPalPayment();
        payPalPayment.setCurrencyType("USD");
        payPalPayment.setRecipient("maneet.lal10@gmail.com");
        payPalPayment.setMerchantName("LAL10");
        payPalPayment.setSubtotal(new BigDecimal("1.00"));
        //PayPalInvoiceItem payPalInvoiceItem = new PayPalInvoiceItem();
        //payPalInvoiceItem.setID("User_id : "+sessionManager.getUserDetails().get("uid")+" | pay_id : "+uid);

        //PayPalInvoiceData payPalInvoiceData = new PayPalInvoiceData();
       // payPalInvoiceData.add(payPalInvoiceItem);
        payPalPayment.setPaymentType(PayPal.PAYMENT_TYPE_PERSONAL);
       // payPalPayment.setInvoiceData(payPalInvoiceData);
        Intent checkoutIntent = PayPal.getInstance()
                .checkout(payPalPayment, this /*, new ResultDelegate());

        startActivityForResult(checkoutIntent, 67);


        */


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 76) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    //Getting the payment details
                    String paymentDetails = confirm.toJSONObject().toString();
                    try {
                        Log.i("confirm_paypal", confirm.getPayment().toJSONObject().toString(4));
                        ProofOfPayment jsonObject = confirm.getProofOfPayment();

                        String payid =  jsonObject.getPaymentId();
                        Log.d("payuid_paypal",""+payid);
                        Log.i("confirm_paypal_2", confirm.toJSONObject().toString(4));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getApplicationContext(),"payment successful",Toast.LENGTH_SHORT).show();
                    Log.i("paymentExample", paymentDetails);

                    Generate_Order(confirm.getProofOfPayment().getPaymentId().toString(),sessionManager.getUserDetails().get("uid"),spinner.getSelectedItem().toString(),grand_total,"Not Delivered","PayPal",grand_total);

                    //Starting a new activity for the payment details and also putting the payment details with intent


                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
                Toast.makeText(getApplicationContext(),"Payment Cancelled by User",Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

                Toast.makeText(getApplicationContext(),"Error Occured ",Toast.LENGTH_SHORT).show();
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    public void updatespinner()
    {
        spinner.setAdapter(new Checkout_Addr_Adapter(getApplicationContext(),dbHelper.GetAddresses()));

            AddressLayout.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);
            addmore.setVisibility(View.VISIBLE);

    }


    public void upload_data(final String title, final String addr, final String area, final String city,final String dist, final String state, final String pin, final  String cont, final String useruid, final String country )
    {
        final ProgressDialog loading = ProgressDialog.show(Checkout_Page.this,"Adding Addresses...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        Toast.makeText(getApplicationContext(),s.toString(),Toast.LENGTH_LONG).show();

                        Log.d("response",s.toString());


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

    public void startPayment()
    {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        checkout.setPublicKey("rzp_live_Sc3yj4ZA5y1zUY");
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.lal_logo);

        /**
         * Reference to current activity
         */
        //final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: Rentomojo || HasGeek etc.
             */
            options.put("name", "Lal 10");
            options.put("key","rzp_live_Sc3yj4ZA5y1zUY");


            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */

            String uid=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

            options.put("description", "Pay_id : "+uid+" User_id : "+sessionManager.getUserDetails().get("uid"));

            options.put("currency", "INR");

            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */
            options.put("amount", "100");

            checkout.open(this, options);
        }
        catch(Exception e)
        {
            Log.e("card_razor", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s)
    {


        Log.d("payment_Sucess"," "+s);
        Catch_Razor(s,"100");
        Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_SHORT).show();
        Generate_Order(s,sessionManager.getUserDetails().get("uid"),spinner.getSelectedItem().toString(),grand_total," Not Delivered ","Razor_Pay",grand_total);

    }

    @Override
    public void onPaymentError(int i, String s)
    {

        Log.d("payment_error"," "+i+" "+s);

    }


    public void Generate_PayPal_Order()
    {



    }


    public boolean Catch_Razor(final String uid , final String amount)
    {
        final ProgressDialog loading = ProgressDialog.show(this,"Generating Order...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("inside_response",""+s);
                        if(s!=null)
                        {
                            loading.dismiss();
                            Log.d("catch_response",""+s);

                        }else {
                            loading.dismiss();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        Log.d("voley error",volleyError.toString());
                        //Showing toast
                        Toast.makeText(Checkout_Page.this, "Error in connectivity ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("id",uid);
                Keyvalue.put("amount",amount);
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

    public void Generate_Order( final String payuid, final String useruid, final String ordadd, final String grand_total, final String status, final String pay_mode, final String price)
    {
        final ProgressDialog loading = ProgressDialog.show(this,"Generating Order...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("inside_response",""+s);
                        if(s!=null)
                        {

                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("Order_Confirmation");
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                Generate_Invoice(jsonObject1.getString("ordid"),Cart_Data.toString(),price);
                                loading.dismiss();

                            } catch (JSONException e) {
                                loading.dismiss();

                                e.printStackTrace();
                            }

                        }else {
                            loading.dismiss();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        Log.d("voley error",volleyError.toString());
                        //Showing toast
                        Toast.makeText(Checkout_Page.this, "Error in connectivity ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("payuid",payuid);
                Keyvalue.put("useruid",useruid);

                Keyvalue.put("ordadd",ordadd);
                Keyvalue.put("grandtotal",grand_total);
                Keyvalue.put("user_name",sessionManager.getUserDetails().get("name"));
                Keyvalue.put("user_email",sessionManager.getUserDetails().get("email"));
                Keyvalue.put("pay_mode",pay_mode);
                Keyvalue.put("status",status);
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


    public void Generate_Invoice(final String orderid, final String prouid, final String price)
    {

        final ProgressDialog loading = ProgressDialog.show(Checkout_Page.this,"Genrating Invoice...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s)
                    {

                        loading.dismiss();
                        Log.d("response",s.toString());
                        HashMap<String,String> map = new HashMap<String,String>();
                        map.put("orderid",orderid);
                        map.put("price",price);
                       Intent intent  = new Intent(Checkout_Page.this,Order_Successful.class);
                        intent.putExtra("data",map );
                        intent.putExtra("checkout_add", (HashMap<String,String>)spinner.getSelectedItem());
                        startActivity(new Intent(Checkout_Page.this, Order_Successful.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        //Showing toast
                        Toast.makeText(getApplicationContext(), "Error in generating invoice please contact support", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("ordid",orderid);
                Keyvalue.put("proid",prouid);
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
}
