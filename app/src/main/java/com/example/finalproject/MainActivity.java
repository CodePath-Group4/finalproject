package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.finalproject.fragments.ProfileFragment;
import com.example.finalproject.fragments.SearchFragment;
import com.example.finalproject.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private FrameLayout flContainer;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;

                bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.home_outline_24);  // TODO: Change the image used for the search fragment
                bottomNavigationView.getMenu().getItem(1).setIcon(R.drawable.user_outline_24);
                bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.cog_empty);

                switch(item.getItemId()) {
                    case R.id.action_search:
                        Log.i(TAG, "action_search");
                        item.setIcon(R.drawable.home_filled_24);    // TODO: Change the image used for the search fragment
                        fragment = new SearchFragment();
                        break;
                    case R.id.action_profile:
                        Log.i(TAG, "action_profile");
                        item.setIcon(R.drawable.user_filled_24);
                        fragment = new ProfileFragment();
                        break;
                    case R.id.action_settings:
                        Log.i(TAG, "action_settings");
                        item.setIcon(R.drawable.cog_filled);
                        fragment = new SettingsFragment();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Unknown menu Item selected!",Toast.LENGTH_SHORT).show();
                        fragment = new SearchFragment();     // Default to search
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_search);     // Default to search when first opened
    }
}