package banyan.com.e_commerce_app.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import banyan.com.e_commerce_app.Activity.Activity_Product_DescriptionView;
import banyan.com.e_commerce_app.Activity.MainActivity;
import banyan.com.e_commerce_app.Model.SingleItemModel;
import banyan.com.e_commerce_app.R;
import banyan.com.e_commerce_app.SupportInterface.AddorRemoveCallbacks;


public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder>  {

    ArrayList<SingleItemModel>itemsList;
    private Context mContext;

    public static int image_array[] ={R.drawable.nav_fruits,R.drawable.nav_grocery,R.drawable.rice,R.drawable.nav_fruits,R.drawable.nav_vegitables,R.drawable.nav_fruits};
    int id ;
    public SectionListDataAdapter(Context context, final ArrayList<SingleItemModel> itemsList) {
       this.itemsList = itemsList;
       this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        final SingleItemRowHolder mh = new SingleItemRowHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {

        SingleItemModel singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.getName());
        System.out.println("GTENAME :"+singleItem.getName().toString());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent=new Intent(mContext,Activity_Product_DescriptionView.class);
               mContext.startActivity(intent);

           }
       });

        // image set
        // id=mContext.getResources().getIdentifier("banyan.com.e_commerce_app:drawable/nav_grocery" , null, null);
         holder.itemImage_show.setImageResource(image_array[i]);
         holder.itemImage_show.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(mContext,Activity_Product_DescriptionView.class);
                 mContext.startActivity(intent);
             }
         });

         holder.add_pay.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(!itemsList.get(i).isAddedTocart())
                 {
                     itemsList.get(i).setAddedTocart(true);
                     holder.add_pay.setText("Remove");
                     if(mContext instanceof MainActivity)
                     {
                         ((AddorRemoveCallbacks)mContext).onAddProduct();
                     }

                 }
                 else if(itemsList.get(i).isAddedTocart())
                 {
                     itemsList.get(i).setAddedTocart(false);
                     holder.add_pay.setText("Add");
                     ((AddorRemoveCallbacks)mContext).onRemoveProduct();
                 }
/*
                 TastyToast.makeText(mContext,"Payment is SuccessFully Added",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
*/

             }
         });

         /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }




    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;
        protected CardView cardView;
        protected ImageView itemImage_show;

        protected Button add_pay;

        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage_show = (ImageView) view.findViewById(R.id.itemImage);
            this.add_pay = (Button) view.findViewById(R.id.addPay);
            this.cardView = (CardView) view.findViewById(R.id.cardview);

        }
    }

}