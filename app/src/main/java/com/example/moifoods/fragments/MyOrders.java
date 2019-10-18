package com.example.moifoods.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.moifoods.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrders extends Fragment {

    Button track;
    Context context;
    View v;


    public MyOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_my_orders, container, false);
        context = container.getContext();


        track = v.findViewById(R.id.track);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,Track.class);
                startActivity(i);
            }
        });








        return v;

        // Inflate the layout for this fragment

    }

}
