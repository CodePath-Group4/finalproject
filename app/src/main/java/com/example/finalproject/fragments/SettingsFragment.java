package com.example.finalproject.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalproject.LoginActivity;
import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    private Button btnChangePassword;
    private Button btnLogout;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "btnLogout onClick, logging out user " + ParseUser.getCurrentUser().getUsername());
                ParseUser.logOut();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
    }
}