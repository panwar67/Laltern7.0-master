package com.example.sparsh23.laltern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.lucasr.twowayview.TwoWayView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutArtistFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutArtistFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutArtistFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RatingBar authen, prices, overall;
    DBHelper dbHelper;
    private TextView names, tob, state, craft, awards;

    TwoWayView artistproducts;



    // TODO: Rename and change types of parameters
    private HashMap<String,String> mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AboutArtistFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutArtistFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutArtistFrag newInstance(HashMap<String, String> param1, String param2) {
        AboutArtistFrag fragment = new AboutArtistFrag();
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
            mParam1 = (HashMap<String, String>) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        dbHelper = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_artist,container,false);
//authen = (RatingBar)view.findViewById(R.id.authenrate);
  //      prices = (RatingBar)view.findViewById(R.id.pricerate);
    //    overall = (RatingBar)view.findViewById(R.id.overallrating);
      //  authen.setRating(Float.parseFloat(mParam1.get("authentic")));
        //prices.setRating(Float.parseFloat(mParam1.get("price")));
        //overall.setRating(Float.parseFloat(mParam1.get("rating")));
        artistproducts = (TwoWayView)view.findViewById(R.id.artistproducts);
        SliderLayout sliderShow = (SliderLayout) view.findViewById(R.id.slider);
        artistproducts.setAdapter(new Trending_Pro_Adapter_Product_Page(getContext(),dbHelper.GetArtistProducts(mParam1.get("artuid"))));
        WebView webView = (WebView)view.findViewById(R.id.webs);
        webView.getSettings().getJavaScriptCanOpenWindowsAutomatically();
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setLoadWithOverviewMode(true);
        ///webView.getSettings().setUseWideViewPort(true);
       // webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
      //  webView.clearCache(true);
        webView.clearView();
        webView.reload();
     //   webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.loadUrl("about:blank");
        webView.loadUrl("http://www.whydoweplay.com/lalten/no-jquery.html");
        webView.setWebChromeClient(new WebChromeClient());
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"BioRhyme-Regular.ttf");
//        int noimg = Integer.parseInt(mParam1.get("noimg"));
  //      Log.d("No Images in art", ""+noimg);
        return view;
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
