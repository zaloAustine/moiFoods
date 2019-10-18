package com.example.moifoods;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moifoods.Aunthetication.Login;
import com.example.moifoods.Screens.MainMenu;

import maes.tech.intentanim.CustomIntent;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logolauncher launcher = new logolauncher();
        launcher.start();


    }

    public class logolauncher extends Thread{

        @Override
        public void run() {
            super.run();

            try {
                sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            Intent i = new Intent(SplashScreen.this, Login.class);
            CustomIntent.customType(SplashScreen.this,"fadein-to-fadeout");
            startActivity(i);
          SplashScreen.this.finish();





        }
    }
}
