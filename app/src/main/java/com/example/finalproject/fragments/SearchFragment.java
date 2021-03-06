package com.example.finalproject.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;
import com.example.finalproject.R;
import com.example.finalproject.SongsAdapter;
import com.example.finalproject.models.Song;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";
    private static final String API_URL = "https://api.spotify.com/v1/";

    private EditText etSearch;
    private Button btnSubmit;
    private RecyclerView rvSearchResults;
    private SongsAdapter adapter;

    private List<Song> songs;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        songs = new ArrayList<Song>();
        etSearch = view.findViewById(R.id.etSearch);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "btnSubmit onClick");

                String searchText = etSearch.getText().toString();
                if (searchText.isEmpty()) {
                    Toast.makeText(getContext(), "Search cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                searchSongs(searchText);
            }
        });

        rvSearchResults = view.findViewById(R.id.rvSearchResults);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvSearchResults.setLayoutManager(layoutManager);

        adapter = new SongsAdapter(getContext(), songs);
        rvSearchResults.setAdapter(adapter);
    }

    protected void searchSongs(String searchText) {

        // TODO: Complete search
        // Tentative steps that search should take:
        // 1. Authenticate to the Spotify API
        // 2. Search using Spotify API
        // 3. Compare song IDs of results to spotifyIds in database
        //      a. If song is not in database, add it along with relevant data
        // 4. Add songs to list and notify dataset has changed
        // 5. Profit B)

        adapter.clear();
//        queryAllSongs();        // For now, just showing all songs in the Back4App database
        querySongs(searchText);
    }

    private void queryAllSongs() {
        Log.i(TAG, "queryAllSongs");
        ParseQuery<Song> query = ParseQuery.getQuery(Song.class);

        query.include(Song.KEY_SONG_NAME);
//        query.whereEqualTo(Favorite.KEY_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Song>() {
            @Override
            public void done(List<Song> songsFound, ParseException e) {
                Log.i(TAG, "queryAllSongs, done");
                if (e != null) {
                    Log.e(TAG, "Problem getting songs", e);
                    return;
                }

                Log.i(TAG, "Number of songs found: " + songsFound.size());
                for (int i = 0; i < songsFound.size(); i++) {
                    Song song = (Song) songsFound.get(i);
                    Log.i(TAG, "Song name: " + song.getSongName());
                    songs.add(song);
                }

                adapter.notifyDataSetChanged();
            }
        });
    }


    private void querySongs(String songName) {
        Log.i(TAG, "querySongs");
        ParseQuery<Song> query = ParseQuery.getQuery(Song.class);

        query.include(Song.KEY_SONG_NAME);
        query.whereContains(Song.KEY_ARTIST_NAME, songName);
        query.findInBackground(new FindCallback<Song>() {
            @Override
            public void done(List<Song> songsFound, ParseException e) {
                Log.i(TAG, "querySongs, done");
                if (e != null) {
                    Log.e(TAG, "Problem getting songs", e);
                    return;
                }

                Log.i(TAG, "Number of songs found: " + songsFound.size());
                for (int i = 0; i < songsFound.size(); i++) {
                    Song song = (Song) songsFound.get(i);
                    Log.i(TAG, "Song name: " + song.getSongName());
                    songs.add(song);
                }

                adapter.notifyDataSetChanged();
            }
        });
    }


    protected String requestAccessToken() {
        // Trying to do Client Credentials Flow from https://developer.spotify.com/documentation/general/guides/authorization-guide/
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestHeaders headers = new RequestHeaders();
        headers.put("Authorization", "Basic: edbe0491df78468ea5314bdd1c4cc7b9");    // Not too sure this one is done properly
        params.put("grant_type", "client_credentials");

        // I believe this is meant to be a post request, but I can't seem to figure out how to do that
        client.get("https://accounts.spotify.com/api/token", params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, String response) {
                Log.i(TAG, "requestAccessToken onSuccess");
                Log.d(TAG, response);
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String errorResponse, Throwable t) {
                Log.e(TAG, "requestAccessToken onFailure, " + errorResponse);
            }
        });

        return null;
    }
    // Could maybe try using this instead if we can't figure out the above https://github.com/thelinmichael/spotify-web-api-java
}