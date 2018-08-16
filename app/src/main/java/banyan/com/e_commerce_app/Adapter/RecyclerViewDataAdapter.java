package banyan.com.e_commerce_app.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import banyan.com.e_commerce_app.Activity.Activity_Product_DescriptionView;
import banyan.com.e_commerce_app.Model.SectionDataModel;
import banyan.com.e_commerce_app.Model.SingleItemModel;
import banyan.com.e_commerce_app.R;


public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

    private ArrayList<SectionDataModel> dataList, filtered_data_list;
    private Context mContext;
    private Fragment currentFragment;

    private List<SingleItemModel> movieListFiltered;
    private ArrayList<SingleItemModel> itemsList;

    public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList, Fragment currentfragment) {
        this.dataList = dataList;
        this.filtered_data_list = dataList ;
        this.mContext = context;
        this.currentFragment = currentfragment;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, final int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);

        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);

        itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);

        itemRowHolder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.btnMore:
                        Intent intent=new Intent(mContext,Activity_Product_DescriptionView.class);
                        mContext.startActivity(intent);
                        break;
                        default:
                            break;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;
        protected Button btnView;
        protected RecyclerView recycler_view_list;

        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            this.btnView = (Button) view.findViewById(R.id.btnMore);

        }

    }


    // my search filter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        filtered_data_list.clear();
        if (charText.length() == 0) {
            filtered_data_list.addAll(dataList);
        } else {
            for (SectionDataModel wp : dataList) {
                if (wp.getHeaderTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    filtered_data_list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}