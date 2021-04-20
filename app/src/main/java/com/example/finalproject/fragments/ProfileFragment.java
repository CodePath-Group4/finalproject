package com.example.finalproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.R;
import com.example.finalproject.SongsAdapter;
import com.example.finalproject.models.Favorite;
import com.example.finalproject.models.Song;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    private RecyclerView rvFavoriteSongs;
    private SongsAdapter adapter;

    private List<Song> songs;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        songs = new ArrayList<Song>();

        rvFavoriteSongs = view.findViewById(R.id.rvFavoriteSongs);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvFavoriteSongs.setLayoutManager(layoutManager);

        adapter = new SongsAdapter(getContext(), songs);        // Should be able to use the same SongAdapter used by SearchFragment
        rvFavoriteSongs.setAdapter(adapter);
        fetchFavorites();
    }

    private void fetchFavorites() {
        Log.i(TAG, "fetchFavorites for user: " + ParseUser.getCurrentUser().getUsername());
        ParseQuery<Favorite> query = ParseQuery.getQuery(Favorite.class);

        query.include(Favorite.KEY_USER);
        query.whereEqualTo(Favorite.KEY_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Favorite>() {
            @Override
            public void done(List<Favorite> favoritesFound, ParseException e) {
                Log.i(TAG, "fetchFavorites, done");
                if (e != null) {
                    Log.e(TAG, "Problem getting favorites", e);
                    return;
                }

                Log.i(TAG, "Number of favorites found: " + favoritesFound.size());
                for (int i = 0; i < favoritesFound.size(); i++) {
                    Favorite favorite = favoritesFound.get(i);
                    Song song = (Song) favorite.getSong();
                    Log.i(TAG, "Song name: " + song.getSongName());
                    songs.add(song);
                }

                adapter.notifyDataSetChanged();
            }
        });
    }
}