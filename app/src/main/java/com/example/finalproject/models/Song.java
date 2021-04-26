package com.example.finalproject.models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

@ParseClassName("Song")
public class Song extends ParseObject {
    public static final String TAG = "Song.class";

    public static final String KEY_SPOTIFY_ID = "spotifyId";
    public static final String KEY_SONG_NAME = "songName";
    public static final String KEY_ALBUM_NAME = "albumName";
    public static final String KEY_ARTIST_NAME = "artistName";
    public static final String KEY_IMAGE_URL = "imageUrl";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_ID = "objectId";

    public void setSpotifyId(String songId) {
        put(KEY_SPOTIFY_ID, songId);
    }

    public void setSongName(String songName) {
        put(KEY_SONG_NAME, songName);
    }

    public void setAlbumName(String albumName) {
        put(KEY_ALBUM_NAME, albumName);
    }

    public void setArtistName(String artistName) {
        put(KEY_ARTIST_NAME, artistName);
    }

    public void setImageUrl(String imageUrl) {
        put(KEY_IMAGE_URL, imageUrl);
    }


    public String getSpotifyId() {
        return getString(KEY_SPOTIFY_ID);
    }

    public String getSongName() {
        try {
            return fetchIfNeeded().getString(KEY_SONG_NAME);
        } catch (Exception e) {
            Log.e(TAG, "getSongName encountered error: " + e);
        }

        return null;
    }

    public String getAlbumName() {
        try {
            return fetchIfNeeded().getString(KEY_ALBUM_NAME);
        } catch (Exception e) {
            Log.e(TAG, "getAlbumName encountered error: " + e);
        }

        return null;
    }

    public String getArtistName() {
        try {
            return fetchIfNeeded().getString(KEY_ARTIST_NAME);
        } catch (Exception e) {
            Log.e(TAG, "getArtistName encountered error: " + e);
        }

        return null;
    }

    public String getImageUrl() {
        try {
            return fetchIfNeeded().getString(KEY_IMAGE_URL);
        } catch (Exception e) {
            Log.e(TAG, "getImageUrl encountered error: " + e);
        }

        return null;
    }

//    public String getObjectId() {
//        try {
//            return fetchIfNeeded().getString(KEY_ID);
//        } catch (Exception e) {
//            Log.e(TAG, "getObjectId encountered error: " + e);
//        }
//
//        return null;
//    }
}
