package com.example.moifoods.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moifoods.R;
import com.example.moifoods.Screens.Cart;
import com.example.moifoods.Screens.ItemView;
import com.example.moifoods.models.items;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.viewHolder> implements Filterable {

    private final Context mcontext;
    private final List<items> itemL;
    private List<items> Filtered;
    private OnItemClickListener mlistener;
    ItemAdapter itemAdapter;
    String userid;
    int total;

    public cartAdapter(Context mcontext, List<com.example.moifoods.models.items> items) {
        this.mcontext = mcontext;
        this.itemL = items;
        Filtered = items;
    }



    @NonNull
    @Override
    public cartAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mcontext).inflate(R.layout.cart_layout,parent,false);

        return new cartAdapter.viewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final cartAdapter.viewHolder holder, final int position) {
        final items object = Filtered.get(position);



        holder.itemName.setText(object.getItemName());
        holder.quantity.setText("Qnt :"+object.getPrice());
        final int amount = Integer.parseInt(object.getAmount());
        final int quantity = Integer.parseInt(object.getPrice());

        holder.amount.setText("Total : "+String.valueOf(amount*quantity)+" Ksh");

        getpicture(object.getImage(),holder.image);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(position,Filtered);
                Snackbar.make(v, "Removed from cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

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


    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        PhotoView image;
        TextView itemName,quantity,amount;
        CardView cardview;
        Button delete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.theimage);
            cardview = itemView.findViewById(R.id.cardview);
            itemName = itemView.findViewById(R.id.itemName);
            quantity = itemView.findViewById(R.id.quantity);
            amount = itemView.findViewById(R.id.amount);
            delete = itemView.findViewById(R.id.delete);



            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(mlistener!=null){
                int Position = getAdapterPosition();
                if(Position!=RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 2:
                            mlistener.OnEditClick(Position);
                            return true;
                        case 1:
                            mlistener.OnDeleteClick(Position);
                            return  true;
                    }

                }
            }
            return true;
        }

        @Override
        public void onClick(View v) {

            if(mlistener!=null){
                int Position = getAdapterPosition();
                if(Position!=RecyclerView.NO_POSITION){
                    switch (v.getId()){


                    }


                }
            }



        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


            MenuItem edit = menu.add(Menu.NONE,2,2,"Remove");

            edit.setOnMenuItemClickListener(this);

        }
    }


    public void getpicture(final String url, final PhotoView view){



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



    public interface OnItemClickListener{

        void OnEditClick(int Position);
        void OnDeleteClick(int Position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){

        mlistener = listener;

    }

    public void delete(final int Position,List<items> itemsList){
        total=0;

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
       userid = firebaseUser.getUid();


        DatabaseReference mDatabaseReference = getInstance().getReference("Cart").child(userid);


        final items selectedItem = itemsList.get(Position);
        final String SelectedKey = selectedItem.getKey();
        mDatabaseReference.child(SelectedKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mcontext,e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }

}

