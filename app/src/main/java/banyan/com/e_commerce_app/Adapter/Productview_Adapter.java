package banyan.com.e_commerce_app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import banyan.com.e_commerce_app.Activity.MainActivity;
import banyan.com.e_commerce_app.Model.SingleItemModel;
import banyan.com.e_commerce_app.R;
import banyan.com.e_commerce_app.SupportInterface.AddorRemoveCallbacks;

public class Productview_Adapter extends RecyclerView.Adapter<Productview_Adapter.ViewHolder>
{
 private ArrayList<SingleItemModel> ldatalist;
 private Context context;
    public static int image_array[] ={R.drawable.nav_fruits,R.drawable.nav_grocery,R.drawable.rice,R.drawable.nav_fruits,R.drawable.nav_vegitables,R.drawable.nav_fruits};
    public static String content_title[] ={"Fruits","Vegitables","Domestic Item","Fashion dress","Jewellery","Fruits","Vegitables","Domestic Item","Fashion dress","Jewellery","Fruits","Vegitables","Domestic Item","Fashion dress","Jewellery"};

    public Productview_Adapter(Context context,ArrayList<SingleItemModel> ldatalist) {
        this.ldatalist = ldatalist;
        this.context = context;
    }

    @NonNull
    @Override
    public Productview_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catagory_product_page,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Productview_Adapter.ViewHolder holder, final int position) {
       holder.imageView.setImageResource(image_array[position]);
        final SingleItemModel singleItem = ldatalist.get(position);
        holder.add_option.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(!ldatalist.get(position).isAddedTocart())
               {
                   ldatalist.get(position).setAddedTocart(true);
                   holder.content_data.setText(content_title[position]);
                   System.out.println("DATA_SHOW :"+singleItem.getName());
               }
               else if(context instanceof MainActivity)
               {
                   ((AddorRemoveCallbacks)context).onAddProduct();
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return ldatalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
       private ImageView imageView;
       private TextView content_data;
       private Button add_option;
       String content_text,image_url;
        SingleItemModel smodel;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.image);
            content_data = (TextView)itemView.findViewById(R.id.title);
            add_option = (Button)itemView.findViewById(R.id.add_Pay);
            content_text= content_data.getText().toString();
            image_url = imageView.toString();

            ldatalist = new ArrayList<>();
            smodel = new SingleItemModel(content_text,image_url);
            smodel.setName("fruits");

            smodel = new SingleItemModel(content_text,image_url);
            smodel.setName("vegitables");
            ldatalist.add(smodel);


        }
    }
}
