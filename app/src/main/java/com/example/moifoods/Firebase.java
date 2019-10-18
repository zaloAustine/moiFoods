package com.example.moifoods;

import android.app.Application;

import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class Firebase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Picasso.Builder biulder =new Picasso.Builder(this);
        biulder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso biuit = biulder.build();
        biuit.setIndicatorsEnabled(true);
        biuit.setLoggingEnabled(true);
        Picasso.setSingletonInstance(biuit);


        // Enabling database for resume support even after the application is killed:
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);

// Setting timeout globally for the download network requests:
        PRDownloaderConfig config2= PRDownloaderConfig.newBuilder()
                .setReadTimeout(30_000)
                .setConnectTimeout(30_000)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);
    }
}
