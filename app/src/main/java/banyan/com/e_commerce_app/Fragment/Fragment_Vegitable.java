package banyan.com.e_commerce_app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import banyan.com.e_commerce_app.Adapter.CustomPagerAdapter;
import banyan.com.e_commerce_app.Adapter.RecyclerViewDataAdapter;
import banyan.com.e_commerce_app.Adapter.SectionListDataAdapter;
import banyan.com.e_commerce_app.Model.SectionDataModel;
import banyan.com.e_commerce_app.Model.SingleItemModel;
import banyan.com.e_commerce_app.R;

public class Fragment_Vegitable extends Fragment {

    private Fragment currentfragment;
    private static int currentPage = 0;
    TextView txt_search;
    SearchView searchView;
    ViewPager viewPager;
    CustomPagerAdapter adapter;


    public static final int[]images = {R.drawable.nav_fruits, R.drawable.nav_vegitables,R.drawable.nav_grocery,R.drawable.rice}; // viewpager image
    int i,j;

    ArrayList<SectionDataModel> allSampleData;

    RecyclerViewDataAdapter adapter_section;
    ArrayList<SingleItemModel> arraylist = new ArrayList<SingleItemModel>();
//    section details
    public static int image_array[] ={R.drawable.nav_fruits,R.drawable.nav_grocery,R.drawable.rice,R.drawable.nav_fruits,R.drawable.nav_vegitables,R.drawable.nav_fruits};
    public static String content_title[] ={"Fruits","Vegitables","Domestic Item","Fashion dress","Jewellery","Fruits","Vegitables","Domestic Item","Fashion dress","Jewellery","Fruits","Vegitables","Domestic Item","Fashion dress","Jewellery"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.vegitables_layout,null);
        getActivity().setTitle("Vegitables");

//        find view by id
        //searchView = (SearchView)v.findViewById(R.id.search1);
        for (int i = 0; i < content_title.length; i++) {
            SingleItemModel animalNames = new SingleItemModel(content_title[i],"URL " + i);
            // Binds all strings into an array
            arraylist.add(animalNames);
        }

        //txt_search = (TextView)v.findViewById(R.id.txt_search);
        viewPager = (ViewPager)v.findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout)v.findViewById(R.id.tabDots);
        RecyclerView my_recycler_view = (RecyclerView) v.findViewById(R.id.my_recycler_view);


//        set details
//        set view pager adapter
        adapter = new CustomPagerAdapter(getActivity());
        viewPager.setAdapter(adapter);

//        set view pager in tab
        tabLayout.setupWithViewPager(viewPager, true);

        currentfragment = new Fragment_Vegitable();
        allSampleData = new ArrayList<SectionDataModel>();

        // Calling the RecyclerView
        AutoUpdatedCount();

        my_recycler_view.setHasFixedSize(true);
//        set adaptor
        adapter_section = new RecyclerViewDataAdapter(getActivity(), allSampleData,currentfragment);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter_section);


        //action

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);


        // search section
        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                adapter_section.filter(text);
                return false;
            }
        });*/


        return v;
    }

    public void AutoUpdatedCount() {
        for (i = 0; i < 15; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle(content_title[i]);

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for ( j = 0; j < 5; j++) {
                singleItem.add(new SingleItemModel(content_title[j], "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }
}
