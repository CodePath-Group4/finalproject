package com.example.finalproject.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.LoginActivity;
import com.example.finalproject.R;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    private static final String TAG = "SettingFragment";


    private EditText etNewPassword;
    private EditText etNewPassword2;

    protected Button btnChangePassword;
    protected Button btnLogout;

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
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etNewPassword2 = view.findViewById(R.id.etNewPassword2);

        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newPassword = etNewPassword.getText().toString();
                String newPassword2 = etNewPassword2.getText().toString();

                ParseUser currentUser = ParseUser.getCurrentUser();
                if (!newPassword.equals(newPassword2)){
                    Toast.makeText(getActivity(),"Passwords do not match!",Toast.LENGTH_SHORT).show();
                }
                else {
                    currentUser.setPassword(newPassword);
                    currentUser.saveInBackground();
                    Toast.makeText(getActivity(),"Password has successfully been changed!",Toast.LENGTH_SHORT).show();
                    etNewPassword. setText("");
                    etNewPassword2. setText("");
                }
            }
        });

        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                goLoginActivity();
            }
        });

    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}