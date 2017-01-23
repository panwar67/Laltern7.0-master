package com.example.sparsh23.laltern;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.BubbleThumbSeekbar;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.lucasr.twowayview.TwoWayView;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutProductFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutProductFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutProductFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TwoWayView similar;
    Spinner avail_sizes;

    // TODO: Rename and change types of parameters
    private HashMap<String,String> mParam1;
    ArrayList<String> sizes = new ArrayList<String>();
    private String mParam2;
    TextView title , quan, price, artistname, des, craftpro, deshead, crafthead, selectquantity, similarproductslabel, priceseek, finalrating, availcolor, availsizes;
    RatingBar  overall;
    DBHelper dbHelper;
    Button button;
    TextView bar;
    HashMap<String,String> data;
    // FloatingActionsMenu floatingActionsMenu;
    String DOWN_URL = "http://www.whydoweplay.com/lalten/Addtocart.php";
    int counter = 0;
    ExpandableLinearLayout expandableLinearLayout;
    ExpandableRelativeLayout expandableRelativeLayout ;
    HashMap<String, String> artdata = new HashMap<String, String>();
    SessionManager sessionManager;
    com.github.clans.fab.FloatingActionButton fabcart;
    com.github.clans.fab.FloatingActionButton fabreq;
    BubbleThumbSeekbar seekBar;
    ImageView viewcart;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Tracker mTracker;


    private OnFragmentInteractionListener mListener;

    public AboutProductFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutProductFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutProductFrag newInstance(HashMap<String, String> param1, String param2) {
        AboutProductFrag fragment = new AboutProductFrag();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data =(HashMap<String,String>) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        dbHelper = new DBHelper(getContext());

        AnalyticsApplication application = (AnalyticsApplication)getActivity().getApplication();
        mTracker = application.getDefaultTracker();



    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_about_product, container, false);

        seekBar = (BubbleThumbSeekbar) view.findViewById(R.id.quantity);

        bar = (TextView)view.findViewById(R.id.quantityseek);
        avail_sizes = (Spinner) view.findViewById(R.id.sizehorizontalavail);
        availcolor = (TextView)view.findViewById(R.id.coloravail);
      //  availsizes = (TextView)view.findViewById(R.id.sizesavailable);
        priceseek = (TextView)view.findViewById(R.id.priceseek);
        des = (TextView)view.findViewById(R.id.descriptionpartpro);
        craftpro = (TextView) view.findViewById(R.id.typepro);
        crafthead = (TextView)view.findViewById(R.id.crafthead);
        deshead = (TextView)view.findViewById(R.id.deshead);
        similar = (TwoWayView)view.findViewById(R.id.similarproducts);
        sessionManager = new SessionManager(getContext());
        seekBar.setMinStartValue(Float.parseFloat(data.get("quantity")));
        seekBar.setMinValue(Float.parseFloat(data.get("quantity")));
        seekBar.setMaxValue(Float.parseFloat(data.get("stock")));
        ArrayList<String> sizelist = new ArrayList<String>();

        //sizelist = (ArrayList<String>) Arrays.asList(data.get("sizeavail").split(","));
        sizelist.addAll(Arrays.asList(data.get("sizeavail").split(",")));
        avail_sizes.setAdapter(new Available_Sizes_Adapter(getContext(),sizelist ));

        avail_sizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"selected item "+adapterView.getSelectedItem()+"",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        selectquantity = (TextView)view.findViewById(R.id.selectquantitylabel);
        similarproductslabel = (TextView)view.findViewById(R.id.similarproductslabel);
        finalrating = (TextView)view.findViewById(R.id.finalratings);
        overall = (RatingBar)view.findViewById(R.id.productrating);
        String string = "\u20B9";
        byte[] utf8 = null;
        try {
            utf8 = string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assert utf8 != null;
        try {
            string = new String(utf8, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final ArrayList<HashMap<String,String>> pro_data = dbHelper.GetSimilarProducts(data.get("title"),data.get("protype"),data.get("craft"),data.get("uid"));
        similar.setAdapter(new Trending_Pro_Adapter_Product_Page(getContext(),pro_data));
        similar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(),ProductView.class);
                intent.putExtra("promap",pro_data.get(i));
                startActivity(intent);

            }
        });

        seekBar.setBarHighlightColor(R.color.seekbar);

        final String finalString = string;
        seekBar.setLeftThumbHighlightDrawable(R.drawable.seekbig);
        seekBar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {

                bar.setText(value.toString());
                if(value.intValue()<Integer.parseInt(data.get("revquantity") ))
                {

                    priceseek.setText(""+ finalString +""+data.get("price"));

                    seekBar.setBarHighlightColor(R.color.seekbarin);
                    //seekBar.col
                    if(Integer.parseInt(data.get("revprice"))>0)
                    {

                    }


                }
                if(value.intValue()>Integer.parseInt(data.get("revquantity")))
                {
                    seekBar.setBarHighlightColor(Color.DKGRAY);
                    priceseek.setText(""+ finalString +""+data.get("revprice"));


                }

            }
        });
        fabcart = (FloatingActionButton) view.findViewById(R.id.fabcart);
        fabreq = (com.github.clans.fab.FloatingActionButton)view.findViewById(R.id.fabreq);
        fabreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SubmitRequest.class);
                intent1.putExtra("map",data);
                startActivity(intent1);
            }
        });
        //  fabcart = new FloatingActionButton(getApplicationContext());



        //fabcart = (FloatingActionButton) findViewById(R.id.fabcart);



        fabcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (dbHelper.IsProductUnique(data.get("uid")))
                {

                    mTracker.setScreenName("Add to cart");
                    //mTracker.set("added_cart",""+data.get("uid"));
                    mTracker.send(new HitBuilders.EventBuilder().build());
                    String uid=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

                    dbHelper.InsertCartData(uid,data.get("uid"),sessionManager.getUserDetails().get("uid"), String.valueOf(bar.getText()), String.valueOf(avail_sizes.getSelectedItem()));
                    upload_data(uid,data.get("uid"),sessionManager.getUserDetails().get("uid"),String.valueOf(bar.getText()),String.valueOf(avail_sizes.getSelectedItem()));

                }
                else {

                    Toast.makeText(getContext(),"Already In Cart",Toast.LENGTH_SHORT).show();

                }
            }
        });

        SliderLayout sliderShow = (SliderLayout) view.findViewById(R.id.slider);

        sliderShow.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        PagerIndicator pagerIndicator = (PagerIndicator)view.findViewById(R.id.custom_indicator);

        sliderShow.setCustomIndicator(pagerIndicator);

        int noimg = Integer.parseInt(data.get("noimages"));

        Log.d("No Images", ""+noimg);


        if(noimg>0){

            sliderShow.addSlider(new DefaultSliderView(getContext()).image( data.get("path")));


            for(int i=1;i<noimg;i++){


                String n =  "http://www.whydoweplay.com/lalten/Images/"+data.get("uid")+"_"+i+".jpeg";

                Log.d("path", n);

                sliderShow.addSlider(new DefaultSliderView(getContext()).image(n));


            }


        }




        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "Montserrat-Light.otf");

        Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(),"Helveticatest.ttf");
        Typeface tf2    =   Typeface.createFromAsset(getActivity().getAssets(),"Montserrat-Regular.otf");

        deshead.setTypeface(tf2);
        crafthead.setTypeface(tf2);
        similarproductslabel.setTypeface(tf2);
        selectquantity.setTypeface(tf2);
        availcolor.setTypeface(tf2);
        availcolor.setTypeface(tf2);
        title = (TextView)view.findViewById(R.id.titlepro);
        //des = (TextView)findViewById(R.id.descriptionpartpro);
        quan = (TextView)view.findViewById(R.id.quantitypro);
        price = (TextView)view.findViewById(R.id.pricepro);
        overall.setRating(Float.parseFloat(data.get("rating")));
        finalrating.setText(data.get("rating")+"/5");
        title.setTypeface(tf);

        craftpro.setTypeface(tf);

       // TextView textView = (TextView) view.findViewById(R.id.showart);

        price.setText( ""+string+""+data.get("price")+" - "+data.get("revprice"));
        des.setText(data.get("des"));
        des.setTypeface(tf);
        quan.setText("M.O.Q : "+data.get("quantity"));
        title.setText(data.get("title").toUpperCase());
        craftpro.setText(" "+data.get("craft"));
        Log.d("craft used",""+data.get("craft"));
        Toast.makeText(getContext(),""+data.get("craft"),Toast.LENGTH_SHORT).show();

        return view;
    }





    public void upload_data(final String cartuid, final String prouid, final String useruid, final String quantity,final String size)
    {
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Adding to cart...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {





                        Toast.makeText(getContext(),s.toString(),Toast.LENGTH_LONG).show();

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
                        Toast.makeText(getActivity(), "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("cartuid",cartuid);
                Keyvalue.put("prouid",prouid);
                Keyvalue.put("useruid",useruid);
                Keyvalue.put("quantity",quantity);
                Keyvalue.put("size",size);
                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
