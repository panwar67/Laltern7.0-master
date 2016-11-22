package com.example.sparsh23.laltern;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.sparsh23.laltern.dummy.DummyContent;
import com.example.sparsh23.laltern.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {



    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> sorteddata = new ArrayList<HashMap<String, String>>();
    DBHelper dbHelper;
   // ArrayList<String> day_radio;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    String ARG_TYPE;
    String ARG_CAT;
    String ARG_SUB;
    HashMap<String,String> cat_subcat_map = new HashMap<String, String>();
    String ARG_UID;
    ImageView sort, filter;
    ToggleButton imageView;
    ArrayList<HashMap<String,String>> map = new ArrayList<HashMap<String, String>>();
    HashMap<String,String> filtermap =    new HashMap<String, String>();
    HashMap<String,ArrayList<HashMap<String,String>>> filter_build = new HashMap<String, ArrayList<HashMap<String,String>>>();

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount)
    {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            map = (ArrayList<HashMap<String,String>>)getArguments().getSerializable("data");
            cat_subcat_map = (HashMap<String,String>)getArguments().getSerializable("selection");

            if(getArguments().getSerializable("filter")!=null)

            {

                filtermap = (HashMap<String,String>)getArguments().getSerializable("filter");
                Log.d("inside filter map",filtermap.toString());

            }
            if(getArguments().getSerializable("filter_build")!=null);
            {
                filter_build = (HashMap<String,ArrayList<HashMap<String,String>>>) getArguments().getSerializable("filter_build");

            }



           // ARG_CAT = getArguments().getString("category");
            //ARG_SUB = getArguments().getString("subcat");

            //ARG_UID = getArguments().getString("uid");
        }



        dbHelper = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        final View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);

        imageView = (ToggleButton)view.findViewById(R.id.togglelayoutitem);
        sort = (ImageView)view.findViewById(R.id.searchpagesort);
        filter = (ImageView)view.findViewById(R.id.filtersearchpage);
        imageView.setText(null);
        imageView.setTextOff(null);
        imageView.setTextOn(null);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(map, mListener, getContext(),1));



       // Toast.makeText(getContext(),""+ARG_CAT+" "+ARG_SUB+" "+ARG_UID+"",Toast.LENGTH_SHORT).show();




        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(filtermap!=null&&filtermap.size()>0)
                {



                    Bundle bundle = new Bundle();
                    bundle.putSerializable("filter",filtermap);
                    bundle.putSerializable("selection",cat_subcat_map);
                    FilterNoSearchFragment fragment = new FilterNoSearchFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.navrep, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }if(filtermap.size()==0)
                {

                    Bundle bundle = new Bundle();
                    //bundle.putSerializable("filter",filtermap);
                    FilterFragment fragment = new FilterFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.navrep, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }


            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = view.getContext();







                if(imageView.isChecked())
                {
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 2));

                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(map, mListener, getContext(),2));


                }
                if(!imageView.isChecked()){



                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(map, mListener, getContext(),1));



                }
            }
        });

        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Intent intent = new Intent(getActivity(),ProductView.class);
                        intent.putExtra("promap",map.get(position));
                        startActivity(intent);




                    }
                });

        final CharSequence[] items = { "Price : Low to High", "Price : High to Low", "Rating : Low to High", "Rating : High to Low" };



        sort.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext())
                        .setTitle("Choose a Day")
                        .setSingleChoiceItems( items, -1, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
// TODO Auto-generated method stub



                                if(which==0){



                                    sorteddata = map;

                                    Collections.sort(sorteddata, new MapComparator("price"));

                                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

                                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(sorteddata, mListener, getContext(),2));
                                    imageView.setChecked(false);




                                }

                                if (which==1){
                                    sorteddata = map;

                                    Collections.sort(sorteddata, new MapComparator("price"));

                                    Collections.reverse(sorteddata);

                                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

                                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(sorteddata, mListener, getContext(),2));
                                    imageView.setChecked(false);


                                }
                                if (which==2)

                                {
                                    sorteddata = map;

                                    Collections.sort(sorteddata,new MapComparator("rating"));

                                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

                                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(sorteddata, mListener, getContext(),2));
                                    imageView.setChecked(false);


                                }
                                if (which==3)
                                {


                                    sorteddata = map;

                                    Collections.sort(sorteddata, new MapComparator("rating"));
                                    Collections.reverse(sorteddata);

                                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

                                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(sorteddata, mListener, getContext(),2));
                                    imageView.setChecked(false);



                                }




                                Toast.makeText(getContext(),
                                        "The selected Day is " +which, Toast.LENGTH_LONG).show();

//dismissing the dialog when the user makes a selection.
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertdialog2 = builder2.create();
                alertdialog2.show();






                return false;
            }
        });






/*        if (view instanceof RecyclerView) {


            Context context = view.getContext();

            if (mColumnCount == 0) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new MyItemRecyclerViewAdapter(map, mListener, getContext(),1));

            } else {
               recyclerView.setLayoutManager(new GridLayoutManager(context, 2));

                recyclerView.setAdapter(new MyItemRecyclerViewAdapter(map, mListener, getContext(),2));

            }
        }

*/




       /* if(ARG_UID != "nouid")
        {

            searchdata = dbHelper.GetProductsFromSearch(ARG_UID);
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(searchdata, mListener, getContext()));

}

        else
        if((ARG_CAT!="nocat")&&(ARG_SUB!="nosub")){

        HashMap<String,String> aux = new HashMap<String, String>();
        aux.put("category",ARG_CAT);
        aux.put("subcat",ARG_SUB);


        data = dbHelper.GetSubCategoryImageData(aux);
       //  dbHelper.getimageDatatype(ARG_TYPE);
        // Set the adapter


            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(data, mListener, getContext()));
        }*/

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
