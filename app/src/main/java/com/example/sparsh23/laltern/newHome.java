package com.example.sparsh23.laltern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link newHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link newHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newHome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DBHelper dbHelper;
    AutoCompleteTextView autoCompleteTextView;
    SessionManager sessionManager;
    HashMap<String,String> data = new HashMap<String, String>();
    TextView name, company, desig, tob, addr, cont, pan, email, webs, state, city;


    //ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public newHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment newHome.
     */
    // TODO: Rename and change types and number of parameters
    public static newHome newInstance(String param1, String param2) {
        newHome fragment = new newHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbHelper = new DBHelper(getContext());




    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the gridsubitem for this fragment

        View root = inflater.inflate(R.layout.fragment_new_home, container, false);


        sessionManager = new SessionManager(getContext());

        name=(TextView)root.findViewById(R.id.reg_name);
        company=(TextView)root.findViewById(R.id.reg_comp);
        desig=(TextView)root.findViewById(R.id.reg_desg);
        tob=(TextView)root.findViewById(R.id.reg_bustype);
        addr=(TextView)root.findViewById(R.id.reg_addr);
        cont=(TextView)root.findViewById(R.id.reg_cont);
        pan=(TextView)root.findViewById(R.id.reg_pan);
        email=(TextView)root.findViewById(R.id.reg_email);
        webs=(TextView)root.findViewById(R.id.reg_webs);
        state=(TextView)root.findViewById(R.id.reg_state);
        city=(TextView)root.findViewById(R.id.reg_city);



        data  = dbHelper.GetProfile(sessionManager.getUserDetails().get("uid"));

//        webs.setText(data.get("web").toString());
        name.setText(data.get("name").toString());
        company.setText(data.get("comp").toString());
        desig.setText(data.get("design").toString());
        tob.setText(data.get("tob").toString());
        cont.setText(data.get("cont").toString());
        email.setText(data.get("email").toString());
        addr.setText(data.get("addr").toString());
        city.setText(data.get("city").toString());
        state.setText(data.get("state").toString());
        pan.setText(data.get("pan").toString());







        return root;






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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
