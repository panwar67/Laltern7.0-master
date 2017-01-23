package com.example.sparsh23.laltern;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //Spinner cat, subcat, sizeall, color, producttype;
    DBHelper dbHelper;
    //TextView min, max;
    //Button apply;
   // Spinner cat, subcat, sizeall, color, producttype;
    //DBHelper dbHelper;
    TextView min, max;
    ArrayList<HashMap<String ,String>> Check_Color, Check_Size, Check_Protype = new ArrayList<HashMap<String, String>>();
    Button apply;
    ArrayList<HashMap<String,String>> Selected_Options = new ArrayList<HashMap<String, String>>();
    //HashMap<String, ArrayList<HashMap<String, String>>> filtermap = new HashMap<>();
    ExpandableHeightGridView optionslist, itemlist;

   // HashMap<String,String> map = new HashMap<String, String>();
   // CrystalRangeSeekbar seekbar;
    View root;
    HashMap<String,String> cat_subcat = new HashMap<String, String>();
    ArrayList< HashMap<String,ArrayList<String>>> sizedata = new ArrayList<HashMap<String, ArrayList<String>>>();

    ArrayList< HashMap<String,ArrayList<String>>> colordata = new ArrayList<HashMap<String, ArrayList<String>>>();

    ArrayList< HashMap<String,ArrayList<String>>> prodata = new ArrayList<HashMap<String, ArrayList<String>>>();


    HashMap<String,String> filtermap = new HashMap<String, String>();

    HashMap<String,String> map = new HashMap<String, String>();
    CrystalRangeSeekbar seekbar;
    ArrayList< HashMap<String,ArrayList<String>>> spinnerdata = new ArrayList<HashMap<String, ArrayList<String>>>();



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FilterFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(String param1, String param2) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            filtermap = (HashMap<String, String>) getArguments().getSerializable("filter");
        }
        Check_Color = dbHelper.GetColor_Search();
        Check_Protype = dbHelper.GetProType_Search();
        Check_Size = dbHelper.GetSizes_Search();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_filter, container, false);

        optionslist = (ExpandableHeightGridView) root.findViewById(R.id.filteroptionlist);
        itemlist = (ExpandableHeightGridView) root.findViewById(R.id.filteritemlist);
        optionslist.setExpanded(true);

        optionslist.setNumColumns(1);

        itemlist.setExpanded(true);
        itemlist.setNumColumns(1);
        itemlist.setChoiceMode(ExpandableHeightGridView.CHOICE_MODE_MULTIPLE);
        seekbar = (CrystalRangeSeekbar)root.findViewById(R.id.rangeSeekbarprice);
        seekbar.setMaxStartValue(10000);
        seekbar.setMaxValue(10000);
        min = (TextView)root.findViewById(R.id.filterpricemin);
        max = (TextView)root.findViewById(R.id.filterpricemax);
        apply = (Button)root.findViewById(R.id.applyfilter);


        final ArrayList<String> options = new ArrayList<String>();
        options.add("PRODUCT TYPE");
        options.add("COLOR");
        options.add("SIZE");

        optionslist.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, options ));
        optionslist.performItemClick(optionslist.findViewWithTag(optionslist.getAdapter().getItem(0)), 0, optionslist.getAdapter().getItemId(0));

        optionslist.performItemClick(
                optionslist.getAdapter().getView(0, null, null),
                0,
                optionslist.getAdapter().getItemId(0));

        itemlist.setAdapter(new Filter_Checkbox_Adapter(getContext(),Check_Protype));

        optionslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getContext(),""+options.get(i)+""+i+"",Toast.LENGTH_SHORT).show();
                if(options.get(i).equals("COLOR"))
                {
                    itemlist.setAdapter(new Filter_Checkbox_Adapter(getContext(),Check_Color));

                }
                if(options.get(i).equals("SIZE"))
                {
                    itemlist.setAdapter(new Filter_Checkbox_Adapter(getContext(),Check_Size));

                }
                if(options.get(i).equals("PRODUCT TYPE"))
                {
                    itemlist.setAdapter(new Filter_Checkbox_Adapter(getContext(),Check_Protype));

                }
                if (options.get(i).equals("Category"))
                {
                    ArrayList<String> opt = new ArrayList<String>();
//                    opt.add(filtermap.get("category"));

                    //                  itemlist.setAdapter(new Filter_Checkbox_Adapter(getContext(),opt));
                }
                if (options.get(i).equals("Sub Category"))
                {   ArrayList<String> opt = new ArrayList<String>();
                    //                  opt.add(filtermap.get("subcat"));

//                    itemlist.setAdapter(new Filter_Checkbox_Adapter(getContext(),opt));

                }

                itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        CheckedTextView checkedTextView = (CheckedTextView)view.findViewById(R.id.filter_item_textchecked);
                        if(checkedTextView.isChecked())
                        {


                            Log.d("item_dechecked",""+itemlist.getItemAtPosition(i));
                            HashMap<String,String> map = new HashMap<String, String>();
                            map = (HashMap<String, String>)itemlist.getItemAtPosition(i);
                            if (map.get("col").equals("COLOR"))
                            {
                                Change_Color_Adapter("false",i);
                            }
                            if(map.get("col").equals("SIZE"))
                            {
                                Change_Size_Adapter("false",i);
                            }
                            if (map.get("col").equals("PROTYPE"))
                            {
                                Change_Protype_Adapter("false",i);
                            }


                            // itemlist.getItemAtPosition(i);
                            checkedTextView.setChecked(false);
                            Log.d("decheckeditem",""+i+"");

                            Selected_Options.remove(map);
                            Log.d("selected_item_size",""+Selected_Options.size());

                        }
                        else {


                            HashMap<String,String> map = new HashMap<String, String>();
                            map = (HashMap<String, String>)itemlist.getItemAtPosition(i);

                            Selected_Options.add(map);

                            //Selected_Options.remove(map);
                            Log.d("selected_item_size",""+Selected_Options.size());
                            checkedTextView.setChecked(true);
                            Log.d("checked item", "" + i + "");
                            Log.d("item_checked",""+itemlist.getItemAtPosition(i));
                            if (map.get("col").equals("COLOR"))
                            {
                                Change_Color_Adapter("true",i);
                            }
                            if(map.get("col").equals("SIZE"))
                            {
                                Change_Size_Adapter("true",i);
                            }
                            if (map.get("col").equals("PROTYPE"))
                            {
                                Change_Protype_Adapter("true",i);
                            }

                        }
                    }
                });

                apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ArrayList<HashMap<String, String>> filter_col = new ArrayList<HashMap<String, String>>();

                        for (int i = 0; i < Check_Color.size(); i++) {
                            if (Check_Color.get(i).get("status").equals("true"))
                            {
                                filter_col.add(Check_Color.get(i));
                            }
                        }
                        for (int i = 0; i < Check_Size.size(); i++) {
                            if (Check_Size.get(i).get("status").equals("true"))
                            {
                                filter_col.add(Check_Size.get(i));
                            }

                        }
                        for (int i = 0; i < Check_Protype.size(); i++) {

                            if (Check_Protype.get(i).get("status").equals("true"))
                            {
                                filter_col.add(Check_Protype.get(i));
                            }

                        }



                        ArrayList<HashMap<String,String>> filteredData = new ArrayList<HashMap<String, String>>();

                        Bundle bundle = new Bundle();

                        Log.d("check_forall_filtersize",""+filter_col.size());
                        filteredData = dbHelper.Get_Product_All_Search(filter_col);

                        HashMap<String,ArrayList<HashMap<String,String>>> filterdata = new HashMap<String, ArrayList<HashMap<String, String>>>();
                        filterdata.put("size",Check_Size);
                        filterdata.put("color",Check_Color);
                        filterdata.put("protype",Check_Protype);
                        bundle.putSerializable("data",filteredData);
                        ItemFragment itemFragment = new ItemFragment();
                        itemFragment.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.navrep, itemFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

          }
                });

            }
        });
       // dbHelper = new DBHelper(getContext());


        // Inflate the layout for this fragment
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

    public void Change_Color_Adapter(String status, int i)
    {

        Check_Color.get(i).put("status",status);
        Log.d("changed_item_color",""+Check_Color.get(i));
    }
    public void Change_Size_Adapter(String status,int i)
    {
        Check_Size.get(i).put("status",status);
        Log.d("changed_item_size",""+Check_Size.get(i));
    }
    public void Change_Protype_Adapter(String status, int i)
    {
        Check_Protype.get(i).put("status",status);
        Log.d("changed_item_protype",""+Check_Protype.get(i));

    }
}
