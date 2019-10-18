package com.example.moifoods.Screens;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.moifoods.models.items;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moifoods.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class ItemView extends AppCompatActivity {

    PhotoView photo;
    Button addtocart;
    TextView thedescrition,name,amount;
    private DatabaseReference mDatabaseReference;
    ProgressDialog progressDialog;
    Spinner ChooseQuantity;
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDatabaseReference = getInstance().getReference().child("Cart");

        thedescrition = findViewById(R.id.thedescription);
        name = findViewById(R.id.name);
        photo = findViewById(R.id.photo);
        amount = findViewById(R.id.amount);


        //getting Intent Extras
        Bundle i = getIntent().getExtras();
        final String Iname = i.getString("name");
        final String description = i.getString("description");
        final String Iamount = i.getString("amount");
        final String image = i.getString("image");


        addtocart = findViewById(R.id.addtocart);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(name.getText().toString(),thedescrition.getText().toString(),amount.getText().toString(),image,v);
            }
        });


        //spinner quantity
        String quatityArray[] = {"1","2","3","4","5"};
        ChooseQuantity = findViewById(R.id.ChooseQuantity);
        ArrayAdapter<String> spinner1 = new ArrayAdapter<String>(ItemView.this,android.R.layout.simple_spinner_item,quatityArray);
        spinner1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ChooseQuantity.setAdapter(spinner1);




        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding to cart");




        thedescrition.setText(description);
        name.setText(Iname);
        amount.setText(Iamount);
        Picasso.with(ItemView.this).load(image).noFade().into(photo);



        ChooseQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                value = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });




    }



    private void addToCart(String name,String description,String amount,String Image,final View v ) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userid = firebaseUser.getUid();





        //create new upload item
        items titheStructure = new items(Image,name,description,amount,value);
//                    //create new Entry in dataBase with unique key
        String uploadId = mDatabaseReference.push().getKey();
//                    //save the name and the download uri
        mDatabaseReference.child(userid).child(uploadId).setValue(titheStructure).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ItemView.this,e.getMessage(),Toast.LENGTH_SHORT).show();


            }
        });

        Snackbar.make(v, "Added to cart", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();


    }


}
