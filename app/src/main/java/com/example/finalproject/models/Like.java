package com.example.finalproject.models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Like")
public class Like extends ParseObject {
    public static final String TAG = "Like.class";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";    // Will likely be needed in the future for sorting by date favorited
    public static final String KEY_SONG = "song";

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public void setSong(Song song) {
        put(KEY_SONG, song);
    }


    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public ParseObject getSong() {
        try {
            return fetchIfNeeded().getParseObject(KEY_SONG);
        } catch (Exception e) {
            Log.e(TAG, "getSong encountered error: " + e);
        }

        return null;
    }
}
