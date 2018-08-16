package banyan.com.e_commerce_app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import banyan.com.e_commerce_app.Activity.Activity_Product_DescriptionView;
import banyan.com.e_commerce_app.Model.SingleItemModel;
import banyan.com.e_commerce_app.R;

// vijayakumar


public class ProductViewSearch_Fragment extends Fragment
{
    private int mLastPosition;
    String[] category_item={"Fruits","Domestic Item","Fashion dress","Jewellery","Vegitables"};
    public static String content_title[] ={"Fruits","Domestic Item","Fashion dress","Jewellery","Vegitables"};
    public static int image_array[] ={R.drawable.nav_fruits,R.drawable.rice,R.drawable.nav_fruits,
            R.drawable.nav_vegitables,R.drawable.nav_grocery};
    int limage[]={R.drawable.nav_grocery};
    String ldata[] ={"Vegitables"};

    ArrayList<String> content_data;
    ArrayList<SingleItemModel> allSampleData;
    ListView listView,listView1;
    ListAdapter listAdapter;
    ListDataVegitables listDataVegitables;
    EditText custom_search;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootview=inflater.inflate(R.layout.product_search,null);

        allSampleData = new ArrayList<SingleItemModel>();

        content_data = new ArrayList<>();
        content_data.add("Vegitables");
        content_data.add("Fruits");

        // search demo
        custom_search = (EditText)rootview.findViewById(R.id.editTextSearch);
        custom_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              filter(s.toString());
            }
        });
        System.out.println("DTATA :"+allSampleData);
        listAdapter = new ListAdapter(content_data);

        listDataVegitables = new ListDataVegitables();

       listView = (ListView)rootview.findViewById(R.id.list_show);
       listView.setAdapter(listAdapter);

       //demo
        listView1=(ListView)rootview.findViewById(R.id.list_show1);

        Spinner spinner = (Spinner)rootview.findViewById(R.id.category_spinner);
        spinner.setVisibility(View.VISIBLE);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, category_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(adapter.getItem(position) == "Fruits")
                {
                    listView.setVisibility(View.VISIBLE);
                    listView1.setVisibility(View.GONE);
                    listAdapter.notifyDataSetChanged();
                }else if(adapter.getItem(position) == "Vegitables")
                {
                    listView1.setAdapter(listDataVegitables);
                    listView1.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    listDataVegitables.notifyDataSetChanged();
                }

                else
                {
                    listView.setVisibility(View.GONE);
                    listView1.setVisibility(View.GONE);
                    listAdapter.notifyDataSetChanged();
                    listDataVegitables.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootview;

    }

    // filter settings
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<String> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (String s : content_data) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        listAdapter.filterList(filterdNames);
    }


   public class ListAdapter extends BaseAdapter
   {
       ArrayList<String> lnames;
       public ListAdapter(ArrayList<String> tnames)
       {
           this.lnames=tnames;
       }

       @Override

    public int getCount() {
        return content_title.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.catagory_product_page,null);
        LinearLayout linearLayout = (LinearLayout)v.findViewById(R.id.linear_click);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Activity_Product_DescriptionView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
        TextView head_title = (TextView)v.findViewById(R.id.itemTitle1);
        Button view_image = (Button)v.findViewById(R.id.btnMore1);
        ImageView image = (ImageView)v.findViewById(R.id.image);
        TextView title = (TextView)v.findViewById(R.id.title);
        Button add_pay = (Button)v.findViewById(R.id.add_Pay);
        head_title.setText(content_title[position]);


        image.setImageResource(image_array[position]);
        title.setText(content_title[position]);

        view_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Activity_Product_DescriptionView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        add_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Product added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        // modify
        float initialTranslation = (mLastPosition <= position ? 500f : -500f);

        v.setTranslationY(initialTranslation);
        v.animate()
                .setInterpolator(new DecelerateInterpolator(1.0f))
                .translationY(0f)
                .setDuration(300l)
                .setListener(null);

        // Keep track of the last position we loaded
        mLastPosition = position;

        return v;
    }


       public void filterList(ArrayList<String> filterdNames) {
           notifyDataSetChanged();
           this.lnames = filterdNames;
       }
   }

public class ListDataVegitables extends BaseAdapter
{

    @Override
    public int getCount() {
        return ldata.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.catagory_product_page,null);
        LinearLayout linearLayout = (LinearLayout)v.findViewById(R.id.linear_click);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Activity_Product_DescriptionView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
        TextView head_title = (TextView)v.findViewById(R.id.itemTitle1);
        Button view_image = (Button)v.findViewById(R.id.btnMore1);
        ImageView image = (ImageView)v.findViewById(R.id.image);
        TextView title = (TextView)v.findViewById(R.id.title);
        Button add_pay = (Button)v.findViewById(R.id.add_Pay);
        head_title.setText(ldata[position]);

        image.setImageResource(limage[position]);
        title.setText(ldata[position]);

        view_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Activity_Product_DescriptionView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        add_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Product added to cart", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
}
