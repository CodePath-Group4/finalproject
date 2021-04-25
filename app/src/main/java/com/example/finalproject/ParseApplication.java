package com.example.finalproject;

import android.app.Application;

import com.example.finalproject.models.Favorite;
import com.example.finalproject.models.Like;
import com.example.finalproject.models.Song;
import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        // Use for troubleshooting -- remove this line for production
//        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
//
//        // Use for monitoring Parse OkHttp traffic
//        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
//        // See https://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        builder.networkInterceptors().add(httpLoggingInterceptor);

        // set applicationId, and server server based on the values in the back4app settings.
        // any network interceptors must be added with the Configuration Builder given this syntax

        ParseObject.registerSubclass(Song.class);
        ParseObject.registerSubclass(Like.class);
        ParseObject.registerSubclass(Favorite.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("gPkAymJm0pyYC8S9HJlroVf84cjrdvf1IbYs8K7l") // should correspond to Application Id env variable
                .clientKey("2wUG1kUDDYs9oNswbOabOU03v2wzSMknZaJpeDxA")  // should correspond to Client key env variable
                .server("https://parseapi.back4app.com")
                .build());
    }
}
