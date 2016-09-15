package com.example.sparsh23.laltern;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.sparsh23.laltern.dummy.DummyContent;
import com.example.sparsh23.laltern.dummy.DummyContent.DummyItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SubCatItemsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    DBHelper dbHelper;
    String type;

    private OnListFragmentInteractionListener mListener;
    ArrayList<HashMap<String,String>> viewall = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> category = new ArrayList<HashMap<String, String>>();


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SubCatItemsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SubCatItemsFragment newInstance(int columnCount) {
        SubCatItemsFragment fragment = new SubCatItemsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            dbHelper = new DBHelper(getContext());
            type = getArguments().getString("type");


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subcatitems_list, container, false);


        viewall = dbHelper.getimageDatatype(type+"catall");

        category = dbHelper.getimageDatatype("jewelcat");

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            ViewGroup header = (ViewGroup) inflater.inflate(R.layout.categoryslider, recyclerView,false);



            SliderLayout sliderShow = (SliderLayout) header.findViewById(R.id.sliderviewall);
            for(int i =0; i<viewall.size();i++)
            {

                Log.d("slider jewel all size",""+viewall.size());
                sliderShow.addSlider(new DefaultSliderView(getContext()).image(viewall.get(i).get("path")).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        Toast.makeText(getContext(),""+viewall.get(0).get("meta"),Toast.LENGTH_SHORT).show();
                        // Bundle bundle = new Bundle();bundle.putString("type", artistdata.get(0).get("meta"));DealsFragment nextFrag= new DealsFragment();nextFrag.setArguments(bundle);getFragmentManager().beginTransaction().replace(R.id.navrep, nextFrag,null).addToBackStack(null).commit();

                    }
                }));

            }



            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MySubCatItemsRecyclerViewAdapter( context,category,mListener));
        }




        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
