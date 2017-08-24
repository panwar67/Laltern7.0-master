package com.lions.sparsh23.laltern;

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
import com.lions.sparsh23.laltern.adapters.Filter_Checkbox_Adapter;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilterNoSearchFragment_Search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilterNoSearchFragment_Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterNoSearchFragment_Search extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Spinner cat, subcat, sizeall, color, producttype;
    DBHelper dbHelper;
    ArrayList<String> catspinitem = new ArrayList<String>();
    ArrayList<String> subcatspinitem = new ArrayList<String>();
    ArrayList<String> catitems = new ArrayList<String>();
    TextView min, max;
    ArrayList<HashMap<String ,String>> Check_Color, Check_Size, Check_Protype = new ArrayList<HashMap<String, String>>();
    Button apply;
    ArrayList<HashMap<String,String>> Selected_Options = new ArrayList<HashMap<String, String>>();
    HashMap<String, ArrayList<HashMap<String, String>>> filtermap = new HashMap<>();
    ExpandableHeightGridView optionslist, itemlist;

    HashMap<String,String> map = new HashMap<String, String>();
    CrystalRangeSeekbar seekbar;
    View root;
    HashMap<String,String> cat_subcat = new HashMap<String, String>();
    ArrayList< HashMap<String,ArrayList<String>>> sizedata = new ArrayList<HashMap<String, ArrayList<String>>>();

    ArrayList< HashMap<String,ArrayList<String>>> colordata = new ArrayList<HashMap<String, ArrayList<String>>>();

    ArrayList< HashMap<String,ArrayList<String>>> prodata = new ArrayList<HashMap<String, ArrayList<String>>>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FilterNoSearchFragment_Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterNoSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterNoSearchFragment_Search newInstance(String param1, String param2) {
        FilterNoSearchFragment_Search fragment = new FilterNoSearchFragment_Search();
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
            filtermap = (HashMap<String,ArrayList<HashMap<String,String>>>) getArguments().getSerializable("filter");
            cat_subcat = (HashMap<String,String>)getArguments().getSerializable("selection");
            Check_Color = filtermap.get("color");
            Check_Size = filtermap.get("size");
            Check_Protype = filtermap.get("protype");



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         root = inflater.inflate(R.layout.fragment_filter_no_search, container, false);

       // catspinitem.add(filtermap.get("category"));
        //subcatspinitem.add(filtermap.get("subcat"));

        producttype = (Spinner)root.findViewById(R.id.producttypespinner);
        sizeall = (Spinner)root.findViewById(R.id.sizespinner);
        color = (Spinner)root.findViewById(R.id.colorspinner);
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
        cat = (Spinner)root.findViewById(R.id.catspin);
        subcat = (Spinner)root.findViewById(R.id.subcatspin);
        min = (TextView)root.findViewById(R.id.filterpricemin);
        max = (TextView)root.findViewById(R.id.filterpricemax);
        apply = (Button)root.findViewById(R.id.applyfilter);


        final ArrayList<String> options = new ArrayList<String>();
        options.add("PRODUCT TYPE");
        options.add("COLOR");
        options.add("SIZE");

        optionslist.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, options ));
        optionslist.performItemClick(
                optionslist.getAdapter().getView(0, null, null),
                0,
                optionslist.getAdapter().getItemId(0));

        optionslist.performItemClick(optionslist.findViewWithTag(optionslist.getAdapter().getItem(0)), 0, optionslist.getAdapter().getItemId(0));


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

            }
        });
        dbHelper = new DBHelper(getContext());

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
                    checkedTextView.setChecked(false);
                    Log.d("decheckeditem",""+i+"");
                    Selected_Options.remove(map);
                    Log.d("selected_item_size",""+Selected_Options.size());
                }
                else
                {
                    HashMap<String,String> map = new HashMap<String, String>();
                    map = (HashMap<String, String>)itemlist.getItemAtPosition(i);
                    Selected_Options.add(map);
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

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.spinner_style,catspinitem);
        cat.setAdapter(arrayAdapter);
        cat.setSelection(0);
        subcat.setAdapter(new ArrayAdapter(getContext(),R.layout.spinner_style,subcatspinitem));
        subcat.setSelection(0);

        seekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                min.setText(minValue.toString());
                max.setText( maxValue.toString());

            }
        });




        subcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d("subcatselect",""+subcat.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {


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
                filteredData = dbHelper.Get_Product_All_Test_Filter(filter_col,cat_subcat);

                HashMap<String,ArrayList<HashMap<String,String>>> filterdata = new HashMap<String, ArrayList<HashMap<String, String>>>();
                filterdata.put("size",Check_Size);
                filterdata.put("color",Check_Color);
                filterdata.put("protype",Check_Protype);


                bundle.putSerializable("data",filteredData);
                Log.d("filter_data_result",""+filteredData.size());
                bundle.putSerializable("filter",filterdata);
                bundle.putSerializable("selection",cat_subcat);



                ItemFragment itemFragment = new ItemFragment();

                itemFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.navrep, itemFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
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
