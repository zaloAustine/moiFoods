package com.example.moifoods.Screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.moifoods.Adapters.ItemAdapter;
import com.example.moifoods.R;
import com.example.moifoods.SplashScreen;
import com.example.moifoods.models.items;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

import static maes.tech.intentanim.CustomIntent.customType;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    SliderLayout sliderLayout;
    CardView megaites,rockers,mwangaza,custom;
    TextView how;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        megaites = findViewById(R.id.megabites);
        rockers = findViewById(R.id.rockers);
        mwangaza = findViewById(R.id.mwangaza);
        custom = findViewById(R.id.customC);
        how = findViewById(R.id.how);

        how.setOnClickListener(this);
        megaites.setOnClickListener(this);
        rockers.setOnClickListener(this);
        mwangaza.setOnClickListener(this);
        custom.setOnClickListener(this);






        //loading the main menu images from here
        loadmage();
    }




    public void loadmage() {

      HashMap<String,String>  Hash_file_maps = new HashMap<String, String>();

        sliderLayout = findViewById(R.id.slider);
        Hash_file_maps.put("Ugali", "https://usercontent1.hubstatic.com/13702028_f1024.jpg");
        Hash_file_maps.put("Nyama Choma", "https://usercontent2.hubstatic.com/13702033_f1024.jpg");
        Hash_file_maps.put("Mutura", "https://usercontent2.hubstatic.com/13702037_f1024.jpg");
        Hash_file_maps.put("Mandazi", "https://usercontent2.hubstatic.com/13702051_f1024.jpg");
        Hash_file_maps.put("Chapati", "https://usercontent1.hubstatic.com/13702074_f1024.jpg");
        Hash_file_maps.put("Bhajia", "https://usercontent1.hubstatic.com/14381946_f1024.jpg");
        Hash_file_maps.put("Samosa", "https://usercontent1.hubstatic.com/13702094_f520.jpg");
        Hash_file_maps.put("Mahindi choma", "https://usercontent2.hubstatic.com/13709987_f1024.jpg");
        Hash_file_maps.put("Chai", "https://usercontent1.hubstatic.com/14222622_f1024.jpg");
        Hash_file_maps.put("Kuku", " https://usercontent2.hubstatic.com/13708521.jpg");
        Hash_file_maps.put("Kachumbari", "https://usercontent1.hubstatic.com/14381944_f520.jpg");










        for (String name : Hash_file_maps.keySet()) {

            TextSliderView textSliderView = new TextSliderView(MainMenu.this);
            textSliderView
                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.megabites:
                GoMenu("MegaBites");
                break;
            case R.id.rockers:
                GoMenu("Rockers");
                break;
            case R.id.mwangaza:
                GoMenu("Mwangaza");
                break;
            case R.id.customC:
                break;
            case R.id.how:
                How();
                break;


        }

    }

    private void How() {

        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setMessage("Choose your favourite hotel, place an order and get food at you door step in minutes\nCustom button : Order custom items ie mutura, mahindi choma ,Just anything edible ");
        b.setTitle("Moi Foods");
        b.setIcon(R.mipmap.moi_foods);

        b.setNegativeButton("OK",null);
        b.create();
        b.show();
    }


    public void GoMenu(String HotelName){
        Intent i = new Intent(MainMenu.this, HotelMenu.class);
        i.putExtra("name",HotelName);
        customType(MainMenu.this,"fadein-to-fadeout");
        startActivity(i);


        }


    
}
