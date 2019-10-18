package com.example.moifoods.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moifoods.R;
import com.example.moifoods.Screens.HotelMenu;
import com.example.moifoods.Screens.ItemView;
import com.example.moifoods.Screens.MainMenu;
import com.example.moifoods.models.items;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.viewHolder> implements Filterable {

    private final Context mcontext;
    private final List<items> itemL;
    private List<items> Filtered;

    public ItemAdapter(Context mcontext, List<com.example.moifoods.models.items> items) {
        this.mcontext = mcontext;
        this.itemL = items;
        Filtered = items;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mcontext).inflate(R.layout.item_layout,parent,false);

        return new viewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {

        final items object = Filtered.get(position);


        holder.itemName.setText(object.getItemName());
        holder.description.setText(object.getDescrption());
        holder.amount.setText(object.getAmount());
        getpicture(object.getImage(),holder.image);


        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewIt(object.getItemName(),object.getImage(),object.getDescrption(),object.getAmount());
            }
        });

    }

    @Override
    public int getItemCount() {
        return Filtered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if(key.isEmpty()){
                    Filtered = itemL;
                }else{
                    List<items> lstFiltered = new ArrayList<>();
                    for(items row:itemL){

                        if(row.getItemName().toLowerCase().contains(key.toLowerCase())){
                            lstFiltered.add(row);

                        }
                    }

                    Filtered = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = Filtered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                Filtered = (List<items>) results.values;
                notifyDataSetChanged();

            }
        };
    }


    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView itemName,description,amount;
        CardView cardview;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            cardview = itemView.findViewById(R.id.cardview);
            itemName = itemView.findViewById(R.id.itemName);
            description = itemView.findViewById(R.id.description);
            amount = itemView.findViewById(R.id.amount);
        }
    }


    public void getpicture(final String url, final ImageView view){



        Picasso.with(mcontext).load(url).noFade().skipMemoryCache().networkPolicy(NetworkPolicy.OFFLINE).resize(1000,500).into(view, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(mcontext).load(url).noFade().resize(1000,600).into(view);

            }
        });





        Picasso.with(mcontext).load(url).noFade().skipMemoryCache().networkPolicy(NetworkPolicy.OFFLINE).resize(1000,600).into(view, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(mcontext).load(url).noFade().resize(1000,600).into(view);

            }
        });



    }

    public void viewIt(String name,String image,String description,String amount){
        Intent i = new Intent(mcontext, ItemView.class);
        i.putExtra("name",name);
        i.putExtra("image",image);
        i.putExtra("description",description);
        i.putExtra("amount",amount);
        CustomIntent.customType(mcontext,"fadein-to-fadeout");
        mcontext.startActivity(i);

    }

}
