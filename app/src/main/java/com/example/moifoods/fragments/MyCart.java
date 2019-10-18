package com.example.moifoods.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Progress;
import com.example.moifoods.Adapters.ItemAdapter;
import com.example.moifoods.Adapters.cartAdapter;
import com.example.moifoods.BussinessModels.PlaceOrder;
import com.example.moifoods.R;
import com.example.moifoods.Screens.HotelMenu;
import com.example.moifoods.models.items;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCart extends Fragment implements View.OnClickListener {

    Context context;
    View view;
    Button placeorder;
    private cartAdapter itemAdapter;
    private List<items> itemsList;
    private RecyclerView Recyclerview;
    private ValueEventListener mDBListener;
    private DatabaseReference mDatabaseReference;
    String Hotelname;
    ProgressDialog progressDialog;
    String userid;
    TextView total;
    int Total;



    public MyCart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        view =  inflater.inflate(R.layout.fragment_my_cart, container, false);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
         userid = firebaseUser.getUid();

         total = view.findViewById(R.id.total);

        Intent i = new Intent();
        Hotelname  = i.getStringExtra("Hotelname");


        mDatabaseReference = getInstance().getReference("Cart").child(userid);


        Recyclerview = view.findViewById(R.id.Recyclerview);

        placeorder = view.findViewById(R.id.placeorder);
        placeorder.setOnClickListener(this);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Placing Order");

        SetRecycler();
        LoadCart();

        total();



        return view;
    }




    public void SetRecycler(){

        Recyclerview = view.findViewById(R.id.Recyclerview2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        Recyclerview.setLayoutManager(layoutManager);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        Recyclerview.setNestedScrollingEnabled(false);
        Recyclerview.setHasFixedSize(true);

        Recyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                Recyclerview.setAdapter(itemAdapter);

            }
        }, 1);


        itemsList = new ArrayList<>();
        itemAdapter = new cartAdapter(context, itemsList);


    }

    public void LoadCart(){


        mDBListener = mDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemsList.clear();
                //loop throught all the nodes in the database
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    items blog = postSnapshot.getValue(items.class);
                    blog.setKey(postSnapshot.getKey());
                    itemsList.add(blog);



                }
                itemAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.placeorder:
                if(itemsList.isEmpty()){
                    Snackbar.make(v, "no Items in cart", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Intent i = new Intent(context, PlaceOrder.class);
                    context.startActivity(i);
                }

            break;
        }

    }



    public void total(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        userid = firebaseUser.getUid();
        DatabaseReference mDatabaseReference = getInstance().getReference("Cart").child(userid);
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String amount = dataSnapshot.child("amount").getValue(String.class);
                String price = dataSnapshot.child("price").getValue(String.class);

                Total = Total + Integer.parseInt(amount)*Integer.parseInt(price);
                total.setText(String.valueOf(Total));

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String amount = dataSnapshot.child("amount").getValue(String.class);
                String price = dataSnapshot.child("price").getValue(String.class);

                Total = Total + Integer.parseInt(amount)*Integer.parseInt(price);
                total.setText(String.valueOf(Total));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
