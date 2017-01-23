package com.example.sparsh23.laltern;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.sparsh23.laltern.dummy.DummyContent;
import com.example.sparsh23.laltern.dummy.DummyContent.DummyItem;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class categoryFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    ImageLoader imageLoader;
    DisplayImageOptions options;
    String type;
    String cat;
    private int mColumnCount = 2;
    DBHelper dbHelper;
    ArrayList<HashMap<String,String>> headcategory = new ArrayList<HashMap<String, String>>();

    ArrayList<HashMap<String,String>> category = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> viewall = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> jewelec = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> jewelcs = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> jewelpa = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> jeweltp = new ArrayList<HashMap<String, String>>();
    private OnListFragmentInteractionListener mListener1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public categoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static categoryFragment newInstance() {
        categoryFragment fragment = new categoryFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            type = getArguments().getString("type");
            cat = getArguments().getString("cat");



        }

        options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY).resetViewBeforeLoading(true).build();
        ImageLoaderConfiguration.Builder config1 = new ImageLoaderConfiguration.Builder(getContext());
        config1.defaultDisplayImageOptions(options);
        config1.threadPriority(Thread.NORM_PRIORITY - 2);
        config1.denyCacheImageMultipleSizesInMemory();
        config1.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config1.diskCacheSize(100 * 1024 * 1024); // 50 MiB
        config1.tasksProcessingOrder(QueueProcessingType.LIFO);
        config1.writeDebugLogs();





        imageLoader = ImageLoader.getInstance();
//        imageLoader.destroy();
        imageLoader.init(config1.build());



        dbHelper = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        headcategory = dbHelper.getimageDatatype(type+"catheader");
        viewall = dbHelper.getimageDatatype(type+"catall");



        category = dbHelper.getimageDatatype(type+"cat");

       // Log.d("inside tiles cat",""+category);



        View view = inflater.inflate(R.layout.fragment_deals, container, false);

        GridViewWithHeaderAndFooter listView = (GridViewWithHeaderAndFooter) view.findViewById(R.id.subcatgrid);



        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.categoryslider, listView,false);




        listView.addHeaderView(header,null,true);
        listView.setAdapter(new GridSubcatAdapter(getContext(),category));
        listView.deferNotifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),""+category.get(position).get("meta"),Toast.LENGTH_SHORT).show();
                ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
                data = dbHelper.GetProductsFromSearch_test("SUBCAT",category.get(position).get("meta"));
                //ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
                data = dbHelper.GetProductsFromSearch_test("SUBCAT",category.get(position).get("meta"));
                HashMap<String,String> aux = new HashMap<String, String>();
                HashMap<String,String> map = new HashMap<String, String>();

                aux.put("col","SUBCAT");
                aux.put("value",category.get(position).get("meta"));
                HashMap<String,ArrayList<HashMap<String,String>>> filterdata = new HashMap<String, ArrayList<HashMap<String, String>>>();
                filterdata.put("size",dbHelper.GetSizes_Random("SUBCAT",category.get(position).get("meta")));
                filterdata.put("color",dbHelper.GetColor_Random("SUBCAT",category.get(position).get("meta")));
                filterdata.put("protype",dbHelper.GetProType_Random("SUBCAT",category.get(position).get("meta")));

                Bundle bundle1 = new Bundle();

                bundle1.putSerializable("data",data);
                bundle1.putSerializable("filter",filterdata);
                bundle1.putSerializable("selection",aux);

                ItemFragment_Search itemFragment = new ItemFragment_Search();

                itemFragment.setArguments(bundle1);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.navrep, itemFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });



        SliderLayout sliderShow = (SliderLayout) header.findViewById(R.id.sliderviewall);


        ImageView imageView = (ImageView)header.findViewById(R.id.catheader1);

        imageLoader.displayImage(headcategory.get(0).get("path"), imageView);

        sliderShow.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        sliderShow.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        //sliderShow.startAutoCycle();


        for(int i =0; i<viewall.size();i++)
        {

            Log.d("slider jewel all size",""+viewall.size());
            sliderShow.addSlider(new DefaultSliderView(getContext()).image(viewall.get(i).get("path")).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {

                    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
                    data = dbHelper.GetProductsFromSearch_test("CATEGORY",cat);
                    HashMap<String,String> aux = new HashMap<String, String>();
                    HashMap<String,String> map = new HashMap<String, String>();

                    aux.put("col","CATEGORY");
                    aux.put("value",cat);
                    HashMap<String,ArrayList<HashMap<String,String>>> filterdata = new HashMap<String, ArrayList<HashMap<String, String>>>();
                    filterdata.put("size",dbHelper.GetSizes_Random("CATEGORY",cat));
                    filterdata.put("color",dbHelper.GetColor_Random("CATEGORY",cat));
                    filterdata.put("protype",dbHelper.GetProType_Random("CATEGORY",cat));

                    Bundle bundle1 = new Bundle();

                    bundle1.putSerializable("data",data);
                    bundle1.putSerializable("filter",filterdata);
                    bundle1.putSerializable("selection",aux);

                    ItemFragment_Search itemFragment = new ItemFragment_Search();

                    itemFragment.setArguments(bundle1);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.navrep, itemFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                   // Toast.makeText(getContext(),""+viewall.get(0).get("meta"),Toast.LENGTH_SHORT).show();
                   // Bundle bundle = new Bundle();bundle.putString("type", artistdata.get(0).get("meta"));DealsFragment nextFrag= new DealsFragment();nextFrag.setArguments(bundle);getFragmentManager().beginTransaction().replace(R.id.navrep, nextFrag,null).addToBackStack(null).commit();


                }
            }));

        }
// Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }


          //  recyclerView.setAdapter(new MycategoryRecyclerViewAdapter(DummyContent.ITEMS, mListener1));

        }


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener1 = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener1 = null;
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

}
