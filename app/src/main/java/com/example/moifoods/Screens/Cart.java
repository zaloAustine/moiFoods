package com.example.moifoods.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.moifoods.Adapters.PageAdapter;
import com.example.moifoods.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class Cart extends AppCompatActivity {
    TabItem mycart,myorders;
    TabLayout tabLayout;
    PageAdapter pageAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

       DatabaseReference mDatabaseReference = getInstance().getReference("CurrentHotel");


        Bundle i = getIntent().getExtras();
        final String name = i.getString("Hotelname");

        mDatabaseReference.child("current").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });

        tabLayout = findViewById(R.id.tablayout);
        viewPager =  findViewById(R.id.viewpager);


        //pager
        pageAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(pageAdapter);

        //tab layout
        TabAction();
    }

    public void TabAction(){

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            }
        });
    }
}
