package com.example.finalproject.models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

@ParseClassName("Song")
public class Song extends ParseObject {
    public static final String TAG = "Song.class";

    public static final String KEY_SONG_NAME = "songName";
    public static final String KEY_SPOTIFY_ID = "spotifyId";
    public static final String KEY_IMAGE_URL = "imageUrl";
    public static final String KEY_CREATED_AT = "createdAt";

    public void setSongName(String songName) {
        put(KEY_SONG_NAME, songName);
    }

    public void setSpotifyId(String songId) {
        put(KEY_SPOTIFY_ID, songId);
    }

    public void setImageUrl(String imageUrl) {
        put(KEY_IMAGE_URL, imageUrl);
    }


    public String getSongName() {
        try {
            return fetchIfNeeded().getString(KEY_SONG_NAME);
//        } catch (ParseException e) {
//            Log.e(TAG, "getSongName encountered error: " + e);
//            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "getSongName encountered error: " + e);
        }

        return null;
    }

    public String getSpotifyId() {
        return getString(KEY_SPOTIFY_ID);
    }

    public String getImageUrl() {
        return getString(KEY_IMAGE_URL);
    }
}
