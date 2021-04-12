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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.SongsAdapter;
import com.example.finalproject.models.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

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
        for (int i = 0; i < 10; i++) {
            songs.add(new Song());
        }

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
                adapter.notifyDataSetChanged();
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
    }
}