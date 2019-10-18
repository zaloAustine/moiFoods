package com.example.moifoods.Screens;

import android.content.Intent;
import android.os.Bundle;

import com.example.moifoods.Adapters.ItemAdapter;
import com.example.moifoods.fragments.MyCart;
import com.example.moifoods.models.items;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moifoods.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class HotelMenu extends AppCompatActivity {

    private ItemAdapter itemAdapter;
    private List<com.example.moifoods.models.items> itemsList;
    private RecyclerView Recyclerview;
    private ValueEventListener mDBListener;
    private DatabaseReference mDatabaseReference;
    TextView Hotel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_menu);

        Bundle i = getIntent().getExtras();
        final String name = i.getString("name");

        Hotel = findViewById(R.id.Hotel);
        Hotel.setText(name+" Menu");

        mDatabaseReference = getInstance().getReference(name).child("menu");


        Recyclerview = findViewById(R.id.Recyclerview);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(HotelMenu.this,Cart.class);
                i.putExtra("Hotelname",name);
                CustomIntent.customType(HotelMenu.this,"fadein-to-fadeout");
                startActivity(i);


                Snackbar.make(view, name, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        SetRecycler();
        LoadMenu();




        ///searching
        EditText search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                itemAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }


    public void SetRecycler(){

        Recyclerview = findViewById(R.id.Recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HotelMenu.this);
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
        itemAdapter = new ItemAdapter(HotelMenu.this, itemsList);





    }

    public void LoadMenu(){


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
                Toast.makeText(HotelMenu.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


    protected void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mDBListener);
    }
    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this,"fadein-to-fadeout");
    }



}
