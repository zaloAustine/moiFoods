package com.example.moifoods.BussinessModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moifoods.R;
import com.example.moifoods.models.cart;
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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class PlaceOrder extends AppCompatActivity {
    ProgressDialog progressDialog;
    String userid;
    Button button;
    ArrayList<Object> list;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Placing Order please wait....");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        userid = firebaseUser.getUid();

        text = findViewById(R.id.text);


        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetCartItems();

               placeOrder(v);

            }
        });


    }


    public void GetCartItems(){

        list = new ArrayList<>();


        DatabaseReference mDatabaseReference = getInstance().getReference("Cart").child(userid);
       mDatabaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               GenericTypeIndicator<HashMap<String,Object>> objectindicator = new GenericTypeIndicator<HashMap<String, Object>>() {};
               Map<String,Object> objectMap = dataSnapshot.getValue(objectindicator);

               if (objectMap != null) {
                   list = new ArrayList<>(objectMap.values());
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });



        DatabaseReference UserDatabaseReference = getInstance().getReference("UserOrders").child(userid);
        UserDatabaseReference.setValue(list).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PlaceOrder.this,"done",Toast.LENGTH_LONG).show();

            }
        });

    }

    private void placeOrder(final View V) {

        final DatabaseReference mDatabaseReference = getInstance().getReference("Cart").child(userid);


        progressDialog.show();

        DatabaseReference Database = getInstance().getReference("CurrentHotel");


        Database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Current  = dataSnapshot.child("current").getValue(String.class);



                DatabaseReference   MainDatabase = getInstance().getReference().child(Current).child("Orders");
                String uploadId =   MainDatabase.push().getKey();

                MainDatabase.child(uploadId).setValue(list).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        mDatabaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(V, "Order Received check My Orders for status", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(V, e.getMessage(), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }
                        });


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
